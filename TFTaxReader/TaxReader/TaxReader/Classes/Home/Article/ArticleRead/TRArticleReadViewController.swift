//
//  TRArticleReadViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/6/19.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import HandyJSON
import SwiftyJSON

class TRArticleReadViewController: TRBaseViewController {
    
    var ArticleID: String?
    convenience init(ArticleID: String?) {
        self.init()
        self.ArticleID = ArticleID
    }
    
    var detailTextHeight: CGFloat = 0
    var webViewHeight: CGFloat = 0
    
    var loadHtmlDataNumber: Int = 0
    var isPageControlAction: Bool = false
    
    var htmlContent: String?
    var productIssueNumberDataModel: TRProductGetIssueNumberDataModel?
    
    lazy var navView: TRReadNavigationView = {
        let view = TRReadNavigationView.init(frame: .zero)
        view.navBackButtonClick = {[weak self](button, buttonType) in
            switch buttonType {
            case .buttonBack:
                self?.navigationController?.popViewController(animated: true)
            case .buttonFavor:
                self?.blockFavorButtonAction()
            case .buttonBuy:
                self?.blockBuyButtonAction()
            case .buttonShare:
                self?.blockShareButtonAction()
            }
        }
        
        return view
    }()
    
    lazy var scrollView: UIScrollView = {
        let scrollView = UIScrollView.init(frame: .zero)
        scrollView.backgroundColor = UIColor.white
        scrollView.contentSize = CGSize.init(width: LXScreenWidth, height: LXScreenHeight - LXNavBarHeight)
        scrollView.isScrollEnabled = true
        scrollView.showsVerticalScrollIndicator = true
        
        return scrollView
    }()
    
    lazy var contentView: UIView = {
        let view = UIView.init(frame: .zero)
        view.backgroundColor = UIColor.white
        
        return view
    }()
    
    // 单行
    lazy var titleLabel: UILabel = {
        let view = UILabel.init(frame: .zero)
        view.backgroundColor = UIColor.white
        view.textAlignment = .center
        
        return view
    }()
    
    lazy var detailView: TRReadTypeDetailView = {
        let view = TRReadTypeDetailView.init(frame: .zero)
        view.backgroundColor = UIColor.white
        
        return view
    }()
    
    lazy var articleLabel: LXInsetsLabel = {
        let view = LXInsetsLabel(frame: .zero)
        view.backgroundColor = LXTableViewBackgroundColor
        view.textColor = UIColor.black
        view.font = UIFont.systemFont(ofSize: 16.0)
        view.textAlignment = .left
        view.numberOfLines = 0
        
        view.textInsets = UIEdgeInsets(top: 0, left: 8, bottom: 0, right: 8)
        
        return view
    }()
    
    lazy var footerButtonView: LXFooterButtonCenterView = {
        let view = LXFooterButtonCenterView.init(frame: .zero)
        view.footerButton.setTitle("阅读全文", for: .normal)
        view.isHidden = true
        view.footerButtonClick = {[weak self](button) in
            self?.blockReadAllButtonAction()
        }
        
        return view
    }()
    
    lazy var richTextView: RichTextView = {
        let view = RichTextView(frame: .zero, fromVC: self)
        view.isDidScrollEnabled = false
        view.backgroundColor = UIColor.white
        view.webviewDidFinishBlock = {[unowned self](webViewHeight) in
            self.blockWebviewDidFinish(webViewHeight: webViewHeight)
        }
        
        return view
    }()
    
    lazy var pageControlView: LXFooterButtonTwoView = {
        let view = LXFooterButtonTwoView.init(frame: .zero)
        view.footerButton1.setTitle("上一篇", for: .normal)
        view.footerButton1.setTitleColor(TRThemeColor, for: .normal)
        view.footerButton1.backgroundColor = UIColor.white
        view.footerButton1.layer.borderColor = TRThemeColor.cgColor
        view.footerButton1.layer.borderWidth = 1.0
        view.footerButton2.setTitle("下一篇", for: .normal)
        view.footerButton2.setTitleColor(TRThemeColor, for: .normal)
        view.footerButton2.backgroundColor = UIColor.white
        view.footerButton2.layer.borderColor = TRThemeColor.cgColor
        view.footerButton2.layer.borderWidth = 1.0
        view.footerButtonClick = {[weak self](button) in
            if button.tag == 1{
                self?.blockLastPageButtonClick(button: button)
            }
            
            if button.tag == 2 {
               self?.blockNextPageButtonClick(button: button)
            }
        }
        
        return view
    }()
    
