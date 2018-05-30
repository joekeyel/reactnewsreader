//
//  searchcell.swift
//  idraw
//
//  Created by Hasanul Isyraf on 12/01/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit

class searchcell: UITableViewCell {

   
    @IBOutlet weak var labelmarker: UILabel!
    
    @IBOutlet weak var imagemarker: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
