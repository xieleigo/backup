//
//  TRProductGetIssueNumberModel.swift
//  TaxReader
//
//  Created by asdc on 2020/6/26.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON

struct TRProductGetIssueNumberModel: HandyJSON {
    var ret: Bool? = false
    var msgCode: Int = 0
    var msg: String?
    var data: TRProductGetIssueNumberDataModel?
}

struct TRProductGetIssueNumberDataModel: HandyJSON {
    var ret: Bool? = false
    var BatchProduct: [TRProductGetIssueNumberDataBatchModel]?
    var YearProduct: TRProductGetIssueNumberDataYearModel?
    var CurrProduct: TRProductGetIssueNumberDataCurrentModel?
    var ArticleProduct: TRProductGetIssueNumberDataArticleProductModel?
    
    var isHasCurrentPian: Bool? = false
    
    
    var msgCode: Int = 0
    var ArticleID: Int = 0
    var ReadTypeRootID: Int = 0
    var ArticleTitle: String?
    var ArticleAuthorName: String?
    var ArticleCreatTime: String?
    var ArticleUnitName: String?
    var ArticleContent: String?
    var ArticleAbstract: String?
    var IsFavorite: Bool = false
    
    var ArticlePrice: Int = 0
    
    var PubIssueYear: String?
    var PubIssueNum: Int = 0
    var ProdIssue: Int = 0
    var PubID: Int = 0
    var ProID: Int = 0
    var PubIssueID: Int = 0
    var ArticleIOSPrice: String?
}

struct TRProductGetIssueNumberDataYearModel: HandyJSON {
    var ProdIssue: Int = 0
    var ProdIOSPrice: Double = 0.00
    var ReadTypeRootID: Int = 0
    var ReadingTypeID: Int = 0
    var ProdIsDiscount: Int = 0
    var ProdID: Int = 0
    var ProdYear: Int = 0
    var ProdName: String?
    var ProdImg: String?
}

struct TRProductGetIssueNumberDataCurrentModel: HandyJSON {
    var ProdIssue: Int = 0
    var ProdIOSPrice: Double = 0.00
    var ReadTypeRootID: Int = 0
    var ReadingTypeID: Int = 0
    var ProdIsDiscount: Int = 0
    var ProdID: Int = 0
    var ProdYear: Int = 0
    var ProdLevel: Int = 0
    var ProdName: String?
    var ProdImg: String?
}

struct TRProductGetIssueNumberDataBatchModel: HandyJSON {
    var ret: Bool? = false
    var ProdIssue: Int = 0
    var ProdIOSPrice: Double = 0.00
    var ReadTypeRootID: Int = 0
    var ReadingTypeID: Int = 0
    var ProdIsDiscount: Int = 0
    var ProdID: Int = 0
    var ProdYear: Int = 0
    var ProdName: String?
    var ProdImg: String?
    
    var isSelected: Bool = false
}

struct TRProductGetIssueNumberDataArticleProductModel: HandyJSON {
    var ProdImg: String?
    var ProdName: String?
    var ArticleList: String?
    var ProdAbstract: String?
    var ProdID: Int = 0
    var ProdCreator: Int = 0
    var ProdForm: Int = 0
    var ProdStatus: Int = 0
    var ProdYear: Int = 0
    var ReadingTypeID: Int = 0
    var ReadSourceParentID: Int = 0
    var ReadSourceID: String?
    var ProdCreateTime: String?
    var ReadTypeRootID: Int = 0
    var ProdSumIssue: Int = 0
    var ProdIssue: Int = 0
    var ProdIsDiscount: Int = 0
    var ReadSourceType: Int = 0
    var DoMain: String?
    var ProdPrice: Float = 0.0
    var ProdAuthorName: String?
    var ProdIsRCMD: Int = 0
    var ReadSourceURL: String?
    var ProdIsFree: Bool = false
    var ProdType: Int = 0
    var ProdUpdateDate: String?
    var ProdShowStatus: Int = 0
    var ProdLevel: Int = 0
    var ProdIOSPrice: Float = 0.0
    var ReadTypeRootCode: String?
    var ProdUpdateMan: Int = 0
}



