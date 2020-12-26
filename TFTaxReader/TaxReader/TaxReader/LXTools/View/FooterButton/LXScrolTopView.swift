//
//  LXScrolTopView.swift
//  TaxReader
//
//  Created by asdc on 2020/8/6.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

typealias LXScrolTopViewBlock = (_ button: UIButton) ->Void

class LXScrolTopView: UIView {
    
    var buttonClickBlock: LXScrolTopViewBlock?
    
    lazy var contentView: UIView = {
        let view = UIView.init()
        
        return view
    }()
        
    lazy var circlebutton:UIButton = {
        let view = UIButton.init(type: UIButton.ButtonType.custom)
        view.setImage(UIImage.init(named: "shangjiantou_huaban"), for: .normal)
        view.setImage(UIImage.init(named: "xiajiantou_huaban"), for: .selected)
        view.addTarget(self, action: #selector(circlebutton(button:)), for: .touchUpInside)
        
        return view
    }()
    
    @objc func circlebutton(button:UIButton) {
        guard let buttonClickBlock = buttonClickBlock else { return }
        buttonClickBlock(button)
    }
    
    func setupLayout() {
        self.addSubview(self.contentView)
        self.contentView.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
        }
        
        self.contentView.addSubview(self.circlebutton)
        self.circlebutton.snp.makeConstraints { (make) in
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
}
