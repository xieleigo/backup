//
//  LXGetIPAddressTool.m
//  TaxReader
//
//  Created by asdc on 2020/5/22.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

//#import <ifaddrs.h>
//#import <arpa/inet.h>
//#import <net/if.h>
//
//#define IOS_CELLULAR    @"pdp_ip0"
//#define IOS_WIFI        @"en0"
//#define IOS_VPN         @"utun0"
//#define IP_ADDR_IPv4    @"ipv4"
//#define IP_ADDR_IPv6    @"ipv6"

#import <ifaddrs.h>
#import <resolv.h>
#import <arpa/inet.h>
#import <net/if.h>
#import <netdb.h>
#import <netinet/ip.h>
#import <net/ethernet.h>
#import <net/if_dl.h>

#define MDNS_PORT       5353
#define QUERY_NAME      "_apple-mobdev2._tcp.local"
#define DUMMY_MAC_ADDR  @"02:00:00:00:00:00"
#define IOS_CELLULAR    @"pdp_ip0"
#define IOS_WIFI        @"en0"
#define IOS_VPN         @"utun0"
#define IP_ADDR_IPv4    @"ipv4"
#define IP_ADDR_IPv6    @"ipv6"

#import "LXGetIPAddressTool.h"

@implementation LXGetIPAddressTool

// 只有当你的设备连接到WIFI时才能获取到IP地址，倘若你的设备用的是流量，那就不行
- (NSString *)getIPAddress {
    NSString *address = @"error";
    struct ifaddrs *interfaces = NULL;
    struct ifaddrs *temp_addr = NULL;
    int success = 0;
    // retrieve the current interfaces - returns 0 on success
    success = getifaddrs(&interfaces);
    if (success == 0) {
        // Loop through linked list of interfaces
        temp_addr = interfaces;
        while(temp_addr != NULL) {
            if(temp_addr->ifa_addr->sa_family == AF_INET) {
                // Check if interface is en0 which is the wifi connection on the iPhone
                if([[NSString stringWithUTF8String:temp_addr->ifa_name] isEqualToString:@"en0"]) {
                    // Get NSString from C String
                    address = [NSString stringWithUTF8String:inet_ntoa(((struct sockaddr_in *)temp_addr->ifa_addr)->sin_addr)];
                }
            }
            temp_addr = temp_addr->ifa_next;
        }
    }
    // Free memory
    freeifaddrs(interfaces);
    return address;

}

// 方法比较通用，无论是WIFI还是流量都能获取到IP地址
+ (NSString *)getIPAddressWiFiAndTraffic:(BOOL)preferIPv4 {
    NSArray *searchArray = preferIPv4 ?
    @[ IOS_VPN @"/" IP_ADDR_IPv4, IOS_VPN @"/" IP_ADDR_IPv6, IOS_WIFI @"/" IP_ADDR_IPv4, IOS_WIFI @"/" IP_ADDR_IPv6, IOS_CELLULAR @"/" IP_ADDR_IPv4, IOS_CELLULAR @"/" IP_ADDR_IPv6 ] :
    @[ IOS_VPN @"/" IP_ADDR_IPv6, IOS_VPN @"/" IP_ADDR_IPv4, IOS_WIFI @"/" IP_ADDR_IPv6, IOS_WIFI @"/" IP_ADDR_IPv4, IOS_CELLULAR @"/" IP_ADDR_IPv6, IOS_CELLULAR @"/" IP_ADDR_IPv4 ] ;

    NSDictionary *addresses = [self getIPAddresses];
    NSLog(@"addresses: %@", addresses);

    __block NSString *address;
    [searchArray enumerateObjectsUsingBlock:^(NSString *key, NSUInteger idx, BOOL *stop)
     {
         address = addresses[key];
         //筛选出IP地址格式
         if([self isValidatIP:address]) *stop = YES;
     } ];
    return address ? address : @"0.0.0.0";
}

+ (BOOL)isValidatIP:(NSString *)ipAddress {
    if (ipAddress.length == 0) {
        return NO;
    }
    NSString *urlRegEx = @"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    NSError *error;
    NSRegularExpression *regex = [NSRegularExpression regularExpressionWithPattern:urlRegEx options:0 error:&error];

    if (regex != nil) {
        NSTextCheckingResult *firstMatch=[regex firstMatchInString:ipAddress options:0 range:NSMakeRange(0, [ipAddress length])];

        if (firstMatch) {
            NSRange resultRange = [firstMatch rangeAtIndex:0];
            NSString *result=[ipAddress substringWithRange:resultRange];
            //输出结果
            NSLog(@"%@",result);
            return YES;
        }
    }
    return NO;
}

