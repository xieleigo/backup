//
//  ExtensionScrollView.swift
//  TaxReader
//
//  Created by asdc on 2020/8/6.
//  Copyright © 2020 TongFangXL. All rights reserved.
//

import Foundation

extension UIScrollView {
    func scrollToTop() {
        let desiredOffset = CGPoint(x: 0, y: -contentInset.top)
        setContentOffset(desiredOffset, animated: true)
    }
}
