//
//  SceneDelegate.swift
//  TaxReader
//
//  Created by asdc on 2020/3/27.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import UIKit
import SwiftDate

// authorization 全局变量
var publicAuthorizationToken: String?
// 正常退出时，回退按钮回到首页；其他情况，返回触发登录的那个页面
var publicLoginOut: Bool = false

class SceneDelegate: UIResponder, UIWindowSceneDelegate, WXApiDelegate{

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        guard let _ = (scene as? UIWindowScene) else { return }
        
        print("NSHomeDirectory = \(NSHomeDirectory())")
        
        // 转交给 SceneDelegate 的willConnectTo Session:方法进行根控制器设置
        self.window?.backgroundColor = UIColor.white
        let remLoginText = UserDefaults.LoginInfo.string(forKey: .rem_login)
        self.window?.rootViewController = (remLoginText == "1") ? TRWLoginViewController() : LXTabbarProvider.TRsystemStyle()
        
        //self.window?.rootViewController = LXTabbarProvider.TRsystemStyle()
        self.window?.makeKeyAndVisible()
        
        Bugly.start(withAppId: buglyAppID)
        
        WXApi.startLog(by: .detail) { (log) in
            print("log = \(log)")
        }
        WXApi.registerApp(wxPayAppId, universalLink: appIp)
    }
    
    func application(_ application: UIApplication, handleOpen url: URL) -> Bool {
        return WXApi.handleOpen(url, delegate: WXApiManager.shared())
    }
    
    func application(_ application: UIApplication, open url: URL, sourceApplication: String?, annotation: Any) -> Bool {
        return WXApi.handleOpen(url, delegate: WXApiManager.shared())
    }
    
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        return WXApi.handleOpenUniversalLink(userActivity, delegate: WXApiManager.shared())
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        return WXApi.handleOpen(url, delegate: WXApiManager.shared())
    }
    
    func onResp(_ resp: BaseResp) {
        switch resp.errCode {
        case 0:
            print("支付成功")
        default:
            print("支付失败")
        }
    }
    
    func scene(_ scene: UIScene, continue userActivity: NSUserActivity) {
        WXApi.handleOpenUniversalLink(userActivity, delegate: self)
    }
    
    func scene(_ scene: UIScene, openURLContexts URLContexts: Set<UIOpenURLContext>) {
        let context = URLContexts.first;
        WXApi.handleOpen(context!.url, delegate: self)
    }

    func sceneDidDisconnect(_ scene: UIScene) {
        // Called as the scene is being released by the system.
        // This occurs shortly after the scene enters the background, or when its session is discarded.
        // Release any resources associated with this scene that can be re-created the next time the scene connects.
        // The scene may re-connect later, as its session was not neccessarily discarded (see `application:didDiscardSceneSessions` instead).
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
        // Called when the scene has moved from an inactive state to an active state.
        // Use this method to restart any tasks that were paused (or not yet started) when the scene was inactive.
    }

    func sceneWillResignActive(_ scene: UIScene) {
        // Called when the scene will move from an active state to an inactive state.
        // This may occur due to temporary interruptions (ex. an incoming phone call).
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
        // Called as the scene transitions from the background to the foreground.
        // Use this method to undo the changes made on entering the background.
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
        // Called as the scene transitions from the foreground to the background.
        // Use this method to save data, release shared resources, and store enough scene-specific state information
        // to restore the scene back to its current state.
    }


}

