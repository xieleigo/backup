//
//  TRUserInfoModel.swift
//  TaxReader
//
//  Created by asdc on 2020/6/2.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON

struct TRUserInfoModel: HandyJSON {
    var ret: Bool? = false
    var msgCode: Int = 0
    var msg: String?
    var data: TRUserInfoData?
}

struct TRUserInfoData: HandyJSON {
    var UserAnswer: String?
    var UserID: Int = 0
    var CountyId: Int = 0
    var UserUpdateTime: String?
    var UserCreateTime: String?
    var UserLastLoginTime: String?
    var OrganTreePath: String?
    var UserMobile: String?
    var UserPCPass: String?
    var UserMobileAreaCode: String?
    var UserPass: String?
    var UserRegIP: String?
    var UserGender: Int = 0
    var RawPWD: String?
    var UserQuestion: String?
    var AreaID: Int = 0
    var UserRealName: String?
    var UserPostal: String?
    var AreaFullName: String?
    var AllowBrowserPubIDs: String?
    var UserActivity: Int = 0
    var UserBirthDate: String?
    var UserUnit: String?
    var UserPWD: String?
    var UseFrom: Int = 0
    var AreaTreePath: String?
    var UserLastLoginIP: String?
    var UserPost: String?
    var UserTitle: String?
    var UserNickName: String?
    var CityId: Int = 0
    var UserDepart: String?
    var UserLoginCount: Int = 0
    var UserStatus: Int = 0
    var UnitID: Int = 0
    var UserAddress: String?
    var UserName: String?
    var ProvinceId: Int = 0
    var UserImage: String?
    var UserValidStartTime: String?
    var CountryId: Int = 0
    var OrganFullName: String?
    var DoMain: String?
    var UserLevel: Int = 0
    var BrowserPowerList: String?
    var AreaName: String?
    var UserOnLineID: String?
    var OrganName: String?
    var UserEmail: String?
    var UserRegTime: String?
    var UserValidFinishTime: String?
}

/*
    info = {
      "data" : {
        "UserEmail" : "",
        "CountyId" : 0,
        "UserName" : "15711351011",
        "UserMobileAreaCode" : "+86",
        "AreaName" : null,
        "UserPostal" : "",
        "UnitID" : 0,
        "OrganTreePath" : "",
        "UserStatus" : 10,
        "CityId" : 0,
        "UserGender" : 1,
        "AreaFullName" : " 北京市 东城区",
        "UserTitle" : "",
        "UserNickName" : "15711351011",
        "ProvinceId" : 0,
        "UserBirthDate" : "2020-06-01 12:00:00",
        "UserPWD" : "KZhi1kKBGlnMMau7xnTkxg==",
        "UserID" : 4459,
        "UserQuestion" : "",
        "UserPost" : "",
        "UserLevel" : 0,
        "UserRegTime" : "2020-06-01 01:30:08",
        "UserDepart" : "",
        "UserActivity" : 0,
        "UserLoginCount" : 50,
        "UseFrom" : 20,
        "UserAnswer" : "",
        "UserImage" : "",
        "UserLastLoginTime" : "2020-06-02 09:23:05",
        "OrganName" : null,
        "UserValidStartTime" : null,
        "UserPCPass" : "",
        "UserRealName" : "哈哈哈",
        "UserAddress" : "",
        "UserValidFinishTime" : null,
        "UserCreateTime" : "2020-06-01 01:30:08",
        "UserUpdateTime" : null,
        "UserMobile" : "15711351011",
        "UserUnit" : "",
        "AreaTreePath" : "4000-1-2",
        "UserRegIP" : "192.168.1.132",
        "CountryId" : 0,
        "UserPass" : "",
        "AreaID" : 2,
        "UserLastLoginIP" : "172.25.75.62",
        "OrganFullName" : ""
      },
      "ret" : true,
      "msg" : "查询成功",
      "msgCode" : "200"
    }

 */
