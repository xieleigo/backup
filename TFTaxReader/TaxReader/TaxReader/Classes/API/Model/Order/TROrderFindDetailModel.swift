//
//  TROrderFindDetailModel.swift
//  TaxReader
//
//  Created by asdc on 2020/6/29.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON

struct TROrderFindDetailModel: HandyJSON {
    var msgCode: Int = 0
    var ret: Bool = false
    var msg: String?
    var data: TROrderFindDetailDataModel?
}

struct TROrderFindDetailDataModel: HandyJSON {
    var OrderStatus: Int = 0
    var PayModel: Int = 0
    var OrderRemarks: String?
    var UserName: String?
    var Order_token: String?
    var OrderSerialCode: String?
    var OrderInvoice: TROrderFindDetailDataOrderInvoiceModel?
    var OrderIOSFeeMoney: Int = 0
    var Order_failMsg: String?
    var DoMain: String?
    var Order_tradeNo: String?
    var Order_fee: Int = 0
    var Order_refundOrderId: String?
    var OrderForm: Int = 0
    var Order_requestId: String?
    var OrderDate: String?
    var OrderMoney: Int = 0
    var OrderID: Int = 0
    var OrderInvoiceDueDate: String?
    var UserID: Int = 0
    var OrderIOSMoney: Int = 0
    var OrderFeeMoney: Int = 0
    var OrderPayDate: String?
    var OrderInvoiceStatus: Int = 0
    var OrderConsignee: TROrderFindDetailDataOrderConsigneeModel?
    var OrderDetail: [TROrderFindDetailDataOrderDetailModel]?
}

struct TROrderFindDetailDataOrderInvoiceModel: HandyJSON {
    var OrderInvoiceRemarks: String?
    var OrderID: Int = 0
    var OrderInvoiceContent: String?
    var OrderInvoicePayee: String?
    var OrderInvoiceType: Int = 0
    var OrderInvoiceValCode: String?
    var OrderInvoiceBuyerTel: String?
    var OrderInvoiceID: Int = 0
    var OrderInvoiceReceiveType: Int = 0
    var OrderInvoiceBuyerBankName: String?
    var RecvPdf51Url: String?
    var OrderInvoiceTaxpayerNo: String?
    var OrderInvoiceorAddress: String?
    var OrderInvoiceReceiveType_str: String?
    var OrderInvoicePhone: String?
    var OrderMoney: Int = 0
    var OrderInvoiceProperty: Int = 0
    var OrderInvoiceCity: String?
    var OrderInvoiceDrawer: String?
    var OrderInvoiceTitle: String?
    var DoMain: String?
    var OrderInvoiceMinus_flag: String?
    var OrderInvoiceDate: String?
    var OrderInvoiceBankAcount: String?
    var OrderInvoiceProv: String?
    var OrderInvoiceEXT: String?
    var OrderInvoiceNumber: String?
    var OrderInvoiceReceiver: String?
    var UserInvoiceID: Int = 0
    var OrderInvoiceEmail: String?
    var RecvPdfUrl: String?
    var OrderInvoiceCode: String?
    var OrderInvoiceChecker: String?
}

struct TROrderFindDetailDataOrderDetailModel: HandyJSON {
    var PubProductID: Int = 0
    var PubProductMedia: Int = 0
    var PubProductName: String?
    var DoMain: String?
    var PubProductPrice: Int = 0
    var PubProductIOSPrice: Int = 0
    var OrderDetailSN: String?
    var OrderID: Int = 0
    var OrderDetailCount: Int = 0
    var PubProductFeePrice: Int = 0
    var OrderDetailtMoney: Int = 0
    var PubProductIOSFeePrice: Int = 0
    var OrderDetailID: Int = 0
    var Product: TROrderFindDetailDataOrderDetailProductModel?
}

struct TROrderFindDetailDataOrderDetailProductModel: HandyJSON {
    var ProdLevel: Int = 0
    var ProdUpdateDate: String?
    var ArticleList: String?
    var ProdIsDiscount: Int = 0
    var ProdIsFree: Bool = false
    var ReadTypeRootCode: String?
    var ReadSourceURL: String?
    var ProdShowStatus: Int = 0
    var ReadTypeRootID: Int = 0
    var ProdForm: Int = 0
    var ProdPrice: Int = 0
    var ProdImg: String?
    var ProdUpdateMan: Int = 0
    var DoMain: String?
    var ReadingTypeID: Int = 0
    var ProdCreator: Int = 0
    var ProdAuthorName: String?
    var ProdIsRCMD: Int = 0
    var ProdIssue: Int = 0
    var ReadSourceID: String?
    var ReadSourceType: Int = 0
    var ProdID: Int = 0
    var ProdName: String?
    var ProdAbstract: String?
    var ProdType: Int = 0
    var ProdYear: Int = 0
    var ProdIOSPrice: Int = 0
    var ProdCreateTime: String?
    var ProdStatus: Int = 0
    var ReadSourceParentID: Int = 0
    var ProdSumIssue: Int = 0
}

struct TROrderFindDetailDataOrderConsigneeModel: HandyJSON {
    var OrderForm: Int = 0
    var OrderStatus: Int = 0
    var OrderConsCity: String?
    var OrderID: Int = 0
    var OrderConsProvince: String?
    var OrderCons: String?
    var OrderConsTel: String?
    var OrderConsDetail: String?
}




