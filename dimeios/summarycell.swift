//
//  summarycell.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 20/06/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class summarycell: UITableViewCell {

    @IBOutlet weak var building: UILabel!
    
    @IBOutlet weak var total: UILabel!
    @IBOutlet weak var basket: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}

class cellmenu2: UITableViewCell {
    
    @IBOutlet weak var menuitem: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}

class agingcell : UITableViewCell{


    @IBOutlet weak var bgview: UIView!
    @IBOutlet weak var aging72hours: UILabel!
    @IBOutlet weak var aging48hours: UILabel!
    @IBOutlet weak var aging24hours: UILabel!
    @IBOutlet weak var totalcurrenttt: UILabel!
   
    @IBOutlet weak var totalttmigration: UILabel!
    @IBOutlet weak var totalmigratesub: UILabel!
    @IBOutlet weak var exchange: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        bgview.layer.cornerRadius = 10
    
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }




}

