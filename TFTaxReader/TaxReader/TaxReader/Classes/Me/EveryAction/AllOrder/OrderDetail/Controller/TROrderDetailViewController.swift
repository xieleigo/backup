//
//  TROrderDetailViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/6/28.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON
import SwiftyJSON

class TROrderDetailViewController: UIViewController {
//    // bendi
//    var orderModel: TROrderModel?
//    var dataArr: [TROrderDataModel]? = []
    
    var orderID: String?
    convenience init(orderID: String?) {
        self.init()
        self.orderID = orderID
    }
    
    // network
    var orderFindDetailModel: TROrderFindDetailModel?
    var dataArr: [TROrderFindDetailDataOrderDetailModel]? = []
    
    var payModelSelectedItem: String? = "0"
    
    lazy var navView: UIView = {
        let view = LXNavigationBarView.init(frame: .zero)
        view.titleString = "订单详情"
        view.navBackButtonClick = {[weak self]() in
            self?.navigationController?.popViewController(animated: true)
        }
        
        return view
    }()
    
    private var page: Int = 1
    private let TROrderDetailHeaderViewID = "TROrderDetailHeaderView"
    private let TROrderDetailFooterViewID = "TROrderDetailFooterView"
    private let TROrderTableViewCellID = "TROrderTableViewCell"
    lazy var tableView: UITableView = {
        let tableView = UITableView.init(frame: .zero, style: UITableView.Style.grouped)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.backgroundColor = UIColor.white
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.singleLine
        
        tableView.register(TROrderDetailHeaderView.self, forHeaderFooterViewReuseIdentifier: TROrderDetailHeaderViewID)
        tableView.register(TROrderDetailFooterView.self, forHeaderFooterViewReuseIdentifier: TROrderDetailFooterViewID)
        tableView.register(TROrderTableViewCell.self, forCellReuseIdentifier: TROrderTableViewCellID)
        
        return tableView
    }()
    
    func setupLayout() {
        self.view.addSubview(self.navView)
        self.navView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(LXNavBarHeight)
        }
        
        self.view.addSubview(self.tableView)
        self.tableView.snp.makeConstraints { (make) in
            make.top.equalTo(self.navView.snp.bottom)
            make.left.bottom.right.equalToSuperview()
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.lightGray
        setupLayout()
        NetworkOrderFindDetail(more: false)
    }
    
    lazy var networkViewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TROrderDetailViewController {
    func NetworkOrderFindDetail(more: Bool) {
        networkViewModel.updateBlock = {[unowned self] in
            if self.networkViewModel.orderFindDetailModel?.ret == false {
                MBProgressHUD.showWithText(text: self.networkViewModel.orderFindDetailModel?.msg ?? "", view: self.view)
                
                return
            }
            self.orderFindDetailModel = self.networkViewModel.orderFindDetailModel
            self.dataArr = self.networkViewModel.orderFindDetailModel?.data?.OrderDetail
            self.tableView.reloadData()
        }
        
        networkViewModel.refreshDataSource_OrderFindDetail(OrderID: self.orderID ?? "", PageIndex: "", PageSize: "")
    }
}

extension TROrderDetailViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataArr?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 128
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let dataModel: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
        let detailModel: TROrderFindDetailDataOrderDetailModel = self.dataArr?[indexPath.row] ?? TROrderFindDetailDataOrderDetailModel.init()
        
        let cell: TROrderTableViewCell = tableView.dequeueReusableCell(withIdentifier: TROrderTableViewCellID, for: indexPath) as! TROrderTableViewCell
        cell.findDetailDataModel = dataModel
        cell.findDetailDataDetailModel = detailModel
        cell.delegate = self
        cell.isHasTicketButton = false
        
        cell.invoiceButton.tag = indexPath.row
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
            tableView.deselectRow(at: indexPath, animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 30 + 64
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let model: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
        let headerView: TROrderDetailHeaderView = tableView.dequeueReusableHeaderFooterView(withIdentifier: TROrderDetailHeaderViewID) as! TROrderDetailHeaderView
        headerView.titleString = model.OrderDate
        headerView.findDetailDataModel = model
        
        return headerView
    }
    
    func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        let headerView: UITableViewHeaderFooterView = view as! UITableViewHeaderFooterView
        headerView.contentView.backgroundColor = UIColor.lightGray
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        let model: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
        // return model.OrderStatus == 1 ? 66 + 134 : 30 + 134
        
