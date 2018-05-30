//
//  detailscell.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 10/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class detailscell: UITableViewCell {

    @IBOutlet weak var itemdetails: UILabel!
   
     
    @IBOutlet weak var bgview: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        bgview.layer.cornerRadius = 10
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
