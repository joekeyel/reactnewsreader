//
//  shcedulecell.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 20/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class shcedulecell: UITableViewCell {

    @IBOutlet weak var projecttype: UILabel!
    @IBOutlet weak var bgview: UIView!
    @IBOutlet weak var site: UILabel!
    
    @IBOutlet weak var ptt: UILabel!
    @IBOutlet weak var finishdate: UILabel!
    @IBOutlet weak var newcabinet: UILabel!
    
    @IBOutlet weak var status: UILabel!
    @IBOutlet weak var pmwno: UILabel!
    @IBOutlet weak var migrationdate: UILabel!
    @IBOutlet weak var newckc: UILabel!
    @IBOutlet weak var oldckc: UILabel!
    @IBOutlet weak var oldcabinet: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        
        //set rounded corner top only for site text
//        let rectShape = CAShapeLayer()
//        rectShape.bounds = self.site.frame
//        rectShape.position = self.site.center
//        rectShape.path = UIBezierPath(roundedRect: self.site.bounds, byRoundingCorners: [.bottomRight], cornerRadii: CGSize(width: 10, height: 10)).cgPath
//
//       self.site.layer.mask = rectShape
        
        
        bgview.layer.cornerRadius = 10
    }
    
    

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
