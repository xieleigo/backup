//
//  TRLoginLogoView.swift
//  TaxReader
//
//  Created by asdc on 2020/5/11.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

typealias TRLoginLogoViewBackButtonClickBlock = () ->Void

class TRLoginLogoView: UIView {
    
    var navBackButtonClickBlock: TRLoginLogoViewBackButtonClickBlock?
    
    // 返回
    private lazy var backButton: UIButton = {
        let button = UIButton.init(type: .custom)
        button.setBackgroundImage(UIImage.init(named: "返回白"), for: .normal)
        button.addTarget(self, action: #selector(navBackButtonClick(backButton:)), for: .touchUpInside)
        
        return button
    }()
    
    @objc func navBackButtonClick(backButton:UIButton) {
        guard let navBackButtonClickBlock = navBackButtonClickBlock else { return }
        navBackButtonClickBlock()
    }
    
    lazy var logoUnitLabel: UILabel = {
        let view = UILabel.init()
        view.text = "中国税务杂志社"
        view.textColor = UIColor.white
        view.font = UIFont.systemFont(ofSize: 28.0)
        view.textAlignment = .center
        
        return view
    }()
    
    lazy var logoLabel: UILabel = {
        let view = UILabel.init()
        view.text = "税刊阅读"
        view.textColor = UIColor.white
        view.font = UIFont.systemFont(ofSize: 18.0)
        
        return view
    }()
    
    func setupLayout() {
        self.addSubview(self.backButton)
        self.backButton.snp.makeConstraints { (make) in
            make.top.equalTo(LXPhoneLHNavStatusHeight+8)
            make.left.equalTo(8)
            make.size.equalTo(36)
        }
        
        self.addSubview(self.logoUnitLabel)
        self.logoUnitLabel.snp.makeConstraints { (make) in
            make.centerX.equalToSuperview()
            make.centerY.equalToSuperview()
            make.width.equalTo(LXScreenWidth - 100*scaleHeightSE2nd)
        }
        
        self.addSubview(self.logoLabel)
        self.logoLabel.snp.makeConstraints { (make) in
            make.top.equalTo(self.logoUnitLabel.snp.bottom).offset(12)
            make.centerX.equalToSuperview()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setupLayout()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
