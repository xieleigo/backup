//
//  TRLoginMeViewController.swift
//  TaxReader
//
//  Created by asdc on 2020/8/5.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit

class TRLoginMeViewController: UIViewController {
    lazy var navView: UIView = {
        let view = LXNavigationBarView.init(frame: .zero)
        view.titleString = "会员中心"
        view.isFirstView = true
        
        return view
    }()
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        loginMeAlert()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        setupLayout()
    }
    
    func setupLayout() {
        self.view.addSubview(self.navView)
        self.navView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(LXNavBarHeight)
        }
    }
    
    func loginMeAlert() {
        if publicAuthorizationToken == nil {
            let alertController = LXAlertController.alertAlert(title: TokenNullTitle, message: TokenNullDetailTitle, okTitle: TokenNullActionDefault, cancelTitle: TokenNullActionCancel) {
                let popoverView = TRWLoginViewController()
                popoverView.modalPresentationStyle = .custom
                popoverView.isTypeShowFromTokenNull = true
                popoverView.loginReloadBlock = {[unowned self] in
                    let meVc = LXNavigationController.init(rootViewController: TRMeViewController())
                    self.tabBarController?.viewControllers![3] = meVc
                    meVc.tabBarItem = UITabBarItem.init(title: "我的",
                                                        image: UIImage(named: "Me"),
                                                        selectedImage: UIImage(named: "Me_1"))
                }
                self.present(popoverView, animated: true, completion: nil)
            }
            self.present(alertController, animated: true, completion: nil)
        }else {
            let meVc = LXNavigationController.init(rootViewController: TRMeViewController())
            self.tabBarController?.viewControllers![3] = meVc
            meVc.tabBarItem = UITabBarItem.init(title: "我的",
                                                image: UIImage(named: "Me"),
                                                selectedImage: UIImage(named: "Me_1"))
        }
    }
}