    // 滚动到顶部
    lazy var scrolTopView: LXScrolTopView = {
        let view = LXScrolTopView.init(frame: .zero)
        self.view.bringSubviewToFront(view)
        view.buttonClickBlock = {[unowned self](button) in
            self.blockScrolTopButtonClick(button: button)
        }
        
        return view
    }()

    func setupLayout() {
        self.view.addSubview(self.navView)
        self.navView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(LXNavBarHeight)
        }
        
        self.view.addSubview(self.scrollView)
        self.scrollView.snp.makeConstraints { (make) in
            make.top.equalTo(self.navView.snp.bottom)
            make.left.bottom.right.equalToSuperview()
        }
        
        self.scrollView.addSubview(self.contentView)
        self.contentView.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
            make.centerX.equalToSuperview()
            make.height.equalTo(2000)
        }
        
        self.contentView.addSubview(self.titleLabel)
        self.titleLabel.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(44)
        }
        
        self.contentView.addSubview(self.detailView)
        self.detailView.snp.makeConstraints { (make) in
            make.top.equalTo(self.titleLabel.snp.bottom)
            make.left.right.equalToSuperview()
            make.height.equalTo(90)
        }
        
        self.contentView.addSubview(self.articleLabel)
        self.articleLabel.snp.makeConstraints { (make) in
            make.top.equalTo(self.detailView.snp.bottom)
            make.left.right.equalToSuperview()
            make.height.equalTo(200)
        }
        
        // 阅读全文按钮
        self.contentView.addSubview(self.footerButtonView)
        self.footerButtonView.snp.makeConstraints { (make) in
            make.top.equalTo(self.articleLabel.snp.bottom).offset(44)
            make.left.right.equalToSuperview()
            make.height.equalTo(64)
        }
        
        // 富文本全文信息
        self.contentView.addSubview(self.richTextView)
        self.richTextView.snp.makeConstraints { (make) in
            make.top.equalTo(self.articleLabel.snp.bottom)
            make.left.right.equalToSuperview()
        }
        
        // 上一篇 下一篇
        self.contentView.addSubview(self.pageControlView)
        self.pageControlView.snp.makeConstraints { (make) in
            make.left.right.equalToSuperview()
            make.bottom.equalToSuperview()
            make.height.equalTo(64)
        }

        // 滚动到顶部
        self.view.addSubview(self.scrolTopView)
        self.scrolTopView.snp.makeConstraints { (make) in
            make.right.equalToSuperview()
            make.bottom.equalToSuperview().offset(-LXPhoneLHBarHeight - 20)
            make.size.equalTo(44)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupLayout()
        NetworkArticleDetail(ArticleID: self.ArticleID, SkipIndex: "0")
    }
    
    lazy var networkViewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TRArticleReadViewController {
    // 订阅下的弹框数据
    func NetworkGetIssueNumber(model: TRArticleDetailDataModel?) {
        networkViewModel.getIssueNumberUpdateBlock = {[unowned self] in
            if self.networkViewModel.productGetIssueNumberModel?.ret == false {
                // 3000 authorization参数不能为空
                if self.networkViewModel.productGetIssueNumberModel?.msgCode == NetDataAuthorizationNull {
                    let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                        let popoverView = TRWLoginViewController()
                        popoverView.modalPresentationStyle = .custom
                        popoverView.isTypeShowFromTokenNull = true
                        popoverView.loginReloadBlock = {[unowned self] in
                            self.NetworkGetIssueNumber(model: model)
                        }
                        self.present(popoverView, animated: true, completion: nil)
                    }
                    self.present(alertController, animated: true, completion: nil)
                }else {
                    MBProgressHUD.showWithText(text: self.networkViewModel.productGetIssueNumberModel?.msg ?? "", view: self.view)
                }
                
                return
            }
            var dataModel: TRProductGetIssueNumberDataModel? = self.networkViewModel.productGetIssueNumberModel?.data
            dataModel?.isHasCurrentPian = true
            dataModel?.ArticleTitle = model?.ArticleTitle
            dataModel?.PubIssueYear = "\(model?.PubIssueYear ?? 0)"
            dataModel?.PubIssueNum = (model?.PubIssueNum ?? 0)
            dataModel?.ArticleIOSPrice = "\(model?.ArticleIOSPrice ?? 0.00)"
            self.productIssueNumberDataModel = dataModel
        }
        
        networkViewModel.refreshDataSource_ProductGetIssueNumber(PubIssueID: "\(model?.PubIssueID ?? 0)",
                                                                 PubID: "\(model?.PubID ?? 0)",
                                                                 Year: "\(model?.PubIssueYear ?? 0)",
                                                            ArticleID: "\(model?.ArticleID ?? 0)")
    }
    
    func NetworkArticleDetail(ArticleID: String?, SkipIndex: String?) {
        networkViewModel.articleDetailUpdateBlock = {[unowned self] in
            if self.networkViewModel.articleDetailModel?.ret == false {
                MBProgressHUD.showWithText(text: self.networkViewModel.articleDetailModel?.msg ?? "", view: self.view)
                return
            }
            
            let isFavorite = self.networkViewModel.articleDetailModel?.data?.IsFavorite ?? false
            self.navView.trFavorButton.isSelected = isFavorite ? true : false
            self.titleLabel.text = self.networkViewModel.articleDetailModel?.data?.ArticleTitle
            self.detailView.readTypeDataModel = self.networkViewModel.articleDetailModel?.data
            
            let contentText = "内容简介：\n        \(self.networkViewModel.articleDetailModel?.data?.ArticleAbstract ?? "")"
            let textHeight:CGFloat = self.lxheight(for: contentText)
            print("简介计算的高度textHeight = \(textHeight)")
            self.detailTextHeight = textHeight
            self.articleLabel.snp.updateConstraints { (make) in
                make.height.equalTo(textHeight)
            }
            
            //通过富文本来设置行间距
            let paraph = NSMutableParagraphStyle()
            paraph.lineSpacing = 5
            let attributes = [NSAttributedString.Key.font:UIFont.systemFont(ofSize: 16),
                              NSAttributedString.Key.paragraphStyle: paraph]
            self.articleLabel.attributedText = NSAttributedString(string: contentText, attributes: attributes)
            
            // 调用订阅下的弹框数据
            let dataModel: TRArticleDetailDataModel? = self.networkViewModel.articleDetailModel?.data
            self.NetworkGetIssueNumber(model: dataModel)
            
            // 更新ArticleID
            self.ArticleID = "\(dataModel?.ArticleID ?? 0)"
            // 更新富文本
            self.NetworkArticleGetHtmlContent(ArticleID: self.ArticleID)
        }
        
        networkViewModel.refreshDataSource_ArticleDetail(ArticleID: ArticleID ?? "", SkipIndex: SkipIndex ?? "")
    }
    
    func NetworkArticleGetHtmlContent(ArticleID: String?) {
        networkViewModel.articleGetHtmlContentUpdateBlock = {[unowned self] in
            
            // 需要购买显示“阅读全文按钮”
            self.loadHtmlDataNumber = self.loadHtmlDataNumber + 1
            let richText = self.networkViewModel.articleGetHtmlContentModel?.data
            if self.loadHtmlDataNumber == 1  || self.isPageControlAction { // 第一次加载
                if richText?.isBlank ?? false || richText == "null" || richText == nil {
                    self.footerButtonView.isHidden = false
                    self.richTextView.isHidden = true
                    self.pageControlView.snp.makeConstraints { (make) in
                        make.left.right.equalToSuperview()
                        make.bottom.equalToSuperview()
                        make.height.equalTo(64)
                    }
                    let height = 44 + 90 + self.detailTextHeight + (55 + 44) + (64 + 12)
                    self.contentView.snp.updateConstraints { (make) in
                        make.height.equalTo(height)
                    }
                }else{
                    self.footerButtonView.isHidden = true
                    self.richTextView.isHidden = false
                    self.richTextView.isDidScrollEnabled = false
                    self.richTextView.richText = richText
                }
            }else {
                if richText?.isBlank ?? false || richText == "null" || richText == nil {
                    if publicAuthorizationToken == nil {
                        let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                            let popoverView = TRWLoginViewController()
                            popoverView.modalPresentationStyle = .custom
                            popoverView.isTypeShowFromTokenNull = true
                            popoverView.loginReloadBlock = {[unowned self] in
                                self.blockReadAllButtonAction()
                            }
                            self.present(popoverView, animated: true, completion: nil)
                        }
                        self.present(alertController, animated: true, completion: nil)
                        return
                    }
                    
                    guard let nextVc = TRBuyActionSheetViewController(model: self.productIssueNumberDataModel) else { return }
                    nextVc.delegate = self
                    self.present(nextVc, animated: false, completion:  nil)
                }else {
                    self.footerButtonView.isHidden = true
                    self.richTextView.isHidden = false
                    self.richTextView.isDidScrollEnabled = false
                    self.richTextView.richText = richText
                }
            }
            
            if self.networkViewModel.articleGetHtmlContentModel?.ret == false {
                return
            }
        }
        
        networkViewModel.refreshDataSource_ArticleGetHtmlContent(ArticleID: ArticleID ?? "")
    }
}