+ (NSDictionary *)getIPAddresses
{
    NSMutableDictionary *addresses = [NSMutableDictionary dictionaryWithCapacity:8];

    // retrieve the current interfaces - returns 0 on success
    struct ifaddrs *interfaces;
    if(!getifaddrs(&interfaces)) {
        // Loop through linked list of interfaces
        struct ifaddrs *interface;
        for(interface=interfaces; interface; interface=interface->ifa_next) {
            if(!(interface->ifa_flags & IFF_UP) /* || (interface->ifa_flags & IFF_LOOPBACK) */ ) {
                continue; // deeply nested code harder to read
            }
            const struct sockaddr_in *addr = (const struct sockaddr_in*)interface->ifa_addr;
            char addrBuf[ MAX(INET_ADDRSTRLEN, INET6_ADDRSTRLEN) ];
            if(addr && (addr->sin_family==AF_INET || addr->sin_family==AF_INET6)) {
                NSString *name = [NSString stringWithUTF8String:interface->ifa_name];
                NSString *type;
                if(addr->sin_family == AF_INET) {
                    if(inet_ntop(AF_INET, &addr->sin_addr, addrBuf, INET_ADDRSTRLEN)) {
                        type = IP_ADDR_IPv4;
                    }
                } else {
                    const struct sockaddr_in6 *addr6 = (const struct sockaddr_in6*)interface->ifa_addr;
                    if(inet_ntop(AF_INET6, &addr6->sin6_addr, addrBuf, INET6_ADDRSTRLEN)) {
                        type = IP_ADDR_IPv6;
                    }
                }
                if(type) {
                    NSString *key = [NSString stringWithFormat:@"%@/%@", name, type];
                    addresses[key] = [NSString stringWithUTF8String:addrBuf];
                }
            }
        }
        // Free memory
        freeifaddrs(interfaces);
    }
    return [addresses count] ? addresses : nil;
}

/*
 * 获取设备物理地址
 */
- (nullable NSString *)getMacAddress {
    res_9_init();
    int len;
    //get currnet ip address
    NSString *ip = [self currentIPAddressOf:IOS_WIFI];
    if(ip == nil) {
        fprintf(stderr, "could not get current IP address of en0\n");
        return DUMMY_MAC_ADDR;
    }//end if
    
    //set port and destination
    _res.nsaddr_list[0].sin_family = AF_INET;
    _res.nsaddr_list[0].sin_port = htons(MDNS_PORT);
    _res.nsaddr_list[0].sin_addr.s_addr = [self IPv4Pton:ip];
    _res.nscount = 1;
    
    unsigned char response[NS_PACKETSZ];
    
    
    //send mdns query
    if((len = res_9_query(QUERY_NAME, ns_c_in, ns_t_ptr, response, sizeof(response))) < 0) {
        
        fprintf(stderr, "res_search(): %s\n", hstrerror(h_errno));
        return DUMMY_MAC_ADDR;
    }//end if
    
    //parse mdns message
    ns_msg handle;
    if(ns_initparse(response, len, &handle) < 0) {
        fprintf(stderr, "ns_initparse(): %s\n", hstrerror(h_errno));
        return DUMMY_MAC_ADDR;
    }//end if
    
    //get answer length
    len = ns_msg_count(handle, ns_s_an);
    if(len < 0) {
        fprintf(stderr, "ns_msg_count return zero\n");
        return DUMMY_MAC_ADDR;
    }//end if
    
    //try to get mac address from data
    NSString *macAddress = nil;
    for(int i = 0 ; i < len ; i++) {
        ns_rr rr;
        ns_parserr(&handle, ns_s_an, 0, &rr);
        
        if(ns_rr_class(rr) == ns_c_in &&
           ns_rr_type(rr) == ns_t_ptr &&
           !strcmp(ns_rr_name(rr), QUERY_NAME)) {
            char *ptr = (char *)(ns_rr_rdata(rr) + 1);
            int l = (int)strcspn(ptr, "@");
            
            char *tmp = calloc(l + 1, sizeof(char));
            if(!tmp) {
                perror("calloc()");
                continue;
            }//end if
            memcpy(tmp, ptr, l);
            macAddress = [NSString stringWithUTF8String:tmp];
            free(tmp);
        }//end if
    }//end for each
    macAddress = macAddress ? macAddress : DUMMY_MAC_ADDR;
    return macAddress;
}//end getMacAddressFromMDNS

- (nonnull NSString *)currentIPAddressOf: (nonnull NSString *)device {
    struct ifaddrs *addrs;
    NSString *ipAddress = nil;
    
    if(getifaddrs(&addrs) != 0) {
        return nil;
    }//end if
    
    //get ipv4 address
    for(struct ifaddrs *addr = addrs ; addr ; addr = addr->ifa_next) {
        if(!strcmp(addr->ifa_name, [device UTF8String])) {
            if(addr->ifa_addr) {
                struct sockaddr_in *in_addr = (struct sockaddr_in *)addr->ifa_addr;
                if(in_addr->sin_family == AF_INET) {
                    ipAddress = [self IPv4Ntop:in_addr->sin_addr.s_addr];
                    break;
                }//end if
            }//end if
        }//end if
    }//end for
    
    freeifaddrs(addrs);
    return ipAddress;
}//end currentIPAddressOf:

- (nullable NSString *)IPv4Ntop: (in_addr_t)addr {
    char buffer[INET_ADDRSTRLEN] = {0};
    return inet_ntop(AF_INET, &addr, buffer, sizeof(buffer)) ?
    [NSString stringWithUTF8String:buffer] : nil;
}//end IPv4Ntop:

- (in_addr_t)IPv4Pton: (nonnull NSString *)IPAddr {
    in_addr_t network = INADDR_NONE;
    return inet_pton(AF_INET, [IPAddr UTF8String], &network) == 1 ?
    network : INADDR_NONE;
}//end IPv4Pton:

@end

