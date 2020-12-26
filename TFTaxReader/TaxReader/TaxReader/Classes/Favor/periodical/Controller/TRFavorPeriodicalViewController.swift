//
//  TRFavorPeriodicalViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/4/14.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

class TRFavorPeriodicalViewController: UIViewController, UIViewControllerTransitioningDelegate {
    
    var dataArr: [TRFavorFindDataModel]? = []
    
    private let TRFavorPeriodicalTableViewCellID = "TRFavorPeriodicalTableViewCell"
    lazy var tableView: UITableView = {
        let tableView = UITableView.init(frame: .zero, style: UITableView.Style.grouped)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.backgroundColor = LXTableViewBackgroundColor
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.register(TRFavorPeriodicalTableViewCell.self, forCellReuseIdentifier: TRFavorPeriodicalTableViewCellID)
        
        return tableView
    }()
    
    func setupLayout() {
        self.view.addSubview(self.tableView)
        self.tableView.snp.makeConstraints { (make) in
            make.top.left.right.bottom.equalToSuperview()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        NetworkFavorFind()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.cyan
        
        setupLayout()
    }
    
    lazy var networkViewModel: TaxReaderViewModel = {
        return TaxReaderViewModel()
    }()
}

extension TRFavorPeriodicalViewController {
    func NetworkFavorFind() {
        networkViewModel.updateBlock = {[unowned self] in
            if self.networkViewModel.favorFindModel?.ret == false {
                
                // 3000 authorization参数不能为空
                if self.networkViewModel.favorFindModel?.msgCode == NetDataAuthorizationNull {
                    let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                        let popoverView = TRWLoginViewController()
                        popoverView.modalPresentationStyle = .custom
                        popoverView.isTypeShowFromTokenNull = true
                        popoverView.loginReloadBlock = {[unowned self] in
                            self.NetworkFavorFind()
                        }
                        self.present(popoverView, animated: true, completion: nil)
                    }
                    self.present(alertController, animated: true, completion: nil)
                }else {
                    MBProgressHUD.showWithText(text: self.networkViewModel.favorFindModel?.msg ?? "", view: self.view)
                }
                
                return
            }
            
            self.dataArr = self.networkViewModel.favorFindModel?.data
            self.tableView.reloadData()
        }
        
        networkViewModel.refreshDataSource_FavorFind(FavoriteID: "", ReadFavoriteType: "20") 
    }
}

extension TRFavorPeriodicalViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.dataArr?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 128
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: TRFavorPeriodicalTableViewCellID, for: indexPath) as! TRFavorPeriodicalTableViewCell
        
        let model: TRFavorFindDataModel = self.dataArr?[indexPath.section] ?? TRFavorFindDataModel.init()
        cell.findDataModel = model
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        let model: TRFavorFindDataModel = self.dataArr?[indexPath.section] ?? TRFavorFindDataModel.init()
        let nextVc = TRNewsDetailViewController(PubIssueID: "\(model.PubIssueID)")
        self.navigationController?.pushViewController(nextVc, animated: true)
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 0.01
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        return nil
    }
    
    func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        let headerView: UITableViewHeaderFooterView = view as! UITableViewHeaderFooterView
        headerView.contentView.backgroundColor = UIColor.red
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 8
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        return nil
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
            let model: TRFavorFindDataModel = self.dataArr?[indexPath.section] ?? TRFavorFindDataModel.init()
            editingStyleDeleteCell(PubIssueID: "\(model.PubIssueID)")
        }
    }
}

extension TRFavorPeriodicalViewController {
    func editingStyleDeleteCell(PubIssueID: String) {
        print("PubIssueID = \(PubIssueID)")
        
        let alert = UIAlertController.init(title: "您确定要删除这个信息吗?", message: "", preferredStyle: .alert)
        let confirmaction = UIAlertAction.init(title: "确定", style: .default) { (UIAlertAction) in
            self.networkViewModel.updateBlock = {[unowned self] in
                MBProgressHUD.showWithText(text: self.networkViewModel.favorCancelModel?.msg ?? "", view: self.view)
                return
                
                self.NetworkFavorFind()
            }
            self.networkViewModel.refreshDataSource_FavorCancel(FavoriteID: PubIssueID, ReadFavoriteType: "20")
        }
        let cancleAction = UIAlertAction.init(title: "取消", style: .cancel, handler: nil)
        alert.addAction(confirmaction)
        alert.addAction(cancleAction)
        self.present(alert, animated: true, completion: nil)
    }
}


