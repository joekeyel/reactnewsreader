//
//  cellmenupopover.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 18/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class cellmenupopover: UITableViewCell {

    @IBOutlet weak var updateby: UILabel!
    @IBOutlet weak var lastmodified: UILabel!
    @IBOutlet weak var menuitem: UILabel!
     
    @IBOutlet weak var bgview: UIView!
    override func awakeFromNib() {
        bgview.layer.cornerRadius = 10
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
