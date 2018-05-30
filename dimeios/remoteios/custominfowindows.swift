//
//  custominfowindows.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 19/09/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class custominfowindows: UIView {

    
    @IBOutlet weak var longitude: UILabel!
    @IBOutlet weak var latitude: UILabel!
  
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

  
}
