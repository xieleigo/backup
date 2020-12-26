//
//  TaxReaderAPIHeader.swift
//  TaxReader
//
//  Created by asdc on 2020/6/1.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

/*
    1、 请将 APP的 签名密钥修改为(请注意大小写)：F3494C964062400C9B25B3A27B2BE178
    2、APP接口对接IP请更换为该域名：http://mag.ctax.org.cn
 */

// 建议先放在配置文件里，后期上线了可能会做更改
// // APP应用secret key,税刊阅读平台 提供给APP应用的唯一标识
let appsecret: String = "F3494C964062400C9B25B3A27B2BE178" // c4ca4238a0b923820dcc509a6f75849b

// 使用：注册协议，联系我们 图片加载
//let appIp: String = "https://mag.ctax.org.cn/" // http://210.12.84.109
let appIp: String = "http://210.12.84.109/" // http://210.12.84.109

// API 接口调用
//let appAPI: String = "https://mag.ctax.org.cn/api" // http://210.12.84.109/API      // 线上
let appAPI: String = "http://210.12.84.109/API" // http://210.12.84.109/API      // 线上

//let appAPI: String = "http://192.168.5.133:81/API"    // DMC 国投
//let appAPI: String = "http://172.25.75.47:83"         // DMC 得实

// 来源（Android=10,IOS=20,PC=30）
let UseFrom: String = "20"

// 安卓20 iOS 30
let OrderForm: String = "30"

// 支付宝 5 weixin 22
let PayModelAliLKL: String = "5"
let PayModelWeixin: String = "22"

// 微信支付
let wxPayAppId: String = "wx8592574799e9e9ba"

// Bugly
let buglyAppID: String = "ef4909e91d"
let buglyAppKey: String = "ceec4471-b849-4a6c-ab61-a14070407448"

/// 订单状态（0-已取消；1-待支付；2-支付中；3-已支付；4-申请退款；5-退款中；6-已退款）
let NetDataOrderStatus: Int = 1
/// 无需开票=0，申请开票=10，开票中=20，开票完成=30
let NetDataOrderInvoiceStatus: Int = 10
let NetDataOrderInvoiceStatus30: Int = 30

// 年度套餐显示问题
let ProdType10: Int = 10
let ProdType30: Int = 30

// authorization参数不能为空
let NetDataAuthorizationNull: Int = 3000
let TokenNullTitle: String = ""
let TokenNullDetailTitle: String = "需要先登录个人账户才能继续操作"
let TokenNullActionDefault: String = "去登录"
let TokenNullActionCancel: String = "取消"

//MARK: -时间转时间戳函数
func TRAPIHeader(isHasToken: Bool) -> Dictionary<String, Any> {
    let appsecretString = appsecret
    let timestampString = Date().milliStamp // 13位时间戳
    let changeTimestampString = String(describing: (Int(timestampString) ?? 0) - 10000) // 提前5秒合适 - 5000
    //print("时间戳是(目前当前时间减了10秒) = \(changeTimestampString)")
    
    let nonceString = "049e73d6e0744a7491c\(changeTimestampString)" // 一次性随机数 32位
    
    // appSecret=c4ca4238a0b923820dcc509a6f75849b&nonce=049e73d6e0744a7491cda2a0a537b90d&timestamp=1466399895704
    // 按照key以升序的方式排序，组合成字符串,使用SHA1WithRSA进行加密,返回小写形式的内容
    // 签名有效期只有一次,每次调用接口时都需要重新生成签名
    let signatureFormat = "appSecret=\(appsecretString)&nonce=\(nonceString)&timestamp=\(changeTimestampString)"
    let signatureString = signatureFormat.sha1()
    print("待签名的字符串是 = \(signatureFormat)")
    print("签名后的字符串是 = \(signatureString)")
    
    var dictionary: [String:String] = [:]
    dictionary.updateValue("application/x-www-form-urlencoded", forKey: "Content-Type")
    dictionary.updateValue(nonceString, forKey: "nonce")
    dictionary.updateValue(signatureString, forKey: "signature")
    dictionary.updateValue(changeTimestampString, forKey: "timestamp")
    dictionary.updateValue("", forKey: "authorization")
    
    //let headerToken: String = UserDefaults.LoginInfo.string(forKey: .access_token) ?? ""
    let headerToken: String = publicAuthorizationToken ?? ""
    if isHasToken && !headerToken.isBlank {
        dictionary.updateValue(headerToken, forKey: "authorization")
    }
    
    //print("最后的 header 是 = \(dictionary)")
    
    return dictionary
}



























/*
public var TRAPIHeaders: [String : String]? {
    let appsecretString = appsecret
    let timestampString = Date().milliStamp // 13位时间戳
    let changeTimestampString = String(describing: (Int(timestampString) ?? 0) - 10000) // 提前5秒合适 - 5000
    print("changeTimestampString = \(changeTimestampString)")
    
    let nonceString = "049e73d6e0744a7491c\(changeTimestampString)" // 一次性随机数 32位
    
    // appSecret=c4ca4238a0b923820dcc509a6f75849b&nonce=049e73d6e0744a7491cda2a0a537b90d&timestamp=1466399895704
    // 按照key以升序的方式排序，组合成字符串,使用SHA1WithRSA进行加密,返回小写形式的内容
    // 签名有效期只有一次,每次调用接口时都需要重新生成签名
    let signatureFormat = "appSecret=\(appsecretString)&nonce=\(nonceString)&timestamp=\(changeTimestampString)"
    let signatureString = signatureFormat.sha1()
    print("\(signatureFormat)\n -> \(signatureString)")
    
    return [
        "Content-Type": "application/x-www-form-urlencoded",
        "nonce": nonceString,
        "signature": signatureString,
        "timestamp": changeTimestampString,
    ]
}
 */
