//
//  TRCartInfoModel.swift
//  TaxReader
//
//  Created by asdc on 2020/6/19.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON

struct TRCartInfoModel: HandyJSON {
    var ret: Bool = false
    var msg: String?
    var totalCount: Int = 0
    var pageIndex: Int = 0
    var pageSize: Int = 0
    var msgCode: Int = 0
    var data: [TRCartInfoDataModel]?
}

struct TRCartInfoDataModel: HandyJSON {
    var addDate: String?
    var data: [TRCartInfoDataDataModel]?
}

struct TRCartInfoDataDataModel: HandyJSON {
    var CartItemType: Int = 0
    var Product: TRCartInfoDataDataProductModel?
    var UserID: Int = 0
    var ProductID: Int = 0
    var CartItemAddDate: String?
    var ProductCount: Int = 0
    var DoMain: String?
    var CartItemID: Int = 0
}

struct TRCartInfoDataDataProductModel: HandyJSON {
    var ProdAuthorName: String?
    var ProdUpdateMan: Int = 0
    var ProdPrice: Float = 0.0
    var ProdUpdateDate: String?
    var ProdSumIssue: Int = 0
    var ProdLevel: Int = 0
    var ProdName: String?
    var ProdIsFree: Bool = false
    var ProdYear: Int = 0
    var ProdIOSPrice: Float = 0.0
    var ReadSourceURL: String?
    var DoMain: String?
    var ReadingTypeID: Int = 0
    var ReadTypeRootID: Int = 0
    var ProdStatus: Int = 0
    var ProdIsDiscount: Int = 0
    var ProdImg: String?
    var ProdCreateTime: String?
    var ProdType: Int = 0
    var ReadSourceParentID: Int = 0
    var ProdID: Int = 0
    var ArticleList: String?
    var ProdIssue: Int = 0
    var ReadSourceID: String?
    var ProdCreator: Int = 0
    var ReadSourceType: Int = 0
    var ProdIsRCMD: Int = 0
    var ReadTypeRootCode: String?
    var ProdAbstract: String?
    var ProdShowStatus: Int = 0
    var ProdForm: Int = 0
}