/*
    {
      "msg" : "查询成功",
      "msgCode" : "200",
      "data" : {
        "YearProduct" : {
          "ProdUpdateDate" : null,
          "ProdAbstract" : null,
          "ProdIssue" : 0,
          "ProdName" : "中国税务",
          "ProdIOSPrice" : 0,
          "ReadTypeRootID" : 2,
          "ReadingTypeID" : 2,
          "ProdSumIssue" : 0,
          "ProdIsDiscount" : 0,
          "ReadTypeName" : null,
          "ReadSourceURL" : null,
          "ReadSourceParentID" : 10,
          "ProdType" : 30,
          "ProdIsFree" : false,
          "ProdID" : 137,
          "ProdStatus" : 10,
          "ProdLevel" : 1,
          "ReadTypeRootCode" : "qkzjc",
          "ProdForm" : 10,
          "ReadSourceType" : 10,
          "ProdCreator" : 1,
          "ProdCreateTime" : "2020-03-24 12:00:00",
          "ProdYear" : 2020,
          "ReadSourceID" : "2490,2495",
          "ProdUpdateMan" : 0,
          "ProdShowStatus" : 0,
          "ProdIsRCMD" : 0,
          "ArticleList" : null,
          "ProdPrice" : 0,
          "ProdImg" : "\/upload\/image\/202002\/f2d9f4f7-83fd-498f-b34e-a2fd099534e1.jpg",
          "ProdAuthorName" : null
        },
        "CurrProduct" : {
          "ProdUpdateDate" : null,
          "ProdAbstract" : null,
          "ProdIssue" : 1,
          "ProdName" : "中国税务",
          "ProdIOSPrice" : 0.01,
          "ReadTypeRootID" : 1,
          "ReadingTypeID" : 2,
          "ProdSumIssue" : 0,
          "ProdIsDiscount" : 0,
          "ReadTypeName" : null,
          "ReadSourceURL" : "\/Journal\/Detail\/id\/894",
          "ReadSourceParentID" : 10,
          "ProdType" : 10,
          "ProdIsFree" : false,
          "ProdID" : 894,
          "ProdStatus" : 10,
          "ProdLevel" : 1,
          "ReadTypeRootCode" : "qk",
          "ProdForm" : 10,
          "ReadSourceType" : 20,
          "ProdCreator" : 1,
          "ProdCreateTime" : "2020-03-24 12:00:00",
          "ProdYear" : 1984,
          "ReadSourceID" : "894",
          "ProdUpdateMan" : 0,
          "ProdShowStatus" : 1,
          "ProdIsRCMD" : 0,
          "ArticleList" : null,
          "ProdPrice" : 0.01,
          "ProdImg" : "\/upload\/1984\/Layouts\/ZS198401.Source.jpg",
          "ProdAuthorName" : null
        },
        "BatchProduct" : [
          {
            "ProdUpdateDate" : null,
            "ProdAbstract" : null,
            "ProdIssue" : 1,
            "ProdName" : "中国税务",
            "ProdIOSPrice" : 0.01,
            "ReadTypeRootID" : 1,
            "ReadingTypeID" : 2,
            "ProdSumIssue" : 0,
            "ProdIsDiscount" : 0,
            "ReadTypeName" : null,
            "ReadSourceURL" : "\/Journal\/Detail\/id\/894",
            "ReadSourceParentID" : 10,
            "ProdType" : 10,
            "ProdIsFree" : false,
            "ProdID" : 894,
            "ProdStatus" : 10,
            "ProdLevel" : 1,
            "ReadTypeRootCode" : "qk",
            "ProdForm" : 10,
            "ReadSourceType" : 20,
            "ProdCreator" : 1,
            "ProdCreateTime" : "2020-03-24 12:00:00",
            "ProdYear" : 1984,
            "ReadSourceID" : "894",
            "ProdUpdateMan" : 0,
            "ProdShowStatus" : 1,
            "ProdIsRCMD" : 0,
            "ArticleList" : null,
            "ProdPrice" : 0.01,
            "ProdImg" : "\/upload\/1984\/Layouts\/ZS198401.Source.jpg",
            "ProdAuthorName" : null
          },
          {
            "ProdUpdateDate" : null,
            "ProdAbstract" : null,
            "ProdIssue" : 2,
            "ProdName" : "中国税务",
            "ProdIOSPrice" : 0.01,
            "ReadTypeRootID" : 1,
            "ReadingTypeID" : 2,
            "ProdSumIssue" : 0,
            "ProdIsDiscount" : 0,
            "ReadTypeName" : null,
            "ReadSourceURL" : "\/Journal\/Detail\/id\/895",
            "ReadSourceParentID" : 10,
            "ProdType" : 10,
            "ProdIsFree" : false,
            "ProdID" : 895,
            "ProdStatus" : 10,
            "ProdLevel" : 1,
            "ReadTypeRootCode" : "qk",
            "ProdForm" : 10,
            "ReadSourceType" : 20,
            "ProdCreator" : 1,
            "ProdCreateTime" : "2020-03-24 12:00:00",
            "ProdYear" : 1984,
            "ReadSourceID" : "895",
            "ProdUpdateMan" : 0,
            "ProdShowStatus" : 1,
            "ProdIsRCMD" : 0,
            "ArticleList" : null,
            "ProdPrice" : 0.01,
            "ProdImg" : "\/upload\/1984\/Layouts\/ZS198402.Source.jpg",
            "ProdAuthorName" : null
          },
          {
            "ProdUpdateDate" : "2020-05-20 02:14:24",
            "ProdAbstract" : null,
            "ProdIssue" : 3,
            "ProdName" : "中国税务",
            "ProdIOSPrice" : 0.01,
            "ReadTypeRootID" : 1,
            "ReadingTypeID" : 2,
            "ProdSumIssue" : 0,
            "ProdIsDiscount" : 0,
            "ReadTypeName" : null,
            "ReadSourceURL" : "\/Journal\/Detail\/id\/896",
            "ReadSourceParentID" : 10,
            "ProdType" : 10,
            "ProdIsFree" : false,
            "ProdID" : 896,
            "ProdStatus" : 10,
            "ProdLevel" : 1,
            "ReadTypeRootCode" : "qk",
            "ProdForm" : 10,
            "ReadSourceType" : 20,
            "ProdCreator" : 1,
            "ProdCreateTime" : "2020-03-24 12:00:00",
            "ProdYear" : 1984,
            "ReadSourceID" : "896",
            "ProdUpdateMan" : 11,
            "ProdShowStatus" : 1,
            "ProdIsRCMD" : 1,
            "ArticleList" : null,
            "ProdPrice" : 0.01,
            "ProdImg" : "\/upload\/1984\/Layouts\/ZS198403.Source.jpg",
            "ProdAuthorName" : null
          }
        ]
      },
      "ret" : true
    }
 */
