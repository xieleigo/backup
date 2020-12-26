//
//  TRNewsDetailDropView.swift
//  TaxReader
//
//  Created by asdc on 2020/4/9.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

class TRNewsDetailDropView: UIView {
    /*
    lazy var trTitleButton: UIButton = {
        let view = UIButton.init(type: .custom)
        view.setTitle("往期浏览", for: .normal)
        view.setTitleColor(UIColor.black, for: .normal)
        
        return view
    }()
 */
    
    lazy var button0: UIButton = {
        let button = UIButton.init(type: .custom)
        button.backgroundColor = UIColor.white
        button.setTitle("阅读", for: .normal)
        button.setTitleColor(UIColor.lightGray, for: .normal)
        button.setTitleColor(TRThemeColor, for: .selected)
        button.setImage(UIImage.init(named: "期刊阅读01"), for: .normal)
        button.setImage(UIImage.init(named: "期刊阅读02"), for: .selected)
        
        return button
    }()
        
    func setUpLayout(){
        self.addSubview(self.button0)
        self.button0.snp.makeConstraints { (make) in
            make.top.left.bottom.right.equalToSuperview()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        setUpLayout()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
