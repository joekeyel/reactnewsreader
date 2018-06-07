//
//  statepopover.swift
//  DIME
//
//  Created by Hasanul Isyraf on 07/06/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Firebase


protocol sendDataToViewstatelist {
     func firebaselistenerstate(state:String)
}

class statepopover: UIViewController,UITableViewDataSource,UITableViewDelegate {
 
    @IBOutlet weak var tablestate: UITableView!
    var statelist = [String]()
       var delegate:sendDataToViewstatelist? = nil
    

    override func viewDidLoad() {
        super.viewDidLoad()

     
       loadstatelistfirebase()
    }

   
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return statelist.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
          let cell = tableView.dequeueReusableCell(withIdentifier: "statelistcell", for: indexPath) as? statelistcell
        
        
        cell?.statename.text = self.statelist[indexPath.item]
        
        return cell!
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let state = self.statelist[indexPath.item]
        if(delegate != nil){
        
            
            delegate?.firebaselistenerstate(state: state)
            self.dismiss(animated: true, completion: nil)
            
            
            
        }
    }
   
    
    
    func loadstatelistfirebase(){
        
        
        // initally load photo marker once
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("statelist")
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
    
            
            
            for rest in snapshot.children.allObjects as! [FIRDataSnapshot]{
                
               let state = rest.key
                self.statelist.append(state)
            }
            
            
            
            
            DispatchQueue.main.async {
                
                
               self.tablestate.reloadData()
               
            }
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
    }

}
