//
//  infowindowsmaps.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 20/09/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class infowindowsmaps: UIView {

    @IBOutlet weak var titleimage: UILabel!
    @IBOutlet weak var imageview: UIImageView!
    @IBOutlet weak var longitude: UILabel!
    @IBOutlet weak var latitude: UILabel!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func loadView() -> infowindowsmaps{
        let customInfoWindow = Bundle.main.loadNibNamed("infowindowsmaps", owner: self, options: nil)?[0] as! infowindowsmaps
        return customInfoWindow
    }


}
