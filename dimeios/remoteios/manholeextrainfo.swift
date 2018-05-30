//
//  manholeextrainfo.swift
//  DIME
//
//  Created by Hasanul Isyraf on 30/05/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import Firebase

class manholeextrainfo: UIViewController {
    
    var marker : GMSMarker = GMSMarker()
    var manholename: String = ""
    var createdby: String = ""
    
    @IBOutlet weak var trespassinglabel: UILabel!
    @IBOutlet weak var ownerlabel: UILabel!
    @IBOutlet weak var checkcelcom: DLRadioButton!
    
   
   
    @IBOutlet weak var radiounknown: DLRadioButton!
    @IBOutlet weak var radiodeveloper: DLRadioButton!
    
   
    @IBOutlet weak var radioyes: DLRadioButton!
    
    @IBOutlet weak var radiono: DLRadioButton!
    
  
    @IBOutlet weak var checkunknown: DLRadioButton!
  
    @IBOutlet weak var checktime: DLRadioButton!
    @IBOutlet weak var checkdigi: DLRadioButton!
    
   
    @IBOutlet weak var checkmaxis: DLRadioButton!
    @IBOutlet weak var checkumobile: DLRadioButton!
   
    
    @IBOutlet weak var olnoslabel: UIButton!
    
  
   
    
    @IBOutlet weak var tmradio: DLRadioButton!
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        manholename = marker.title!
        createdby = marker.userData as! String
        
        checkcelcom.isMultipleSelectionEnabled = true
        checkunknown.isMultipleSelectionEnabled = true
        checkdigi.isMultipleSelectionEnabled = true
        checkumobile.isMultipleSelectionEnabled = true
        checkmaxis.isMultipleSelectionEnabled = true
        checktime.isMultipleSelectionEnabled = true
        
        checkcelcom.isIconSquare = true
        checkunknown.isIconSquare = true
        checkdigi.isIconSquare = true
        checkumobile.isIconSquare = true
        checkmaxis.isIconSquare = true
        checktime.isIconSquare = true
        
        
        
        //hide the olnos
        
