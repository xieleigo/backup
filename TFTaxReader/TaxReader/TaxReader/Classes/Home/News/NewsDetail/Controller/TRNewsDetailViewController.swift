//
//  TRNewsDetailViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/4/9.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

class TRNewsDetailViewController: TRBaseViewController {
    
    var productIssueNumberDataModel: TRProductGetIssueNumberDataModel?
    var documentFilepath: String?
    var removeDianpath: String?
    var appIpDownloadpath: String?
    var filepath: String?
    
    var isCurrentPDFDownload: Bool? = false
    
    var PubIssueID: String?
    convenience init(PubIssueID: String?) {
        self.init()
        self.PubIssueID = PubIssueID
    }
    
    lazy var navView: LXNavigationBarView = {
        let view = LXNavigationBarView.init(frame: .zero)
        view.titleString = "期刊详情"
        view.navBackButtonClick = {[weak self]() in
            self?.navigationController?.popViewController(animated: true)
        }
        
        return view
    }()
    
    @objc func titleButtonClick(button:UIButton) {
        dropViewTitleButtonTouchUpInside()
    }
    
    lazy var introView: TRNewsDetailIntroView = {
        let view = TRNewsDetailIntroView.init(frame: .zero)
        view.backgroundColor = UIColor.orange
        
        return view
    }()
    