extension TRArticleReadViewController{
    func blockFavorButtonAction() {
        if publicAuthorizationToken == nil {
            let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                let popoverView = TRWLoginViewController()
                popoverView.modalPresentationStyle = .custom
                popoverView.isTypeShowFromTokenNull = true
                popoverView.loginReloadBlock = {[unowned self] in
                    self.blockFavorButtonAction()
                }
                self.present(popoverView, animated: true, completion: nil)
            }
            self.present(alertController, animated: true, completion: nil)
            return
        }
        
        if self.navView.trFavorButton.isSelected {
            self.networkViewModel.updateBlock = {[unowned self] in
                MBProgressHUD.showWithText(text: self.networkViewModel.favorCancelModel?.msg ?? "", view: self.view)
                if self.networkViewModel.favorCancelModel?.ret == false {
                    return
                }
                
                self.navView.trFavorButton.isSelected = false
            }
            let articleID = self.networkViewModel.articleDetailModel?.data?.ArticleID
            self.networkViewModel.refreshDataSource_FavorCancel(FavoriteID: "\(articleID ?? 0)", ReadFavoriteType: "30")
        } else {
            self.networkViewModel.updateBlock = {[unowned self] in
                MBProgressHUD.showWithText(text: self.networkViewModel.favorAddModel?.msg ?? "", view: self.view)
                if self.networkViewModel.favorAddModel?.ret == false {
                    return
                }
                
                self.navView.trFavorButton.isSelected = true
            }
            
            let ReadSourceID = self.networkViewModel.articleDetailModel?.data?.ArticleID ?? 0
            let ReadParentID = self.networkViewModel.articleDetailModel?.data?.ReadTypeRootID ?? 0
            self.networkViewModel.refreshDataSource_FavorAdd(ReadSourceID: "\(ReadSourceID)", ReadParentID: "\(ReadParentID)", ReadFavoriteType: "30")
        }
        
    }
    
    func blockBuyButtonAction() {
        if publicAuthorizationToken == nil {
            let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                let popoverView = TRWLoginViewController()
                popoverView.modalPresentationStyle = .custom
                popoverView.isTypeShowFromTokenNull = true
                popoverView.loginReloadBlock = {[unowned self] in
                    self.blockBuyButtonAction()
                }
                self.present(popoverView, animated: true, completion: nil)
            }
            self.present(alertController, animated: true, completion: nil)
            
            return
        }
        
        let msg = "你已经拥有该文章阅览权限，如继续购买，系统自动分配阅读码，阅读码可以转移给其他用户使用。"
        let alert = UIAlertController.init(title: TokenNullTitle, message: msg, preferredStyle: .alert)
        let confirmaction = UIAlertAction.init(title: "确定", style: .destructive) { (UIAlertAction) in
            guard let nextVc = TRBuyActionSheetViewController(model: self.productIssueNumberDataModel) else { return }
            nextVc.delegate = self
            self.present(nextVc, animated: false, completion:  nil)
        }
        alert.addAction(confirmaction)
        self.present(alert, animated: true, completion: nil)
    }
    
    func blockShareButtonAction() {
        self.sheetViewShareButtonDidSelected()
    }
}