        olnoslabel.isHidden = true
        checkcelcom.isHidden = true
        checkunknown.isHidden = true
        checkdigi.isHidden = true
        checkumobile.isHidden = true
        checkmaxis.isHidden = true
        checktime.isHidden = true
        
        
        loadcheckboxdisplay(manhole: manholename)
        
        
    }
    
    
    @IBAction func trespassyes(_ sender: DLRadioButton) {
        
        olnoslabel.isHidden = false
        checkcelcom.isHidden = false
        checkunknown.isHidden = false
        checkdigi.isHidden = false
        checkumobile.isHidden = false
        checkmaxis.isHidden = false
        checktime.isHidden = false
        
        updatefirebasetrespass(trespass: "Yes")
    }
 
    
    @IBAction func trespassno(_ sender: DLRadioButton) {
        
        olnoslabel.isHidden = true
        checkcelcom.isHidden = true
        checkunknown.isHidden = true
        checkdigi.isHidden = true
        checkumobile.isHidden = true
        checkmaxis.isHidden = true
        checktime.isHidden = true
        
        updatefirebasetrespass(trespass: "No")
    }
    
   
    
    func updatefirebaseowner(owner:String){
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        
        
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            
            ref.child("manholeinfo").child(manholename).child("owner").setValue(owner)
            
            updateextrainfomysql(manholeid: manholename, indicator: "owner", item: owner)
            
        }
    }
    
    
    func updatefirebasetrespass(trespass:String){
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        
        
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            
            ref.child("manholeinfo").child(manholename).child("trespass").setValue(trespass)
            updateextrainfomysql(manholeid: manholename, indicator: "trespass", item: trespass)
            
            
            
        }
    }
    
    func updatefirebaseolnos(olnos:String){
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        
        
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            
            ref.child("manholeinfo").child(manholename).child("olnos").childByAutoId().setValue(olnos)
            updateextrainfomysql(manholeid: manholename, indicator: "olnos", item: olnos)
            
            
            
        }
    }
    
    func loadcheckboxdisplay(manhole:String){
        
        
        
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("manholeinfo").child(manhole)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            
            
            for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//owner,trepass,olnos level
                
                
                
                
                
                if(rest2.key == "owner"){
                    
                    let ownerstr = rest2.value as! String
                    if(ownerstr == "TM"){
                        
                        self.tmradio.isSelected = true
                        
                    }
                    if(ownerstr == "DEVELOPER"){
                        
                        self.radiodeveloper.isSelected = true
                        
                        
                    }
                    if(ownerstr == "UNKNWON"){
                        
                        self.radiounknown.isSelected = true
                        
                    }
                    
                    
                }
                
                if(rest2.key == "trespass") {
                    
                    let ownerstr = rest2.value as! String
                    if(ownerstr == "Yes"){
                        
                        self.radioyes.isSelected = true
                        
                        self.olnoslabel.isHidden = false
                        self.checkcelcom.isHidden = false
                        self.checkunknown.isHidden = false
                        self.checkdigi.isHidden = false
                        self.checkumobile.isHidden = false
                        self.checkmaxis.isHidden = false
                        self.checktime.isHidden = false
                        
                    }
                    if(ownerstr == "No"){
                        
                        self.radiono.isSelected = true
                    }
                    
                    
                    
                }
                
                //loop through the olnos child to get the value
                
                if(rest2.key == "olnos"){
                    
                    for rest4 in rest2.children.allObjects as! [FIRDataSnapshot] {
                        
                        
                        let olnosstr = rest4.value as! String
                        
                        if(olnosstr == "Celcom"){
                            
                            self.checkcelcom.isMultipleSelectionEnabled = true
                            
                            self.checkcelcom.isSelected = true
                            
                            print("Celcom")
                        }
                        
                        if(olnosstr == "Maxis"){
                            
                            self.checkmaxis.isSelected = true
                        }
                        if(olnosstr == "Digi"){
                            
                            self.checkdigi.isSelected = true
                        }
                        
                        if(olnosstr == "Umobile"){
                            
                            self.checkumobile.isMultipleSelectionEnabled = true
                            self.checkumobile.isSelected = true
                        }
                        
                        if(olnosstr == "Time"){
                            
                            self.checktime.isSelected = true
                        }
                        
                        if(olnosstr == "UNKNOWN"){
                            
                            self.checkunknown.isSelected = true
                        }
                        
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                }
                
                
                
                
            }
            
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        
        
        
        
    }
    
    func deleteolnosfirebase(manhole:String,olnos:String){
        
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        if(currentuser == self.createdby){
            let referencephotomarkerinitial = FIRDatabase.database().reference().child("manholeinfo").child(manhole)
            referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
                
                
                
                for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//owner,trepass,olnos level
                    
                    
                    //loop through the olnos child to get the value
                    
                    if(rest2.key == "olnos"){
                        
                        for rest4 in rest2.children.allObjects as! [FIRDataSnapshot] {
                            
                            
                            let olnosstr = rest4.value as! String
                            let keyid = rest4.key
                            
                            if(olnosstr == olnos){
                                
                                
                                
                                var ref: FIRDatabaseReference!
                                ref = FIRDatabase.database().reference()
                                ref.child("manholeinfo").child(self.manholename).child("olnos").child(keyid).removeValue()
                                
                                
                                
                            }
                        }
                        
                        
                    }
                    
                    
                    
                    
                }
                
                
                
            }) { (nil) in
                print("error firebase listner")
            }
            //delete in mysql
            deleteolnosmysql(manholeid: manholename, indicator: "olnos", item: olnos)
            
        }
        
        
        
        
        
    }
    
    @IBAction func radioownertm(_ sender: DLRadioButton) {
           updatefirebaseowner(owner: "TM")
    }
   
    
    @IBAction func radioownerdeveloper(_ sender: DLRadioButton) {
        
        updatefirebaseowner(owner: "DEVELOPER")
    }
   
    
    @IBAction func radioownerunknown(_ sender: DLRadioButton) {
        updatefirebaseowner(owner: "UNKNOWN")
    }
  
    @IBAction func celcomcheckbox(_ sender: DLRadioButton) {
        if(checkcelcom.isSelected){
            print("Celcom Check")
            
            updatefirebaseolnos(olnos: "Celcom")
        }else{
            
            print("Celcom Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "Celcom")
            
        }
    }
    
  
    
    @IBAction func timecheckbox(_ sender: DLRadioButton) {
        
        if(checktime.isSelected){
            print("Time Check")
            updatefirebaseolnos(olnos: "Time")
        }else{
            
            print("Time Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "Time")
            
        }
    }
    
  
    @IBAction func maxischeckbox(_ sender: DLRadioButton) {
        
        if(checkmaxis.isSelected){
            print("Maxis Check")
            updatefirebaseolnos(olnos: "Maxis")
        }else{
            
            print("Maxis Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "Maxis")
            
        }
    }
  
    @IBAction func umobilecheckbox(_ sender: DLRadioButton) {
        
        if(checkumobile.isSelected){
            print("Umobile Check")
            updatefirebaseolnos(olnos: "Umobile")
        }else{
            
            print("Umobile Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "Umobile")
            
        }
    }
    
    @IBAction func digicheckbox(_ sender: DLRadioButton) {
        
        if(checkdigi.isSelected){
            print("Digi Check")
            updatefirebaseolnos(olnos: "Digi")
        }else{
            
            print("Digi Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "Digi")
            
        }
    }
    
    
   
    @IBAction func unknowncheckbox(_ sender: DLRadioButton) {
        
        if(checkunknown.isSelected){
            print("Uknown Check")
            updatefirebaseolnos(olnos: "UNKNOWN")
        }else{
            
            print("Uknown Uncheck")
            deleteolnosfirebase(manhole: manholename, olnos: "UNKNOWN")
            
        }
    }
    
   
    
    
    func updateextrainfomysql(manholeid:String,indicator:String,item:String) {
        let parameters = ["manholeid" : manholeid  ,
                          "item" : item,
                          "indicator" : indicator,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? ""
            ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/updateextrainfo.php")! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
        
        
        request.httpBody = postString.data(using: String.Encoding.utf8)
        let task = URLSession.shared.dataTask(with: request as URLRequest) { data, response, error in
            guard error == nil && data != nil else {                                                          // check for fundamental networking error
                print("error=\(String(describing: error))")
                return
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode != 200 {           // check for http errors
                print("statusCode should be 200, but is \(httpStatus.statusCode)")
                print("response = \(String(describing: response))")
                
                
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode == 200 {           // check for http errors
                
                print("response = \(String(describing: response))")
                self.showToast(message: "Updated")
                
            }
            
            
            
            
            
        }
        task.resume()
        
    }
    
    
    func deleteolnosmysql(manholeid:String,indicator:String,item:String) {
        let parameters = ["manholeid" : manholeid  ,
                          "item" : item,
                          "indicator" : indicator,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? ""
            ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/deleteolnos.php")! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
        
        
        request.httpBody = postString.data(using: String.Encoding.utf8)
        let task = URLSession.shared.dataTask(with: request as URLRequest) { data, response, error in
            guard error == nil && data != nil else {                                                          // check for fundamental networking error
                print("error=\(String(describing: error))")
                return
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode != 200 {           // check for http errors
                print("statusCode should be 200, but is \(httpStatus.statusCode)")
                print("response = \(String(describing: response))")
                
                
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode == 200 {           // check for http errors
                
                print("response = \(String(describing: response))")
                self.showToast(message: "Updated")
                
            }
            
            
            
            
            
        }
        task.resume()
        
    }
}
