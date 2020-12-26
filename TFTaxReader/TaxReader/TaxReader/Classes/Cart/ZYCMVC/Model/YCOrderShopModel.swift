//
//  YCOrderShopModel.swift
//  learnSwift
//
//  Created by zyc on 2019/12/23.
//  Copyright © 2019 张言超. All rights reserved.
//

import UIKit

class YCOrderShopModel: NSObject {


    /** 店铺名字 */
    var shopName:String?
    var isFromArticleBenPian: Bool? = false
    
    /** 店铺商品 */
    var goodsArr:[YCOrderGoodsModel]?
    /** 是否是全部选中状态 */
    var shopIsAllSelected:Bool = false
    
    override func setValue(_ value: Any?, forKey key: String) {
        if key == "data" {
            var goodsModelArr = [YCOrderGoodsModel]()
            let tempArr:NSArray = value as! NSArray
            for index in 0..<tempArr.count {
                let dict:NSDictionary = tempArr[index] as! NSDictionary
                let model = YCOrderGoodsModel.init()
                model.CartItemID = dict["CartItemID"] as? Int
                model.ProductCount = dict["ProductCount"] as? Int
                model.shopName = dict["shopName"] as? String
                
                model.goodsIsSelect = false
                model.goodsCount = 1;
                
                let productModel = TROrderProductModel.init()
                let proDict: NSDictionary = dict["Product"] as! NSDictionary
                productModel.ProdName = proDict["ProdName"] as? String
                productModel.ProdPrice = proDict["ProdPrice"] as? Double 
                productModel.ProdIOSPrice = proDict["ProdIOSPrice"] as? Double
                productModel.ProdImg = proDict["ProdImg"] as? String
                productModel.ProdYear = proDict["ProdYear"] as? Int
                productModel.ProdIssue = proDict["ProdIssue"] as? Int
                productModel.ProdType = proDict["ProdType"] as? Int
                productModel.ReadSourceID = proDict["ReadSourceID"] as? String
                productModel.ReadSourceParentID = proDict["ReadSourceParentID"] as? Int
                productModel.ReadSourceType = proDict["ReadSourceType"] as? Int
                
                model.Product = productModel
                
                goodsModelArr.append(model)
            }
            self.goodsArr = goodsModelArr
        }else if key == "addDate"{
            self.shopName = value as? String
        }
    }
    
    override func value(forUndefinedKey key: String) -> Any? {
        return nil
    }
    
    
    /*
    /** 店铺名字 */
    var shopName:String?
    /** 店铺商品 */
    var goodsArr:[YCOrderGoodsModel]?
    /** 是否是全部选中状态 */
    var shopIsAllSelected:Bool = false
    
    override func setValue(_ value: Any?, forKey key: String) {
        if key == "commoditys" {
            var goodsModelArr = [YCOrderGoodsModel]()
            let tempArr:NSArray = value as! NSArray
            for index in 0..<tempArr.count {
                let dict:NSDictionary = tempArr[index] as! NSDictionary
                let model = YCOrderGoodsModel.init()
                model.goodsName = dict["commodityName"] as? String
                model.goodsDescription = dict["commodityDescription"] as? String
                model.goodsPrice = dict["commodityPrice"] as? String
                model.shopName = dict["shopName"] as? String
                model.goodsIsSelect = false
                model.goodsCount = 1;
                goodsModelArr.append(model)
            }
            self.goodsArr = goodsModelArr
        }else if key == "shopName"{
            self.shopName = value as? String
        }
    }
    
    override func value(forUndefinedKey key: String) -> Any? {
        return nil
    }
 */
    

}