extension TRArticleReadViewController {
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
        let dataModel: TRArticleDetailDataModel? = self.networkViewModel.articleDetailModel?.data
        let thumbImage = (UIImage.init(named: "TRLogoIcon.png"))!
        let kLinkURL = dataModel?.SourceUrl ?? ""
        let kLinkTagName = "WECHAT_TAG_JUMP_SHOWRANK"
        let kLinkTitle = dataModel?.ArticleTitle ?? ""
        let kLinkDescription = dataModel?.ArticleAbstract ?? ""
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

extension TRArticleReadViewController {
    func blockReadAllButtonAction() {
        self.isPageControlAction = false
        self.NetworkArticleGetHtmlContent(ArticleID: self.ArticleID)
    }
}

extension TRArticleReadViewController{
    func blockWebviewDidFinish(webViewHeight: CGFloat) {
        self.webViewHeight = webViewHeight
        self.richTextView.snp.updateConstraints { (make) in
            make.top.equalTo(self.articleLabel.snp.bottom)
            make.left.right.equalToSuperview()
        }
        let height = 44 + 90 + self.detailTextHeight + (webViewHeight + 12) + (64 + 12)
        self.contentView.snp.updateConstraints { (make) in
            make.height.equalTo(height)
        }
    }
}

extension TRArticleReadViewController{
    func blockLastPageButtonClick(button: UIButton) {
        self.isPageControlAction = true
        NetworkArticleDetail(ArticleID: self.ArticleID, SkipIndex: "-1")
    }
    