/*
    {
      "msg" : "查询成功",
      "pageSize" : 10,
      "ret" : true,
      "data" : [
        {
          "data" : [
            {
              "CartItemID" : 28,
              "ProductCount" : 3,
              "Product" : {
                "ReadTypeName" : null,
                "ProdIOSPrice" : 0.01,
                "ProdName" : "中国税务",
                "ReadSourceID" : "895",
                "ProdImg" : "\/upload\/1984\/Layouts\/ZS198402.Source.jpg",
                "ProdIsFree" : false,
                "ProdIsRCMD" : 0,
                "ProdCreator" : 1,
                "ProdUpdateDate" : null,
                "ProdShowStatus" : 1,
                "ProdAbstract" : null,
                "ProdPrice" : 0.01,
                "ProdSumIssue" : 0,
                "ReadTypeRootCode" : "qk",
                "ReadSourceURL" : "\/Journal\/Detail\/id\/895",
                "ProdLevel" : 1,
                "ArticleList" : null,
                "ProdForm" : 10,
                "ReadTypeRootID" : 1,
                "ReadSourceParentID" : 10,
                "ProdYear" : 1984,
                "ProdAuthorName" : null,
                "ReadingTypeID" : 2,
                "ProdIssue" : 2,
                "ProdType" : 10,
                "ProdStatus" : 10,
                "ProdID" : 895,
                "ReadSourceType" : 20,
                "ProdIsDiscount" : 0,
                "ProdUpdateMan" : 0,
                "ProdCreateTime" : "2020-03-24 12:00:00"
              }
            }
          ],
          "addDate" : "2020-06-19"
        }
      ],
      "totalCount" : 1,
      "msgCode" : "200",
      "pageIndex" : 1
    }
 
 
 {
   "msg" : "查询成功",
   "msgCode" : "200",
   "pageIndex" : 1,
   "data" : [
     {
       "data" : [
         {
           "CartItemID" : 63,
           "ProductCount" : 4,
           "Product" : {
             "ProdShowStatus" : 1,
             "ProdStatus" : 10,
             "ProdIOSPrice" : 0.01,
             "ReadingTypeID" : 2,
             "ReadSourceType" : 20,
             "ProdIsDiscount" : 0,
             "ReadTypeName" : null,
             "ProdAbstract" : null,
             "ProdName" : "中国税务",
             "ReadTypeRootCode" : "qk",
             "ProdAuthorName" : null,
             "ReadSourceID" : "896",
             "ProdSumIssue" : 0,
             "ProdIssue" : 3,
             "ReadSourceURL" : "\/Journal\/Detail\/id\/896",
             "ProdCreateTime" : "2020-03-24 12:00:00",
             "ArticleList" : null,
             "ProdLevel" : 1,
             "ProdUpdateMan" : 11,
             "ProdIsFree" : false,
             "ProdForm" : 10,
             "ReadSourceParentID" : 10,
             "ProdYear" : 1984,
             "ProdIsRCMD" : 1,
             "ProdID" : 896,
             "ProdCreator" : 1,
             "ProdUpdateDate" : "2020-05-20 02:14:24",
             "ProdPrice" : 0.01,
             "ProdImg" : "\/upload\/1984\/Layouts\/ZS198403.Source.jpg",
             "ReadTypeRootID" : 1,
             "ProdType" : 10
           }
         },
         {
           "CartItemID" : 62,
           "ProductCount" : 4,
           "Product" : {
             "ProdShowStatus" : 1,
             "ProdStatus" : 10,
             "ProdIOSPrice" : 0.01,
             "ReadingTypeID" : 2,
             "ReadSourceType" : 20,
             "ProdIsDiscount" : 0,
             "ReadTypeName" : null,
             "ProdAbstract" : null,
             "ProdName" : "中国税务",
             "ReadTypeRootCode" : "qk",
             "ProdAuthorName" : null,
             "ReadSourceID" : "895",
             "ProdSumIssue" : 0,
             "ProdIssue" : 2,
             "ReadSourceURL" : "\/Journal\/Detail\/id\/895",
             "ProdCreateTime" : "2020-03-24 12:00:00",
             "ArticleList" : null,
             "ProdLevel" : 1,
             "ProdUpdateMan" : 0,
             "ProdIsFree" : false,
             "ProdForm" : 10,
             "ReadSourceParentID" : 10,
             "ProdYear" : 1984,
             "ProdIsRCMD" : 0,
             "ProdID" : 895,
             "ProdCreator" : 1,
             "ProdUpdateDate" : null,
             "ProdPrice" : 0.01,
             "ProdImg" : "\/upload\/1984\/Layouts\/ZS198402.Source.jpg",
             "ReadTypeRootID" : 1,
             "ProdType" : 10
           }
         },
         {
           "CartItemID" : 61,
           "ProductCount" : 14,
           "Product" : {
             "ProdShowStatus" : 1,
             "ProdStatus" : 10,
             "ProdIOSPrice" : 0.01,
             "ReadingTypeID" : 2,
             "ReadSourceType" : 20,
             "ProdIsDiscount" : 0,
             "ReadTypeName" : null,
             "ProdAbstract" : null,
             "ProdName" : "中国税务",
             "ReadTypeRootCode" : "qk",
             "ProdAuthorName" : null,
             "ReadSourceID" : "894",
             "ProdSumIssue" : 0,
             "ProdIssue" : 1,
             "ReadSourceURL" : "\/Journal\/Detail\/id\/894",
             "ProdCreateTime" : "2020-03-24 12:00:00",
             "ArticleList" : null,
             "ProdLevel" : 1,
             "ProdUpdateMan" : 0,
             "ProdIsFree" : false,
             "ProdForm" : 10,
             "ReadSourceParentID" : 10,
             "ProdYear" : 1984,
             "ProdIsRCMD" : 0,
             "ProdID" : 894,
             "ProdCreator" : 1,
             "ProdUpdateDate" : null,
             "ProdPrice" : 0.01,
             "ProdImg" : "\/upload\/1984\/Layouts\/ZS198401.Source.jpg",
             "ReadTypeRootID" : 1,
             "ProdType" : 10
           }
         }
       ],
       "addDate" : "2020-06-28"
     }
   ],
   "totalCount" : 3,
   "pageSize" : 50,
   "ret" : true
 }
 */
