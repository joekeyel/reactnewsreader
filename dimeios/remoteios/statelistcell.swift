//
//  statelistcell.swift
//  DIME
//
//  Created by Hasanul Isyraf on 07/06/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit

class statelistcell: UITableViewCell {
    
    
    @IBOutlet weak var statename: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