    func blockNextPageButtonClick(button: UIButton) {
        self.isPageControlAction = true
        NetworkArticleDetail(ArticleID: self.ArticleID, SkipIndex: "1")
    }
    
    func blockScrolTopButtonClick(button: UIButton) {
        button.isSelected = !button.isSelected
        if button.isSelected {
            scrollToPosition(isScrollToTop: true)
        }else {
            scrollToPosition(isScrollToTop: false)
        }
    }
    
    func scrollToPosition(isScrollToTop: Bool) {
        if isScrollToTop {
            let desiredOffset = CGPoint(x: 0, y: -self.scrollView.contentInset.top)
            self.scrollView.setContentOffset(desiredOffset, animated: true)
        }else {
            let desiredOffset = CGPoint(x: 0, y: self.contentView.frame.size.height - LXScreenHeight + 64)
            self.scrollView.setContentOffset(desiredOffset, animated: true)
        }
    }
}

extension TRArticleReadViewController: TRBuyActionSheetViewControllerDelegate {
    func sheetViewBuyButtonDidSelected(dataArrayShopModel: [YCOrderShopModel]?) {
        let nextVc = TRCartSureOrderViewController(dataArrayShopModel: dataArrayShopModel)
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
}














extension TRArticleReadViewController{
    // 计算文本高度
    func lxheight(for contentText: String?) -> CGFloat {
        var height: CGFloat = 10
        guard let text = contentText else { return height }
        let label = UILabel()
        label.numberOfLines = 0
        
        //label.font = UIFont.systemFont(ofSize: 15)
        //label.text = text
        
        //通过富文本来设置行间距
        let paraph = NSMutableParagraphStyle()
        //将行间距设置为28
        paraph.lineSpacing = 5
        //样式属性集合
        let attributes = [NSAttributedString.Key.font:UIFont.systemFont(ofSize: 16),
                          NSAttributedString.Key.paragraphStyle: paraph]
        label.attributedText = NSAttributedString(string: text, attributes: attributes)

        
        height += label.sizeThatFits(CGSize(width: LXScreenWidth - 16, height: CGFloat.infinity)).height
        return height
    }
}


