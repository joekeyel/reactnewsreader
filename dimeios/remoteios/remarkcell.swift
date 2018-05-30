//
//  remarkcell.swift
//  DIME
//
//  Created by Hasanul Isyraf on 30/05/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit

class remarkcell: UITableViewCell {

    
    @IBOutlet weak var createdby: UILabel!
    
    @IBOutlet weak var iconimage: UIImageView!
   
    
    @IBOutlet weak var remarklabel: UILabel!
    @IBOutlet weak var createddate: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
