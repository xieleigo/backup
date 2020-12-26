//
//  TRArticleDetailModel.swift
//  TaxReader
//
//  Created by asdc on 2020/6/19.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON

struct TRArticleDetailModel: HandyJSON {
    var ret: Bool = false
    var msgCode: Int = 0
    var msg: String?
    var data: TRArticleDetailDataModel?
}

struct TRArticleDetailDataModel: HandyJSON {
    var ArticleReadCount: Int = 0
    var ArticleEngKeys: String?
    var ArticleCreator: Int = 0
    var PubIssueNum: Int = 0
    var DoMain: String?
    var PubIssueCataID: Int = 0
    var ArticleLastModTime: String?
    var ArticleBuyCount: Int = 0
    var ArticleCreatTime: String?
    var ArticleSourceType: Int = 0
    var ArticlePDF: String?
    var PubIssueName: String?
    var ReadTypeRootID: Int = 0
    var ArticleFixWordsPrice: Float = 0.0
    var ArticleStatus: Int = 0
    var ArticleEngUnitName: String?
    var ProID: Int = 0
    var IsFavorite: Bool = false
    var PubName: String?
    var ArticleContent: String?
    var ArticleAbstract: String?
    var ArticleIOSPrice: Float = 0.0
    var ArticleTitle: String?
    var PubIssueCataName: String?
    var ArticleDownloadCount: Int = 0
    var ArticleWordsCount: Int = 0
    var ArticleEngAuthorName: String?
    var ReadTypeID: Int = 0
    var ArticleEngTitle: String?
    var ArticleIsFree: Bool = false
    var ArticleAuthorName: String?
    var PubID: Int = 0
    var ArticleEditor: String?
    var ArticleHTMLTitle: String?
    var PubIssueYear: Int = 0
    var ArticleIOSWordsPrice: Float = 0.0
    var ArticleSubTitle: String?
    var ArticleLastModifier: Int = 0
    var ArticleLevel: Int = 0
    var ArticleHTMLContent: String?
    var ArticleFixWords: Int = 0
    var ArticleReadTimeCount: Int = 0
    var ArticleClickCount: Int = 0
    var ArticlePagPages: String?
    var ArticleSortNo: Int = 0
    var ArticleIOSWords: Int = 0
    var PubIssueID: Int = 0
    var ArticleID: Int = 0
    var ArticleKeys: String?
    var PubIssueLastArticle: Bool = false
    var SourceUrl: String?
    var ArticlePrice: Float = 0.0
    var ArticleUnitName: String?
}


/*
    {
      "ret" : true,
      "msg" : "查询成功",
      "data" : {
        "ArticleIsFree" : true,
        "ArticleTitle" : "莫把社会主义税收法“绳索”",
        "ArticleEditor" : null,
        "ArticleEngUnitName" : null,
        "ArticleFixWordsPrice" : 0.20000000000000001,
        "ArticleEngAuthorName" : null,
        "ArticleClickCount" : 0,
        "PubIssueYear" : 0,
        "ArticleWordsCount" : 3142,
        "PubIssueName" : null,
        "ReadTypeID" : 2,
        "ArticleIOSPrice" : 2,
        "ArticleSourceType" : 0,
        "CataOrder" : 1,
        "ArticleKeys" : null,
        "ArticlePDF" : null,
        "ReadTypeRootID" : 2,
        "ArticleEngTitle" : null,
        "ArticleFixWords" : 300,
        "ArticleHTMLTitle" : "<p><br><\/p>",
        "ArticleDownloadCount" : 0,
        "ArticleIOSWords" : 300,
        "ArticleAbstract" : "近来，经常听到一些部门、企业借“松绑”之名，指责税务干部收税“收多了”，“收狠了”，把某些企业和个体户“收垮了”，甚至把税收称做“苛捐杂税”，看作是“捆绑”商品生产发展的“绳索”，主张“财政不监督，税务不进厂”。笔者认为，这种观点是一种偏见，一种糊涂认识。把社会主义税收看作是“捆绑”商品生产发展的“绳索”，是对社会主义税收的地位、作用缺乏应有的认识，社会主义国家的税收，是取之于民用之于民的。国家进 ……",
        "ArticlePrice" : 2,
        "ArticleReadTimeCount" : 0,
        "ArticleIOSWordsPrice" : 0.20000000000000001,
        "PubIssueID" : 894,
        "ArticleCreatTime" : "2020-03-31 12:00:00",
        "PubIssueNum" : 0,
        "ArticleAuthorName" : "沈雪",
        "PubName" : null,
        "ArticleEngKeys" : null,
        "ArticleReadCount" : 10,
        "ArticleLastModTime" : "2020-06-19 04:23:13",
        "ArticleBuyCount" : 0,
        "ArticleLevel" : 1,
        "IsFavorite" : true,
        "ArticlePagPages" : "14",
        "ArticleStatus" : 5,
        "PubIssueCataName" : null,
        "PubIssueCataID" : 28628,
        "ArticleCreator" : 1,
        "ArticleLastModifier" : 11,
        "ArticleUnitName" : null,
        "ArticleSubTitle" : null,
        "ArticleID" : 28628,
        "PubID" : 10
      },
      "msgCode" : "200"
    }
 */
