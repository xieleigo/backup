//
//  TRPublicationViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/7/22.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

typealias TRPublicationVCdidSelectRowAtBlock = (_ model: TRGetAllPublicationDataModel) ->Void
typealias TRPublicationVCDidCommitButtonClickBlock = (_ button: UIButton, _ dataArrayModel: [TRGetAllPublicationDataModel]) ->Void

class TRPublicationViewController: UIViewController {
    
    var didSelectRowAtBlock: TRPublicationVCdidSelectRowAtBlock?
    var didCommitButtonClickBlock: TRPublicationVCDidCommitButtonClickBlock?
    
    var dataArray = [String]()
    var dataArrayModel: [TRGetAllPublicationDataModel]?
    
    // 创建一个数组用来存储选中的 cell 的行
    var selectedIndexs: [Int] = []
    
    fileprivate var coverVeiw = UIView()
    fileprivate var contentView = UIView()
    fileprivate var contentViewHeight: CGFloat {
        return 44*4+44
    }
    
    required init?(dataArray: [String]!) {
        super.init(nibName: nil, bundle: nil)
        // 初始化
        self.dataArray = dataArray;
        
        view.backgroundColor = UIColor.clear
        self.providesPresentationContextTransitionStyle = true
        self.definesPresentationContext = true
        self.modalPresentationStyle = .custom
        
        // 初始化UI
        setupUIViews()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        UIView.animate(withDuration: 0.25) {
            var frame = self.contentView.frame
            frame.origin.y = kScreenHeight - self.contentViewHeight
            self.contentView.frame = frame
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    lazy var headerView: UIView = {
        let view = UIView.init(frame: .zero)
        view.backgroundColor = UIColor.white
        
        return view
    }()
    
    private lazy var trTitleLabel: UILabel = {
        let view = UILabel.init()
        view.font = UIFont.systemFont(ofSize: 15.0)
        view.text = "全部刊名"
        
        return view
    }()
    
    var trcommitButton:UIButton = {
        let view = UIButton.init(type: UIButton.ButtonType.custom)
        view.setTitle("完成", for: .normal)
        view.setTitleColor(UIColor.black, for: .normal)
        view.titleLabel?.font = UIFont.systemFont(ofSize: 15.0)
        view.addTarget(self, action: #selector(commitButtonClick(button:)), for: .touchUpInside)
        
        return view
    }()
    
    @objc func commitButtonClick(button:UIButton) {
        print("selectedIndexs = \(selectedIndexs)")
        if selectedIndexs.count > 0 {
            var dataArrayModel: [TRGetAllPublicationDataModel]? = []
            for index in selectedIndexs {
                let model: TRGetAllPublicationDataModel = self.dataArrayModel?[index] ?? TRGetAllPublicationDataModel.init()
                dataArrayModel?.append(model)
            }
            
            sheetViewDismiss()
            guard let didCommitButtonClickBlock = didCommitButtonClickBlock else { return }
            didCommitButtonClickBlock(button, dataArrayModel!)
        }else {
            sheetViewDismiss()
        }
    }
    
    private let identific = "LXExampleXibTableViewCell"
    lazy var tableView: UITableView = {
        let tableView = UITableView.init(frame: .zero, style: UITableView.Style.plain)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.backgroundColor = UIColor.white
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.singleLine
        
        //tableView.register(LXExampleXibTableViewCell.self, forCellReuseIdentifier: identific)
        tableView.register(UINib.init(nibName: identific, bundle: nil), forCellReuseIdentifier: identific)
        tableView.tableFooterView = UIView.init(frame: .zero)
        
        return tableView
    }()
    
    func setupLayout() {
        self.contentView.addSubview(self.headerView)
        self.headerView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(44)
        }
        
        self.headerView.addSubview(self.trTitleLabel)
        self.trTitleLabel.snp.makeConstraints { (make) in
            make.center.equalToSuperview()
        }
        
        self.headerView.addSubview(self.trcommitButton)
        self.trcommitButton.snp.makeConstraints { (make) in
            make.centerY.equalToSuperview()
            make.right.equalTo(-12)
            make.size.equalTo(36)
        }
        
        self.contentView.addSubview(self.tableView)
        self.tableView.snp.makeConstraints { (make) in
            make.top.equalTo(self.headerView.snp.bottom)
            make.left.bottom.right.equalToSuperview()
        }
    }
    
    lazy var networkViewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TRPublicationViewController: UITableViewDataSource, UITableViewDelegate {
    // MARK: UITableViewDataSource
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataArrayModel?.count ?? 0
    }
    
    public func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 44.0
    }
    
    public func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return CGFloat.leastNormalMagnitude
    }
    public func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return ""
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return UIView.init()
    }
    
    public func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return CGFloat.leastNormalMagnitude
    }
    func tableView(_ tableView: UITableView, titleForFooterInSection section: Int) -> String? {
        return ""
    }
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        return UIView.init()
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: identific, for: indexPath) as! LXExampleXibTableViewCell
        
        let model: TRGetAllPublicationDataModel = self.dataArrayModel?[indexPath.row] ?? TRGetAllPublicationDataModel.init()
        
        cell.textLabel?.textColor = UIColor.init(white: 0.0, alpha: 0.6)
        cell.textLabel?.font = UIFont.init(name: "ChalkboardSE-Bold", size: 14.0)
        cell.textLabel?.lineBreakMode = .byCharWrapping
        cell.textLabel?.text = model.PubName
        cell.textLabel?.numberOfLines = 2
        
        //判断是否选中
        if selectedIndexs.contains(indexPath.row) {
            cell.accessoryType = .checkmark
        } else {
            cell.accessoryType = .none
        }

        return cell
    }
    
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let cell = tableView.cellForRow(at: indexPath) as! LXExampleXibTableViewCell
        //判断该行原先是否选中
        if let index = selectedIndexs.firstIndex(of: indexPath.row){
            selectedIndexs.remove(at: index)
            cell.accessoryType = .none
        }else{
            selectedIndexs.append(indexPath.row)
            cell.accessoryType = .checkmark
        }
        
        //刷新该行,刷新点击行的状态就行了 ，刷新整个 tableView 没有必要，cell 的图片就是选中或者未选中的样子了
        self.tableView.reloadRows(at: [indexPath], with: .automatic)
        