/*
    {
      "msgCode" : "200",
      "data" : {
        "OrderDetail" : [
          {
            "OrderDetailtMoney" : 15,
            "PubProductName" : "中国税务2020年第7期",
            "PubProductFeePrice" : 15,
            "OrderID" : 120,
            "OrderDetailSN" : "fb395d71ce3e4ad586d5e9379c99d401",
            "Product" : {
              "ReadSourceID" : "2500",
              "ProdSumIssue" : 0,
              "ProdUpdateMan" : 11,
              "ProdCreateTime" : "2020-03-24 12:00:00",
              "ProdAuthorName" : "",
              "ReadSourceParentID" : 10,
              "ReadTypeRootCode" : "qk",
              "ProdIssue" : 7,
              "ReadSourceURL" : "\/Journal\/Detail\/id\/2500",
              "ProdIOSPrice" : 15,
              "ProdStatus" : 10,
              "ReadTypeRootID" : 1,
              "ProdImg" : "\/upload\/2020\/Layouts\/ZS202007.Source.jpg",
              "ProdForm" : 10,
              "ProdType" : 10,
              "ProdUpdateDate" : "2020-08-03 11:24:05",
              "DoMain" : "http:\/\/210.12.84.109\/",
              "ProdLevel" : 1,
              "ProdID" : 2500,
              "ProdShowStatus" : 1,
              "ProdPrice" : 15,
              "ProdYear" : 2020,
              "ProdName" : "中国税务",
              "ArticleList" : null,
              "ReadSourceType" : 20,
              "ProdIsDiscount" : 0,
              "ProdCreator" : 1,
              "ProdAbstract" : "",
              "ReadingTypeID" : 2,
              "ProdIsRCMD" : 1,
              "ProdIsFree" : false
            },
            "DoMain" : "http:\/\/210.12.84.109\/",
            "OrderDetailID" : 128,
            "PubProductPrice" : 15,
            "PubProductMedia" : 10,
            "OrderDetailCount" : 1,
            "PubProductID" : 2500,
            "PubProductIOSPrice" : 0,
            "PubProductIOSFeePrice" : 0
          }
        ],
        "Order_refundOrderId" : "",
        "OrderIOSMoney" : 0,
        "OrderIOSFeeMoney" : 0,
        "UserID" : 4574,
        "Order_requestId" : "3005affed06b4592b4f50af329bd7bd7",
        "UserName" : "zhengborang",
        "PayModel" : 4,
        "OrderMoney" : 15,
        "OrderSerialCode" : "637326689483537364",
        "Order_failMsg" : "",
        "Order_token" : "",
        "DoMain" : "http:\/\/210.12.84.109\/",
        "OrderForm" : 10,
        "OrderDate" : "2020-08-10 03:09:08",
        "OrderInvoiceStatus" : 30,
        "OrderInvoice" : {
          "InvoiceDetail" : [

          ],
          "OrderInvoiceCity" : "",
          "RecvPdfUrl" : "",
          "OrderInvoiceBankAcount" : "",
          "OrderInvoiceEmail" : null,
          "UserInvoiceID" : 7,
          "OrderInvoiceReceiver" : "",
          "DoMain" : "http:\/\/210.12.84.109\/",
          "OrderInvoiceTitle" : "中税和",
          "OrderInvoiceorAddress" : null,
          "OrderInvoiceValCode" : "",
          "OrderInvoiceContent" : null,
          "OrderInvoiceChecker" : "",
          "OrderInvoiceMinus_flag" : "",
          "OrderInvoiceNumber" : "",
          "OrderInvoiceBuyerTel" : null,
          "OrderInvoiceProv" : "",
          "OrderInvoiceReceiveType_str" : "企业",
          "OrderInvoicePayee" : "",
          "OrderInvoiceProperty" : 1,
          "OrderInvoiceDate" : "2020-08-10 03:09:08",
          "RecvPdf51Url" : "https:\/\/einvoicelink.51fapiao.cn:8181\/FPFX\/actions\/d0bce0637fa6a2e0a074d384e10a8df906826d",
          "OrderInvoiceID" : 18,
          "OrderInvoiceRemarks" : "",
          "OrderMoney" : 15,
          "OrderInvoiceTaxpayerNo" : "91110106755278449Y",
          "OrderInvoiceBuyerBankName" : null,
          "OrderInvoicePhone" : "13621078746",
          "OrderInvoiceEXT" : "",
          "OrderInvoiceCode" : "",
          "OrderInvoiceReceiveType" : 2,
          "OrderID" : 120,
          "OrderInvoiceType" : 1,
          "OrderInvoiceDrawer" : ""
        },
        "Order_fee" : 0,
        "OrderInvoiceDueDate" : "2020-08-10 03:09:08",
        "OrderPayDate" : null,
        "OrderRemarks" : "",
        "OrderID" : 120,
        "OrderConsignee" : null,
        "OrderStatus" : 3,
        "OrderFeeMoney" : 15,
        "Order_tradeNo" : "20200810742399186544234496"
      },
      "msg" : "查询成功",
      "ret" : true
    }
 */
