//
//  TRInvoiceCompanyViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/5/25.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

class TRInvoiceCompanyViewController: UIViewController {
    var dataArray: [TRInvoiceInfoDataModel]? = []
        
    private let LXHeaderLabelViewID = "LXHeaderLabelView"
    private let TRInvoiceCompanyTableViewCellID = "TRInvoiceCompanyTableViewCell"
    lazy var tableView: UITableView = {
        let tableView = UITableView.init(frame: .zero, style: UITableView.Style.plain)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.backgroundColor = UIColor.white
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.singleLine
        tableView.register(LXHeaderLabelView.self, forHeaderFooterViewReuseIdentifier: LXHeaderLabelViewID)
        tableView.register(TRInvoiceCompanyTableViewCell.self, forCellReuseIdentifier: TRInvoiceCompanyTableViewCellID)
        
        return tableView
    }()
    
    func setupLayout() {
        self.view.addSubview(self.tableView)
        self.tableView.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        NetWorkInvoiceInfo()
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor.cyan
        setupLayout()
    }
        
    lazy var viewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TRInvoiceCompanyViewController {
    func NetWorkInvoiceInfo() {
        viewModel.updateBlock = {[unowned self] in
            
            if self.viewModel.invoiceInfoModel?.ret == false {
                MBProgressHUD.showWithText(text: self.viewModel.invoiceInfoModel?.msg ?? "", view: self.view)
                
                return
            }

            
            self.dataArray?.removeAll()
            for lindex in 0..<(self.viewModel.invoiceInfoModel?.data!.count)!{
                let infoModel:TRInvoiceInfoDataModel = self.viewModel.invoiceInfoModel?.data?[lindex] ?? TRInvoiceInfoDataModel.init()
                if infoModel.UserInvoiceReceiveType == 2 {
                    self.dataArray?.append(infoModel)
                }
            }
            self.tableView.reloadData()
        }
        viewModel.refreshDataSource_InvoiceInfo()
    }
}

extension TRInvoiceCompanyViewController: UITableViewDataSource, UITableViewDelegate {
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataArray?.count ?? 0
    }
    
    public func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 155.0
    }
        
    public func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return CGFloat.leastNormalMagnitude
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: TRInvoiceCompanyTableViewCellID, for: indexPath) as! TRInvoiceCompanyTableViewCell
        
        let infoModel: TRInvoiceInfoDataModel = self.dataArray?[indexPath.row] ?? TRInvoiceInfoDataModel.init()
        cell.infoDataModel = infoModel
        cell.defaultButtonBlock = {[unowned self](button) in
            let infoModel: TRInvoiceInfoDataModel = self.dataArray?[indexPath.row] ?? TRInvoiceInfoDataModel.init()
            self.blockCellButtonSetDefault(UserInvoiceID: String.init(describing: infoModel.UserInvoiceID))
        }
        
        return cell
    }
    
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        let infoModel: TRInvoiceInfoDataModel = self.dataArray?[indexPath.row] ?? TRInvoiceInfoDataModel.init()
        let nextVc = TRAddInvoiceViewController(navTitle: "编辑开票信息", invoiceInfoDataModel: infoModel)
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
    
    func tableView(_ tableView: UITableView, willDisplayFooterView view: UIView, forSection section: Int) {
        let footerView: UITableViewHeaderFooterView = view as! UITableViewHeaderFooterView
        footerView.contentView.backgroundColor = LXGrayColor
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, titleForDeleteConfirmationButtonForRowAt indexPath: IndexPath) -> String? {
        return "删除"
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == UITableViewCell.EditingStyle.delete {
            let infoModel: TRInvoiceInfoDataModel = self.dataArray?[indexPath.row] ?? TRInvoiceInfoDataModel.init()
            editingStyleDeleteCell(UserInvoiceID: String.init(describing: infoModel.UserInvoiceID))
        }
    }
}

extension TRInvoiceCompanyViewController {
    func editingStyleDeleteCell(UserInvoiceID: String) {
        print("UserInvoiceID = \(UserInvoiceID)")
        
        let alert = UIAlertController.init(title: "您确定要删除这个信息吗?", message: "", preferredStyle: .alert)
        let confirmaction = UIAlertAction.init(title: "确定", style: .default) { (UIAlertAction) in
            self.viewModel.updateBlock = {[unowned self] in
                MBProgressHUD.showWithText(text: self.viewModel.invoiceDeleteModel?.msg ?? "", view: self.view)
                
                if self.viewModel.invoiceDeleteModel?.ret == false {
                    MBProgressHUD.showWithText(text: self.viewModel.invoiceDeleteModel?.msg ?? "", view: self.view)
                    
                    return
                }

                
                self.NetWorkInvoiceInfo()
            }
            self.viewModel.refreshDataSource_InvoiceDelete(UserInvoiceID: UserInvoiceID)
        }
        let cancleAction = UIAlertAction.init(title: "取消", style: .cancel, handler: nil)
        alert.addAction(confirmaction)
        alert.addAction(cancleAction)
        self.present(alert, animated: true, completion: nil)
    }
}

extension TRInvoiceCompanyViewController {
    func blockCellButtonSetDefault(UserInvoiceID: String) {
        print("UserInvoiceID = \(UserInvoiceID)")
        
        let alert = UIAlertController.init(title: "您确定要设置为默认开票吗?", message: "", preferredStyle: .alert)
        let confirmaction = UIAlertAction.init(title: "确定", style: .default) { (UIAlertAction) in
            self.viewModel.updateBlock = {[unowned self] in
                MBProgressHUD.showWithText(text: self.viewModel.invoiceDeleteModel?.msg ?? "", view: self.view)
                
                if self.viewModel.invoiceDeleteModel?.ret == false {
                    MBProgressHUD.showWithText(text: self.viewModel.invoiceDeleteModel?.msg ?? "", view: self.view)                    
                    return
                }

                
                self.NetWorkInvoiceInfo()
            }
            self.viewModel.refreshDataSource_InvoiceSetDefault(UserInvoiceID: UserInvoiceID)
        }
        let cancleAction = UIAlertAction.init(title: "取消", style: .cancel, handler: nil)
        alert.addAction(confirmaction)
        alert.addAction(cancleAction)
        self.present(alert, animated: true, completion: nil)
    }
}