//        sheetViewDismiss()
//        let model: TRGetAllPublicationDataModel = self.dataArrayModel?[indexPath.row] ?? TRGetAllPublicationDataModel.init()
//        guard let didSelectRowAtBlock = didSelectRowAtBlock else { return }
//        didSelectRowAtBlock(model)
    }
}

extension TRPublicationViewController {
    func NetworkGetAllPublication() {
        networkViewModel.updateBlock = {[unowned self] in
            if self.networkViewModel.allPublicationModel?.ret == false {
                
                // 3000 authorization参数不能为空
                if self.networkViewModel.allPublicationModel?.msgCode == NetDataAuthorizationNull {
                    let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                        let popoverView = TRWLoginViewController()
                        popoverView.modalPresentationStyle = .custom
                        popoverView.isTypeShowFromTokenNull = true
                        popoverView.loginReloadBlock = {[unowned self] in
                            self.NetworkGetAllPublication()
                        }
                        self.present(popoverView, animated: true, completion: nil)
                    }
                    self.present(alertController, animated: true, completion: nil)
                }else {
                    MBProgressHUD.showWithText(text: self.networkViewModel.allPublicationModel?.msg ?? "", view: self.view)
                }
                
                return
            }
            
            self.dataArrayModel = self.networkViewModel.allPublicationModel?.data
            self.tableView.reloadData()
        }
        
        networkViewModel.refreshDataSource_GetAllPublication()
    }
}

// MARK: 初始化UI
extension TRPublicationViewController {
    func setupUIViews() {
        coverVeiw = UIView(frame: CGRect(x: 0, y: 0, width:kScreenWidth, height: kScreenHeight))
        coverVeiw.backgroundColor = UIColor.black.withAlphaComponent(0.7)
        view.addSubview(coverVeiw)
        
        contentView = UIView.init(frame: CGRect.init(x: 0, y: kScreenHeight, width: kScreenWidth, height: contentViewHeight))
        contentView.backgroundColor = UIColor.orange
        coverVeiw.addSubview(contentView)
        
        setupLayout()
        NetworkGetAllPublication()
    }
}

// MARK: 点击屏幕弹出退出
extension TRPublicationViewController {
    
    func sheetViewDismiss() {
        UIView.animate(withDuration: 0.25, animations: {
            var frame = self.contentView.frame
            frame.origin.y = kScreenHeight
            self.contentView.frame = frame
            self.coverVeiw.alpha = 0
            
        }) { (_) in
            self.dismiss(animated: false, completion: nil)
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        sheetViewDismiss()
    }
    
    @objc func cancelBtnDidClick(btn: UIButton) {
        sheetViewDismiss()
    }
}