    lazy var funcView: TRNewsDetailFuncView = {
        let view = TRNewsDetailFuncView.init(frame: .zero)
        view.backgroundColor = LXGrayColor
        view.delegate = self
        view.trTitleButton.addTarget(self, action: #selector(titleButtonClick(button:)), for: .touchUpInside)
        
        return view
    }()
    
    lazy var dropView: TRNewsDetailDropView = {
        let view = TRNewsDetailDropView.init(frame: .zero)
        view.backgroundColor = UIColor.init(red: 217 / 255, green: 219 / 255, blue: 254 / 255, alpha: 1)
        view.button0.addTarget(self, action: #selector(buttonReadDidSelected(button:)), for: .touchUpInside)
        
        return view
    }()
    
    lazy var catalogView: TRNewsDetailCatalogView = {
        let view = TRNewsDetailCatalogView.init(frame: .zero)
        view.backgroundColor = UIColor.green
        view.delegate = self
        
        return view
    }()
    
    func setupLayout() {
        self.view.addSubview(self.navView)
        self.navView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(LXNavBarHeight)
        }
                
        self.view.addSubview(self.introView)
        self.introView.snp.makeConstraints { (make) in
            make.top.equalTo(self.navView.snp.bottom)
            make.left.right.equalToSuperview()
            make.height.equalTo(180)
        }
        
        self.view.addSubview(self.funcView)
        self.funcView.snp.makeConstraints { (make) in
            make.top.equalTo(self.introView.snp.bottom)
            make.left.right.equalToSuperview()
            make.height.equalTo(49)
        }
        
        self.view.addSubview(self.dropView)
        self.dropView.snp.makeConstraints { (make) in
            make.top.equalTo(self.funcView.snp.bottom)
            make.left.right.equalToSuperview()
            make.height.equalTo(44)
        }
        
        self.view.addSubview(self.catalogView)
        self.catalogView.snp.makeConstraints { (make) in
            make.top.equalTo(self.dropView.snp.bottom)
            make.left.right.bottom.equalToSuperview()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.isNavigationBarHidden = true
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.cyan
        
        setupLayout()
        NetworkHomeProductDetail(ReadSourceID: self.PubIssueID ?? "")
    }
    
    lazy var networkViewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TRNewsDetailViewController {
    func NetworkHomeProductDetail(ReadSourceID: String?) {
        networkViewModel.updateBlock = {[unowned self] in
            if self.networkViewModel.productDetailModel?.ret == false {
                // 3000 authorization参数不能为空
                if self.networkViewModel.productDetailModel?.msgCode == NetDataAuthorizationNull {
                    let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                        let popoverView = TRWLoginViewController()
                        popoverView.modalPresentationStyle = .custom
                        popoverView.isTypeShowFromTokenNull = true
                        popoverView.loginReloadBlock = {[unowned self] in
                            self.NetworkHomeProductDetail(ReadSourceID: ReadSourceID)
                        }
                        self.present(popoverView, animated: true, completion: nil)
                    }
                    self.present(alertController, animated: true, completion: nil)
                }else {
                    MBProgressHUD.showWithText(text: self.networkViewModel.productDetailModel?.msg ?? "", view: self.view)
                }
                
                return
            }
            
            self.introView.detailModel = self.networkViewModel.productDetailModel?.data
            self.catalogView.dataArrayPubIssueCata = self.networkViewModel.productDetailModel?.data?.PubIssueCata
            
            // 根据接口数据判断当前期刊是否被收藏
            let IsSubscribe = self.networkViewModel.productDetailModel?.data?.IsSubscribe ?? false
            let readButtonText = IsSubscribe ? "阅读" : "试读"
            //self.funcView.button0.setTitle(readButtonText, for: .normal)
            self.dropView.button0.setTitle(readButtonText, for: .normal)
            self.funcView.button2.isSelected = ((self.networkViewModel.productDetailModel?.data?.IsFavorite) == true)
            self.documentFilepath = self.getDocumentFilePath(dataModel: self.networkViewModel.productDetailModel?.data)
            self.removeDianpath = self.getRemoveDianPath(dataModel: self.networkViewModel.productDetailModel?.data)
            self.appIpDownloadpath = self.getAppIpDownloadPathPath(dataModel: self.networkViewModel.productDetailModel?.data)
            self.filepath = self.getFilePath(dataModel: self.networkViewModel.productDetailModel?.data)
            self.isCurrentPDFDownload = self.judgePDFDownload(filePath: self.documentFilepath ?? "")
            self.funcView.button3.isSelected = self.judgePDFDownload(filePath: self.documentFilepath ?? "")
            
            // 调用订阅下的弹框数据
            let dataModel: TRProductDetailDataModel? = self.networkViewModel.productDetailModel?.data
            self.NetworkGetIssueNumber(PubIssueID: "\(dataModel?.PubIssueID ?? 0)",
                                       PubID: "\(dataModel?.PubID ?? 0)",
                                        Year: "\(dataModel?.PubIssueYear ?? 0)")
        }
        
        networkViewModel.refreshDataSource_ProductDetail(PubIssueID: ReadSourceID ?? "", PubID: "", Year: "")
    }
    
    func NetworkGetIssueNumber(PubIssueID: String?, PubID: String?, Year: String?) {
        networkViewModel.getIssueNumberUpdateBlock = {[unowned self] in
            if self.networkViewModel.productGetIssueNumberModel?.ret == false {
                MBProgressHUD.showWithText(text: self.networkViewModel.productGetIssueNumberModel?.msg ?? "", view: self.view)
                return
            }
            let dataModel: TRProductGetIssueNumberDataModel? = self.networkViewModel.productGetIssueNumberModel?.data
            self.productIssueNumberDataModel = dataModel
        }
        
        networkViewModel.refreshDataSource_ProductGetIssueNumber(PubIssueID: PubIssueID ?? "",
                                                                 PubID: PubID ?? "",
                                                                 Year: Year ?? "",
                                                                 ArticleID: "")
    }
}

extension TRNewsDetailViewController {
    /// Initializes a document with the name of the pdf in the file system
    private func document(_ name: String) -> PDFDocument? {
        //guard let documentURL = Bundle.main.url(forResource: name, withExtension: "pdf") else { return nil }
        return PDFDocument(url: URL.init(fileURLWithPath: self.documentFilepath ?? ""))
    }
    
    /// Initializes a document with the data of the pdf
    private func document(_ data: Data) -> PDFDocument? {
        return PDFDocument(fileData: data, fileName: "Sample PDF")
    }
    
    /// Initializes a document with the remote url of the pdf
    private func document(_ remoteURL: URL) -> PDFDocument? {
        return PDFDocument(url: remoteURL)
    }
    
    
    /// Presents a document
    ///
    /// - parameter document: document to present
    ///
    /// Add `thumbnailsEnabled:false` to `createNew` to not load the thumbnails in the controller.
    private func showDocument(_ document: PDFDocument) {
        let image = UIImage(named: "")
//        let controller = PDFViewController.createNew(with: document, title: "", actionButtonImage: image, actionStyle: .activitySheet)
//        navigationController?.pushViewController(controller, animated: true)
//        let barButtomItem = UIBarButtonItem(barButtonSystemItem: .action, target: self, action: #selector(actionButtonPressed))
        
        //let barButtomItem = UIBarButtonItem.init(title: "返回", style: .plain, target: self, action: #selector(actionButtonPressed))
        let barButtomItem = UIBarButtonItem(barButtonSystemItem: .close, target: self, action: #selector(actionButtonPressed))
        let controller = PDFViewController.createNew(with: document, title: "", actionButtonImage: image, actionStyle: .customAction({
            
        }), backButton: barButtomItem, isThumbnailsEnabled: true, startPageIndex: 0)
        navigationController?.pushViewController(controller, animated: true)
    }
    
    @objc func actionButtonPressed() {
        self.navigationController?.popViewController(animated: true)
    }
    
    private func showLocalPDFDocument(doctmentName: String?) {
        let insanelyLargePDFDocumentName = doctmentName ?? ""
        if let doc = document(insanelyLargePDFDocumentName) {
            showDocument(doc)
        } else {
            print("Document named \(insanelyLargePDFDocumentName) not found in the file system")
        }
    }
}

extension TRNewsDetailViewController {
    func dropViewTitleButtonTouchUpInside() {
        let nextVc = TRNewsPubIssueViewController(productDetailDataModel: self.networkViewModel.productDetailModel?.data)
        nextVc.delegate = self
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}

extension TRNewsDetailViewController: TRNewsPubIssueViewControllerDelegate {
    func popToPubIssueViewWithSelectedModel(model: TRProductGetPubIssueByYearDataModel?) {
        NetworkHomeProductDetail(ReadSourceID: model?.ReadSourceID)
    }
}

extension TRNewsDetailViewController: TRNewsDetailCatalogViewDelegate {
    func tableViewDidSelectRowAt(_ tableView: UITableView, indexPath: IndexPath, ArticleID: String?) {
        let nextVc = TRArticleReadViewController(ArticleID: ArticleID)
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}

extension TRNewsDetailViewController: TRNewsDetailFuncViewDelegate {
    func getDocumentFilePath(dataModel: TRProductDetailDataModel?) -> String? {
        let IsSubscribe = dataModel?.IsSubscribe ?? false
        let PubIssueFreePDFPath = dataModel?.PubIssuePDF
        let PubIssuePDFPath = dataModel?.PubIssuePDF
        let downloadPath = IsSubscribe ? PubIssuePDFPath : PubIssueFreePDFPath
        let appIpDownloadPath = "\(appIp)\(downloadPath ?? "")"
        print(appIpDownloadPath)
        
        // 下载
        let documentPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first! as NSString
        let removeMao = appIpDownloadPath.replacingOccurrences(of: ":", with: "")
        let removeKong = removeMao.replacingOccurrences(of: "/", with: "")
        let removeDian = removeKong.replacingOccurrences(of: ".", with: "")
        let filePath = documentPath as String + ("/\(removeDian).pdf")
        print("filePath = \(filePath)")
        
        // 要检查的文件目录
        let documentFilePath = documentPath.appendingPathComponent("/\(removeDian).pdf")
        
        return documentFilePath
    }
    
    func getRemoveDianPath(dataModel: TRProductDetailDataModel?) -> String? {
        let IsSubscribe = dataModel?.IsSubscribe ?? false
        let PubIssueFreePDFPath = dataModel?.PubIssuePDF
        let PubIssuePDFPath = dataModel?.PubIssuePDF
        let downloadPath = IsSubscribe ? PubIssuePDFPath : PubIssueFreePDFPath
        let appIpDownloadPath = "\(appIp)\(downloadPath ?? "")"
        print(appIpDownloadPath)
        
        // 下载
        let removeMao = appIpDownloadPath.replacingOccurrences(of: ":", with: "")
        let removeKong = removeMao.replacingOccurrences(of: "/", with: "")
        let removeDian = removeKong.replacingOccurrences(of: ".", with: "")
        
        return removeDian
    }
    
    func getAppIpDownloadPathPath(dataModel: TRProductDetailDataModel?) -> String? {
        let IsSubscribe = dataModel?.IsSubscribe ?? false
        let PubIssueFreePDFPath = dataModel?.PubIssuePDF
        let PubIssuePDFPath = dataModel?.PubIssuePDF
        let downloadPath = IsSubscribe ? PubIssuePDFPath : PubIssueFreePDFPath
        let appIpDownloadPath = "\(appIp)\(downloadPath ?? "")"
        print(appIpDownloadPath)
        
        return appIpDownloadPath
    }
    
    func getFilePath(dataModel: TRProductDetailDataModel?) -> String? {
        let IsSubscribe = dataModel?.IsSubscribe ?? false
        let PubIssueFreePDFPath = dataModel?.PubIssuePDF
        let PubIssuePDFPath = dataModel?.PubIssuePDF
        let downloadPath = IsSubscribe ? PubIssuePDFPath : PubIssueFreePDFPath
        let appIpDownloadPath = "\(appIp)\(downloadPath ?? "")"
        print(appIpDownloadPath)
        
        // 下载
        let documentPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first! as NSString
        let removeMao = appIpDownloadPath.replacingOccurrences(of: ":", with: "")
        let removeKong = removeMao.replacingOccurrences(of: "/", with: "")
        let removeDian = removeKong.replacingOccurrences(of: ".", with: "")
        let filePath = documentPath as String + ("/\(removeDian).pdf")
        print("filePath = \(filePath)")
        
        return filePath
    }
    
    func judgePDFDownload(filePath: String) -> Bool {
        let fileManager = FileManager.default
        if fileManager.fileExists(atPath: filePath) {
            print("文件abc.doc存在")
            return true
        }else {
            print("文件abc.doc不存在")
            return false
        }
    }
    
    @objc func buttonReadDidSelected(button: UIButton) {
        if self.isCurrentPDFDownload ?? false {
            showLocalPDFDocument(doctmentName: self.removeDianpath ?? "")
        }else {
            let hud = MBProgressHUD.showAdded(to: view, animated: true)
            hud.mode = MBProgressHUDMode.determinateHorizontalBar;
            hud.label.text = "Loading...";
            hud.label.numberOfLines = 0;
            hud.label.textAlignment = NSTextAlignment.left;
            hud.bezelView.style = MBProgressHUDBackgroundStyle.solidColor
            hud.contentColor = UIColor.white;
            hud.bezelView.backgroundColor = UIColor.black.withAlphaComponent(0.7);
            
            DispatchQueue.global(qos: .background).async {
                var progress = 0.0
                RequestNetWork.downLoad(withURL: self.appIpDownloadpath ?? "", progress: { (downloadProgress) in
                    let progreeNumber = ((downloadProgress?.fractionCompleted ?? 0) * 100)
                    let showProgressNumber = ((Int)(progreeNumber*100))/100
                    print("下载的进度：\((downloadProgress?.fractionCompleted ?? 0) * 100)")
                    
                    if progress < 1.0 {
                        progress = (downloadProgress?.fractionCompleted ?? 0) * 1
                        DispatchQueue.main.async {
                            hud.label.text = "下载的进度：\(showProgressNumber)%";
                            hud.progress = Float(progress)
                        }
                    }
                }, destination: { (targetPath, response) -> URL? in
                    return URL.init(fileURLWithPath: self.filepath ?? "")
                }, downLoadSuccess: { (response, filePath) in
                    DispatchQueue.main.async {
                        hud.hide(animated: true)
                    }
                    print("response = \(response) filePath = \(String(describing: filePath))")
                    self.showLocalPDFDocument(doctmentName: self.removeDianpath ?? "")
                }) { (error) in
                    DispatchQueue.main.async {
                        hud.hide(animated: true)
                    }
                    print("error = \(error)")
                }
            }
        }
    }
    
    func buttonBuyDidSelected(button: UIButton) {
        if publicAuthorizationToken == nil {
            let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                let popoverView = TRWLoginViewController()
                popoverView.modalPresentationStyle = .custom
                popoverView.isTypeShowFromTokenNull = true
                popoverView.loginReloadBlock = {[unowned self] in
                    self.buttonBuyDidSelected(button: button)
                }
                self.present(popoverView, animated: true, completion: nil)
            }
            self.present(alertController, animated: true, completion: nil)
            return
        }
        
        // 底部弹框
        guard let nextVc = TRBuyActionSheetViewController(model: self.productIssueNumberDataModel) else { return }
        nextVc.delegate = self
        present(nextVc, animated: false, completion:  nil)
    }
    
    func buttonFavorDidSelected(button: UIButton) {
        if publicAuthorizationToken == nil {
            let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                let popoverView = TRWLoginViewController()
                popoverView.modalPresentationStyle = .custom
                popoverView.isTypeShowFromTokenNull = true
                popoverView.loginReloadBlock = {[unowned self] in
                    self.buttonFavorDidSelected(button: button)
                }
                self.present(popoverView, animated: true, completion: nil)
            }
            self.present(alertController, animated: true, completion: nil)
            return
        }
        
        if self.funcView.button2.isSelected {
            self.networkViewModel.updateBlock = {[unowned self] in
                if self.networkViewModel.favorCancelModel?.ret == false {
                    MBProgressHUD.showWithText(text: self.networkViewModel.favorCancelModel?.msg ?? "", view: self.view)
                    return
                }
                
                self.funcView.button2.isSelected = false
            }
            //let articleID = self.networkViewModel.productDetailModel?.data?.PubIssueID
            self.networkViewModel.refreshDataSource_FavorCancel(FavoriteID: self.PubIssueID ?? "", ReadFavoriteType: "20")
        }
        else
        {
            self.networkViewModel.updateBlock = {[unowned self] in
                if self.networkViewModel.favorAddModel?.ret == false {
                    MBProgressHUD.showWithText(text: self.networkViewModel.favorAddModel?.msg ?? "", view: self.view)
                    return
                }
                
                self.funcView.button2.isSelected = true
            }
            self.networkViewModel.refreshDataSource_FavorAdd(ReadSourceID: self.PubIssueID ?? "", ReadParentID: self.PubIssueID ?? "", ReadFavoriteType: "20")
        }
        
    }
    
    func buttonShareDidSelected(button: UIButton) {
        self.sheetViewShareButtonDidSelected()
    }
    
    // 先前文件下载的回调方法
    func buttonDownloadDidSelected(button: UIButton) {
        if self.isCurrentPDFDownload ?? false {
            MBProgressHUD.showWithText(text: "当前文件已下载", view: self.view)
            return
        }
        
        let hud = MBProgressHUD.showAdded(to: view, animated: true)
        hud.mode = MBProgressHUDMode.determinateHorizontalBar;
        hud.label.text = "Loading...";
        hud.label.numberOfLines = 0;
        hud.label.textAlignment = NSTextAlignment.left;
        hud.bezelView.style = MBProgressHUDBackgroundStyle.solidColor;
        hud.contentColor = UIColor.white;
        hud.bezelView.backgroundColor = UIColor.black.withAlphaComponent(0.7);
        
        DispatchQueue.global(qos: .background).async {
            var progress = 0.0
            RequestNetWork.downLoad(withURL: self.appIpDownloadpath ?? "", progress: { (downloadProgress) in
                let progreeNumber = ((downloadProgress?.fractionCompleted ?? 0) * 100)
                let showProgressNumber = ((Int)(progreeNumber*100))/100
                print("下载的进度：\((downloadProgress?.fractionCompleted ?? 0) * 100)")
                
                if progress < 1.0 {
                    progress = (downloadProgress?.fractionCompleted ?? 0) * 1
                    DispatchQueue.main.async {
                        hud.label.text = "下载的进度：\(showProgressNumber)%";
                        hud.progress = Float(progress)
                    }
                }
            }, destination: { (targetPath, response) -> URL? in
                return URL.init(fileURLWithPath: self.filepath ?? "")
            }, downLoadSuccess: { (response, filePath) in
                DispatchQueue.main.async {
                    hud.hide(animated: true)
                }
                print("response = \(response) filePath = \(String(describing: filePath))")
            }) { (error) in
                DispatchQueue.main.async {
                    hud.hide(animated: true)
                }
                print("error = \(error)")
            }
        }
    }
}

extension TRNewsDetailViewController {
    func sheetViewShareButtonDidSelected() {
        let shareAlert = LXShareSheetAlert.init()
        shareAlert.showLEEAlertShareSheet()
        shareAlert.openShareTypeBlock = {(shareType) in
            print("shareType = \(shareType)")
            switch (shareType)
            {
                case 0:
                    self.shareWeixin(wxSceneType: WXSceneTimeline)
                    break;
                case 1:
                    self.shareWeixin(wxSceneType: WXSceneSession)
                    break;
                case 2:
                    self.shareQQ()
                    break;
                case 3:
                    self.shareWB()
                    break;
                default:
                    break;
            }
        }
    }
    
    func shareWeixin(wxSceneType: WXScene) {
        let dataModel: TRProductDetailDataModel? = self.networkViewModel.productDetailModel?.data
        let thumbImage = (UIImage.init(named: "TRLogoIcon.png"))!
        let kLinkURL = dataModel?.SourceUrl ?? ""
        let kLinkTagName = "WECHAT_TAG_JUMP_SHOWRANK"
        let kLinkTitle = dataModel?.PubIssueName ?? ""
        let kLinkDescription = dataModel?.PubIssueExeUnit ?? ""
        let currentScene = wxSceneType
        WXApiRequestHandler.sendLinkURL(kLinkURL,
                                        tagName: kLinkTagName,
                                        title: kLinkTitle,
                                        description: kLinkDescription,
                                        thumbImage: thumbImage,
                                        in: currentScene)
    }
    
    func shareQQ() {
        MBProgressHUD.showWithText(text: "待完成", view: self.view!)
    }

    func shareWB() {
        MBProgressHUD.showWithText(text: "待完成", view: self.view!)
    }
}


extension TRNewsDetailViewController: TRBuyActionSheetViewControllerDelegate {
    func sheetViewBuyButtonDidSelected(dataArrayShopModel: [YCOrderShopModel]?) {
        let nextVc = TRCartSureOrderViewController(dataArrayShopModel: dataArrayShopModel)
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}
