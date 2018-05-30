//
//  ttcustomcell.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 03/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class ttcustomcell: UITableViewCell {

    @IBOutlet weak var servicenumber: UILabel!
    @IBOutlet weak var cabinetid: UILabel!
    @IBOutlet weak var customer: UILabel!
    @IBOutlet weak var loginid: UILabel!
    @IBOutlet weak var eside: UILabel!
    
   
    @IBOutlet weak var bgview: UIView!
    @IBOutlet weak var ttno: UILabel!
    @IBOutlet weak var createddate: UILabel!
    @IBOutlet weak var reasoncode: UILabel!
    @IBOutlet weak var contact: UILabel!
    
    @IBOutlet weak var symtomcode: UILabel!
    @IBOutlet weak var priority: UILabel!
    @IBOutlet weak var dside: UILabel!
    
    @IBOutlet weak var remark: UILabel!
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
