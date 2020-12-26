//
//  TRNewsYearView.swift
//  TaxReader
//
//  Created by asdc on 2020/6/17.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

protocol TRNewsYearViewDelegate: NSObjectProtocol {
    func tableViewDidSelectRowAt(_ tableView: UITableView, indexPath: IndexPath, modelYear: String?)
}

class TRNewsYearView: UIView {
    
    weak var delegate: TRNewsYearViewDelegate?
    var dataArray: [TRProductGetPubYearIssueDataModel]?
    
    lazy var trContentView: UIView = {
        let view = UIView.init(frame: .zero)
        view.backgroundColor = UIColor.cyan
        
        return view
    }()
    
    private let identific = "TRNewsYearTableViewCell"
    lazy var tableView: UITableView = {
        let tableView = UITableView.init(frame: .zero, style: UITableView.Style.plain)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.backgroundColor = UIColor.white
        tableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        tableView.register(UINib.init(nibName: identific, bundle: nil), forCellReuseIdentifier: identific)
        tableView.showsVerticalScrollIndicator = false
        
        return tableView
    }()
    
    func setupLayout() {
        self.addSubview(self.trContentView)
        self.trContentView.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
        }
        
        self.trContentView.addSubview(self.tableView)
        self.tableView.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setupLayout()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    var dataArrayPubIssueDataModel: [TRProductGetPubYearIssueDataModel]? {
        didSet {
            guard let dataArrayModel = dataArrayPubIssueDataModel else {
                return
            }
            
            self.dataArray = dataArrayModel
            self.tableView.reloadData()
            
            // 如果要有默认选择在初始化tableView完成后写
            let indexPath = NSIndexPath.init(row: 0, section: 0) as IndexPath
            tableView.selectRow(at: indexPath, animated: true, scrollPosition: .none)
            let model: TRProductGetPubYearIssueDataModel = self.dataArray?[indexPath.row] ?? TRProductGetPubYearIssueDataModel.init()
            if (self.delegate != nil) && ((self.delegate?.responds(to: Selector.init(("didSelectRowAt")))) != nil) {
                self.delegate?.tableViewDidSelectRowAt(tableView, indexPath: indexPath, modelYear: "\(model.Year)")
            }
        }
    }
}

extension TRNewsYearView: UITableViewDataSource, UITableViewDelegate {
    // MARK: UITableViewDataSource
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataArray?.count ?? 0
    }
    
    public func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 55.0
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
        let cell = tableView.dequeueReusableCell(withIdentifier: identific, for: indexPath) as! TRNewsYearTableViewCell
        let model: TRProductGetPubYearIssueDataModel = self.dataArray?[indexPath.row] ?? TRProductGetPubYearIssueDataModel.init()
        cell.trYearLabel.text = "\(model.Year)"
        
        return cell
    }
    
    public func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        tableView.selectRow(at: indexPath, animated: true, scrollPosition: .none)
        
        let model: TRProductGetPubYearIssueDataModel = self.dataArray?[indexPath.row] ?? TRProductGetPubYearIssueDataModel.init()
        if (self.delegate != nil) && ((self.delegate?.responds(to: Selector.init(("didSelectRowAt")))) != nil) {
            self.delegate?.tableViewDidSelectRowAt(tableView, indexPath: indexPath, modelYear: "\(model.Year)")
        }
    }
}