        var cellHeight = 30 + 150
        if model.OrderStatus == NetDataOrderStatus {// 立即支付按钮
            cellHeight += 36
        }
        
        if model.OrderInvoiceStatus == NetDataOrderInvoiceStatus { // 补开发票按钮
            cellHeight += 36
        }
        
        return CGFloat(cellHeight)
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        let footerView: TROrderDetailFooterView = tableView.dequeueReusableHeaderFooterView(withIdentifier: TROrderDetailFooterViewID) as! TROrderDetailFooterView
        
       let model: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
        footerView.isHasActionPayView = model.OrderStatus == NetDataOrderStatus ? true : false
        footerView.isHasActionInvoiceView = model.OrderInvoiceStatus == NetDataOrderInvoiceStatus ? true : false
        footerView.delegate = self
        footerView.findDetailDataDetailModelArray = model.OrderDetail
        footerView.findDetailDataModel = model
        
        return footerView
    }
    
    func tableView(_ tableView: UITableView, willDisplayFooterView view: UIView, forSection section: Int) {
        let footerView: UITableViewHeaderFooterView = view as! UITableViewHeaderFooterView
        footerView.contentView.backgroundColor = UIColor.white
    }
}

extension TROrderDetailViewController: TROrderTableViewCellDelegate{
    func cellInvoiceButtonDidSelected(button: UIButton, cell: TROrderTableViewCell) {
        let dataModel: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
        var dataModelOwn: TROrderFindDataModel = TROrderFindDataModel.init()
        var dataModelOwnInvoice: TROrderFindDataInvoiceModel = TROrderFindDataInvoiceModel.init()
        dataModelOwnInvoice.RecvPdf51Url = dataModel.OrderInvoice?.RecvPdf51Url
        dataModelOwn.OrderInvoice = dataModelOwnInvoice
        
        let nextVc = TRInvoiceTicketViewController(dataModel: dataModelOwn)
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
    
    func cellCodeButtonDidSelected(button: UIButton, cell: TROrderTableViewCell) {
        let nextVc = TROrderCodeViewController(orderDetailID: "\(cell.detailModel?.OrderDetailID ?? 0)")
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}

extension TROrderDetailViewController: TROrderDetailFooterViewDelegate{
    func cellDidSelectRowAt(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("点击行")
    }
    
    func viewCancelOrderButtonDidSelected(button: UIButton, view: TROrderDetailFooterView) {
        print("取消订单")
    }
    
    func viewPayButtonDidSelected(button: UIButton, view: TROrderDetailFooterView){
        let nextVc = TROrderPaytypeViewController()
        nextVc.presentBackCommitBlock = {[unowned self](payModelSelectedItem) in
            self.payModelSelectedItem = payModelSelectedItem
            
            // 调用二次创建订单接口，开始支付
            let model: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
            let patModel = self.payModelSelectedItem == "0" ? PayModelAliLKL : PayModelWeixin
            self.NetworkOrderSecpay(payModel: patModel, orderID: "\(model.OrderID)")
        }
        
        self.present(nextVc, animated: true, completion: nil)
    }
    
    func viewInvoiceButtonDidSelected(button: UIButton, view: TROrderDetailFooterView) {
        let nextVc = TROrderTicketTypeViewController()
        nextVc.presentBackCommitBlock = {[unowned self](userInvoiceID, selectedInvoiceType) in
            let model: TROrderFindDetailDataModel = self.orderFindDetailModel?.data ?? TROrderFindDetailDataModel.init()
            self.NetworkOrderRebuildInvoice(OrderID: "\(model.OrderID)", UserInvoiceID: userInvoiceID)
        }
        let nextNavc = UINavigationController(rootViewController: nextVc)
        self.present(nextNavc, animated: true, completion: nil)
    }
}

extension TROrderDetailViewController {
    // 补开发票
    func NetworkOrderRebuildInvoice(OrderID: String!, UserInvoiceID: String!) {
        networkViewModel.updateBlock = {[unowned self] in
            MBProgressHUD.showWithText(text: self.networkViewModel.orderRebuildInvoiceModel?.msg ?? "", view: self.view)
            if self.networkViewModel.orderRebuildInvoiceModel?.ret == false {
                return
            }
        }
        
        networkViewModel.refreshDataSource_OrderRebuildInvoice(OrderID: OrderID,
                                                               PayModel: "",
                                                               OrderForm: "",
                                                               UserAddressID: "",
                                                               UserInvoiceID: UserInvoiceID)
    }
    
    // 去支付
    func NetworkOrderSecpay(payModel: String, orderID: String) {
        networkViewModel.updateBlock = {[unowned self] in
            
            if self.networkViewModel.orderCreateModel?.ret == false {
                MBProgressHUD.showWithText(text: self.networkViewModel.orderCreateModel?.msg ?? "", view: self.view)
                return
            }

            
            if payModel == PayModelAliLKL {
                self.payAliLKL(orderCreateData: self.networkViewModel.orderCreateModel?.data ?? TROrderCreateDataModel.init())
            }
            if payModel == PayModelWeixin {
                self.payWeixin(orderCreateData: self.networkViewModel.orderCreateModel?.data ?? TROrderCreateDataModel.init())
            }
        }
        
        networkViewModel.refreshDataSource_OrderSecpay(OrderForm: OrderForm, PayModel: payModel, OrderID: orderID)
    }
    
    func payAliLKL(orderCreateData: TROrderCreateDataModel) {
        let myURL_APP_A = URL.init(string: "alipay:")
        let isCanOpen = UIApplication.shared.canOpenURL(myURL_APP_A!)
        if !isCanOpen {
            MBProgressHUD.showWithText(text: "没有安装支付宝", view: self.view)
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                self.navigationController?.popViewController(animated: true)
            }
            return
        }
        
        /* demo的值
        {
            code = 000000;
            data =     {
                h5JumpUrl = "aHR0cHM6Ly9pbnRwYXkubGFrYWxhLmNvbS9sa2wvaHRtbC9zY2FucGF5L2luZGV4Lmh0bWw/a215UGpSSWc0UTBXS2dIbGxSeEp0ckRyQkNYMW5rd01zclFlcjFyNHNtTU93Y3k2VW5qOHY0d2h0eTcwNWwxcQ==";
                merchantId = 872100010015005;
                orderId = "d9885d11-310c-407d-8";
                returnCode = 000000;
                returnMessage = SUCCESS;
                secretKey = "<null>";
                success = 1;
                token = 20200624RPM0020200624725381024422436864;
                tradeNo = 20200624725381024422436864;
                tradeType = ALIPAYAPP;
                xcxReqpath = "<null>";
                xcxUsername = "<null>";
            };
            message = "\U4ea4\U6613\U6210\U529f";
        }
         */
        
        /* 后台数据拼接的
            {
                code = 000000;
                data =     {
                    h5JumpUrl = "aHR0cHM6Ly9pbnRwYXkubGFrYWxhLmNvbS9sa2wvaHRtbC9zY2FucGF5L2luZGV4Lmh0bWw/anB0b1g5Q1UyZ0RBZGk1bXpaclYyWmxpdlZ2RmJxVHBpZ25taytUY2RHR25ieGZJKzY1aHI1RmdReXAyeVFYOQ==";
                    merchantId = 872100016015000;
                    orderId = 637292215182073679;
                    returnCode = 0;
                    returnMessage = SUCCESS;
                    success = 1;
                    token = 20200701RPM0020200701727939483168808960;
                    tradeNo = 20200701727939483168808960;
                    tradeType = ALIPAYAPP;
                    xcxReqpath = "";
                    xcxUsername = "";
                };
                message = "\U4ea4\U6613\U6210\U529f";
            }
         */
        let dataObject:NSMutableDictionary = NSMutableDictionary.init()
        dataObject.setValue(orderCreateData.h5JumpUrl, forKey: "h5JumpUrl")
        dataObject.setValue(orderCreateData.merchantId, forKey: "merchantId")
        dataObject.setValue(orderCreateData.orderId, forKey: "orderId")
        dataObject.setValue("\(orderCreateData.returnCode)", forKey: "returnCode")
        dataObject.setValue(orderCreateData.returnMessage, forKey: "returnMessage")
        dataObject.setValue(orderCreateData.secretKey, forKey: "secretKey")
        dataObject.setValue("1", forKey: "success")
        dataObject.setValue(orderCreateData.token, forKey: "token")
        dataObject.setValue(orderCreateData.tradeNo, forKey: "tradeNo")
        dataObject.setValue("ALIPAYAPP", forKey: "tradeType")
        dataObject.setValue("", forKey: "xcxReqpath")
        dataObject.setValue("", forKey: "xcxUsername")
        
        let responseObject:NSMutableDictionary = NSMutableDictionary.init()
        responseObject.setValue("000000", forKey: "code")
        responseObject.setValue(dataObject, forKey: "data")
        responseObject.setValue("交易成功", forKey: "message")
        
        print("responseObject = \(responseObject)")
        PaymaxSDK.pay(withAliToken: responseObject as! [AnyHashable : Any], viewController: self) { (result) in
            print("resultType = \(Int(result.type.rawValue))")
            switch result.type {
            case .PAYMAX_CODE_SUCCESS:
                self.pushToPaySuccessVc()
                break
            case .PAYMAX_CODE_FAIL_CANCEL:
                break
            case .PAYMAX_CODE_ERROR_DEAL:
                break
            case .PAYMAX_CODE_FAILURE:
                break
            case .PAYMAX_CODE_ERROR_CONNECT:
                break
            case .PAYMAX_CODE_CHANNEL_WRONG:
                break
            case .PAYMAX_CODE_ERROR_CHARGE_PARAMETER:
                break
            case .PAYMAX_CODE_ERROR_WX_NOT_INSTALL:
                break
            case .PAYMAX_CODE_ERROR_WX_NOT_SUPPORT_PAY:
                break
            case .PAYMAX_CODE_ERROR_WX_UNKNOW:
                break
            @unknown default:
                break
            }
        }
    }
    
    func payWeixin(orderCreateData: TROrderCreateDataModel) {
        /*
            {
              "msgCode" : "200",
              "data" : {
                "sign" : "616CB993AE38C77E006D4A683C58F7E4",
                "appid" : "wx8592574799e9e9ba",
                "nonce_str" : "pAjStTPcv2UcgSLF",
                "timeStamp" : "1593653755",
                "prepay_id" : "wx020935347180998538f31f461986871100",
                "result_code" : "SUCCESS",
                "trade_type" : "APP",
                "orderPKID" : 341,
                "mch_id" : "1503726251",
                "OrderID" : "637292793551217531"
              },
              "ret" : true,
              "msg" : "订单创建成功"
            }
         */
        
//        let req = WXApiRequestHandler.jumpToBizPay()
//        print("req = \(req ?? "")")
        
//        /** 商家向财付通申请的商家id */
//        @property (nonatomic, retain) NSString *partnerId;
//        /** 预支付订单 */
//        @property (nonatomic, retain) NSString *prepayId;
//        /** 随机串，防重发 */
//        @property (nonatomic, retain) NSString *nonceStr;
//        /** 时间戳，防重发 */
//        @property (nonatomic, assign) UInt32 timeStamp;
//        /** 商家根据财付通文档填写的数据和签名 */
//        @property (nonatomic, retain) NSString *package;
//        /** 商家根据微信开放平台文档对数据做的签名 */
//        @property (nonatomic, retain) NSString *sign;
        
        if !WXApi.isWXAppInstalled() {
            MBProgressHUD.showWithText(text: "没有安装微信", view: self.view)
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                self.navigationController?.popViewController(animated: true)
            }
            return
        }
        
        if !WXApi.isWXAppSupport() {
            MBProgressHUD.showWithText(text: "不支持微信支付", view: self.view)
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                self.navigationController?.popViewController(animated: true)
            }
            return
        }
        
        //调起微信支付
        let req = PayReq.init()
        req.partnerId = orderCreateData.mch_id ?? ""
        req.prepayId = orderCreateData.prepay_id ?? ""
        req.nonceStr = orderCreateData.nonce_str ?? ""
        req.timeStamp = UInt32(Date().timeStamp) ?? 0
        req.package = "Sign=WXPay"
        
        let appid = orderCreateData.appid ?? ""
        let noncestr = orderCreateData.nonce_str ?? ""
        let package = "Sign=WXPay"
        let partnerid = orderCreateData.mch_id ?? ""
        let prepayid = orderCreateData.prepay_id ?? ""
        let timestamp = "\(req.timeStamp)"
        let keyString = "key=zhongguoshuiwuzazhishe63886786KF"
        
        let signString = "appid=\(appid)&noncestr=\(noncestr)&package=\(package)&partnerid=\(partnerid)&prepayid=\(prepayid)&timestamp=\(timestamp)&\(keyString)"
        let sign = signString.md5Encrypt()
        req.sign = sign
        
        WXApi.send(req)
    }
    
    func pushToPaySuccessVc() {
        let nextVc = TRPaySuccessViewController(orderID: "")
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}


