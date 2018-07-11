//
//  popoveroccupancy.swift
//  idraw
//
//  Created by Hasanul Isyraf on 10/03/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import Firebase


protocol protocolupdateoccupancy {
  
  
    func getter2()
   
}
class popoveroccupancy: UIViewController{
  
    @IBOutlet weak var occupancylabel: UILabel!
    @IBOutlet weak var occupancy: UILabel!
    @IBOutlet weak var slideroccupancy: UISlider!
    
    @IBOutlet weak var deleteduct: UIButton!
    @IBOutlet weak var updateoccupancy: UIButton!
    @IBOutlet weak var pickerview: UIPickerView!
    @IBOutlet weak var cablecodetext: UITextField!
    
    @IBOutlet weak var one3: DLRadioButton!
    @IBOutlet weak var zero1: DLRadioButton!
    
    @IBOutlet weak var zero3: DLRadioButton!
    @IBOutlet weak var two3: DLRadioButton!
    @IBOutlet weak var three3: DLRadioButton!
    @IBOutlet weak var one1: DLRadioButton!
    
    var updatebutton = UIButton()
    
    var delegate:protocolupdateoccupancy? = nil
    let statusoccupancy = ["AVAILABLE","PARTIALLY UTILIZED","FULLY UTILIZED","ABANDONED"]
    
    var occupancystr = "AVAILABLE"
    var ductname = ""
    var utilization = 0
    var occupancyselector = ""
    var wallnumber = ""
    var manholename = ""
    var createdby = ""
    var nestduct = ""
    var marker = GMSMarker()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        manholename = marker.title!
        createdby = marker.userData as! String
        
        view.addSubview(slideroccupancy)
        slideroccupancy.translatesAutoresizingMaskIntoConstraints = false
        
        self.slideroccupancy.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.slideroccupancy.topAnchor.constraint(equalTo: view.topAnchor,constant:40).isActive = true
        self.slideroccupancy.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        //set initial
        self.slideroccupancy.setValue(Float(utilization), animated: true)
        
        
        view.addSubview(occupancylabel)
        occupancylabel.translatesAutoresizingMaskIntoConstraints = false
        
        occupancylabel.text = "\(manholename) \(wallnumber) \(nestduct) \(ductname)"
        
        loadduct(manhole: manholename, wallnumber: wallnumber, nestduct: nestduct, duct: ductname)
        
        self.occupancylabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.occupancylabel.bottomAnchor.constraint(equalTo: slideroccupancy.topAnchor,constant:2).isActive = true
        
        self.occupancylabel.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        view.addSubview(occupancy)
        occupancy.translatesAutoresizingMaskIntoConstraints = false
        
        self.occupancy.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.occupancy.topAnchor.constraint(equalTo: deleteduct.bottomAnchor,constant:6).isActive = true
        self.occupancy.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        occupancy.text = String(utilization)
        
        
        view.addSubview(cablecodetext)
        cablecodetext.translatesAutoresizingMaskIntoConstraints = false
        
        self.cablecodetext.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.cablecodetext.topAnchor.constraint(equalTo: slideroccupancy.bottomAnchor,constant:2).isActive = true
        
        self.cablecodetext.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        
        
        
        //start with zero3
        
        view.addSubview(zero3)
        zero3.translatesAutoresizingMaskIntoConstraints = false
        self.zero3.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.zero3.leftAnchor.constraint(equalTo: view.leftAnchor,constant:4).isActive = true
        
        
        view.addSubview(one3)
        one3.translatesAutoresizingMaskIntoConstraints = false
        self.one3.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.one3.leftAnchor.constraint(equalTo: zero3.rightAnchor,constant:1).isActive = true
        
        view.addSubview(two3)
        two3.translatesAutoresizingMaskIntoConstraints = false
        self.two3.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.two3.leftAnchor.constraint(equalTo: one3.rightAnchor,constant:1).isActive = true
        
        
        view.addSubview(three3)
        three3.translatesAutoresizingMaskIntoConstraints = false
        self.three3.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.three3.leftAnchor.constraint(equalTo: two3.rightAnchor,constant:1).isActive = true
        
        
        view.addSubview(one1)
        one1.translatesAutoresizingMaskIntoConstraints = false
        self.one1.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.one1.leftAnchor.constraint(equalTo: three3.rightAnchor,constant:1).isActive = true
        
        view.addSubview(zero1)
        zero1.translatesAutoresizingMaskIntoConstraints = false
        self.zero1.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        self.zero1.leftAnchor.constraint(equalTo: one1.rightAnchor,constant:1).isActive = true
        
        
        //end of image picker radio
        
        
        view.addSubview(updatebutton)
        updateoccupancy.translatesAutoresizingMaskIntoConstraints = false
        
        
        self.updateoccupancy.leftAnchor.constraint(equalTo: view.leftAnchor,constant:2).isActive = true
        self.updateoccupancy.topAnchor.constraint(equalTo: zero1.bottomAnchor,constant:2).isActive = true
        
        //  self.updateoccupancy.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(deleteduct)
        deleteduct.translatesAutoresizingMaskIntoConstraints = false
        
        
        self.deleteduct.rightAnchor.constraint(equalTo: view.rightAnchor,constant:-2).isActive = true
        self.deleteduct.topAnchor.constraint(equalTo: zero1.bottomAnchor,constant:2).isActive = true
        
        // self.deleteduct.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        
        
        
    }

   
    
    @IBAction func deleteduct(_ sender: Any) {
        
        
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        if(currentuser == createdby){
            
            
            
            
            deletenestduct(manhole: manholename, wallnumber: wallnumber, nestduct2: nestduct)
            
            deleteductmysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)",nestduct: nestduct)
            
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) { // change 2 to desired number of seconds
            self.delegate?.getter2()
            self.dismiss(animated: true, completion: nil)
        }
        
        
        
    }
    
    @IBAction func onsliderchange(_ sender: UISlider) {
        
        occupancy.text = String(Int(sender.value))
        utilization = Int(sender.value)
    }
    
    
    @IBAction func updateduct(_ sender: Any) {
        
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        let cablecodevalue = cablecodetext.text
        
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(manholename).child("\(wallnumber)\(ductname)").child("occupancy").setValue(occupancystr)
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("occupancy").setValue(occupancystr)
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("utilization").setValue(utilization)
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("occupancyselector").setValue(occupancyselector)
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("cablecode").setValue(cablecodevalue)
            
            updateductmysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)", occupancy: occupancystr, utilization: utilization)
            
            updatecablecodemysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)", cablecode: cablecodevalue!)
            
            
            updateselectormysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)", selector: occupancyselector)
            
        }
        
        delegate?.getter2()
        self.dismiss(animated: true, completion: nil)
        
    }
    
    
    //function to update duct in mysql server
    func updateductmysql(manholeid:String,duct:String,occupancy:String,utilization:Int) {
        let parameters = ["manholeid" : manholeid  ,
                          "occupancy" : occupancy,
                          "duct" : duct,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? "",
                          "utilization" : utilization ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/updateduct.php")! as URL)
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
    
    
    func updatecablecodemysql(manholeid:String,duct:String,cablecode:String) {
        let parameters = ["manholeid" : manholeid  ,
                          "cablecode" : cablecode,
                          "duct" : duct,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? ""
            ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/updateductcablecode.php")! as URL)
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
    
    
    func updateselectormysql(manholeid:String,duct:String,selector:String) {
        
        let parameters = ["manholeid" : manholeid  ,
                          "selector" : selector,
                          "duct" : duct,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? ""
            ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/updateductselector.php")! as URL)
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
    
    
    
    func deleteductmysql(manholeid:String,duct:String,nestduct:String) {
        let parameters = ["manholeid" : manholeid ,
                          "duct" : duct,
                          "nestduct" : nestduct,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? ""].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/deleteduct.php")! as URL)
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
    
    @IBAction func radiozero1(_ sender: DLRadioButton) {
        
        slideroccupancy.value = 0.0
        occupancy.text = "0/1"
        utilization = 0
        occupancystr = "AVAILABLE"
        occupancyselector = "01"
    }
    
    @IBAction func radioone1(_ sender: DLRadioButton) {
        
        slideroccupancy.value = 100.0
        occupancy.text = "1/1"
        
        utilization = 100
        occupancystr = "FULLY UTILIZED"
        occupancyselector = "11"
    }
    
    @IBAction func radiothree3(_ sender: DLRadioButton) {
        
        slideroccupancy.value = 100.0
        occupancy.text = "3/3"
        utilization = 100
        occupancystr = "FULLY UTILIZED"
        occupancyselector = "33"
    }
    
    @IBAction func radiotwo3(_ sender: DLRadioButton) {
        slideroccupancy.value = 67.0
        occupancy.text = "2/3"
        utilization = 67
        occupancystr = "PARTIALLY UTILIZED"
        occupancyselector = "23"
    }
    
    @IBAction func radioone3(_ sender: DLRadioButton) {
        
        slideroccupancy.value = 33.0
        occupancy.text = "1/3"
        utilization = 33
        occupancystr = "PARTIALLY UTILIZED"
        occupancyselector = "13"
    }
    
    @IBAction func radiozero3(_ sender: DLRadioButton) {
        
        slideroccupancy.value = 0.0
        
        occupancy.text = "0/3"
        utilization = 0
        occupancystr = "AVAILABLE"
        occupancyselector = "03"
    }
    
    func loadduct(manhole:String, wallnumber:String,nestduct:String,duct:String){
        
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("Nesductidutilization").child(manhole).child(nestduct).child(wallnumber+duct)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            let value = snapshot.value as? NSDictionary
            var occupancystr = value?["occupancyselector"] as? String ?? ""
            
            
            
            
            if(occupancystr == "03"){
                
                self.zero3.isSelected = true
                self.slideroccupancy.value = 0.0
                
                self.occupancy.text = "0/3"
                self.utilization = 0
                self.occupancystr = "AVAILABLE"
                self.occupancyselector = "03"
                
                
            }
            
            if(occupancystr == "13"){
                
                self.one3.isSelected = true
                
                self.slideroccupancy.value = 33.0
                self.occupancy.text = "1/3"
                self.utilization = 33
                occupancystr = "PARTIALLY UTILIZED"
                self.occupancyselector = "13"
                
                
            }
            if(occupancystr == "23"){
                
                self.two3.isSelected = true
                
                self.slideroccupancy.value = 67.0
                self.occupancy.text = "2/3"
                self.utilization = 67
                occupancystr = "PARTIALLY UTILIZED"
                self.occupancyselector = "23"
                
                
            }
            if(occupancystr == "33"){
                
                self.three3.isSelected = true
                
                self.slideroccupancy.value = 100.0
                self.occupancy.text = "3/3"
                self.utilization = 100
                occupancystr = "FULLY UTILIZED"
                self.occupancyselector = "33"
                
                
            }
            
            if(occupancystr == "11"){
                
                self.one1.isSelected = true
                
                self.slideroccupancy.value = 100.0
                self.occupancy.text = "1/1"
                
                self.utilization = 100
                occupancystr = "FULLY UTILIZED"
                self.occupancyselector = "11"
                
                
            }
            
            if(occupancystr == "01"){
                
                self.zero1.isSelected = true
                self.slideroccupancy.value = 0.0
                self.occupancy.text = "0/1"
                self.utilization = 0
                occupancystr = "AVAILABLE"
                self.occupancyselector = "01"
                
                
            }
            
            
            let cablecodeval = snapshot.value as? NSDictionary
            let cablecodedisplay = cablecodeval?["cablecode"] as? String ?? ""
            
            self.cablecodetext.text = cablecodedisplay
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
    }
    
    
    func deletenestduct(manhole:String, wallnumber:String,nestduct2:String){
        
        
        
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("Nesductidutilization").child(manhole)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            
            
            for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//Nest Duct level
                
                print("\(rest2.key) \(nestduct2) \(wallnumber)")
                
                
                if(rest2.key == nestduct2){
                    
                    for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {//duct level
                        
                        
                        
                        if(rest3.key.range(of:wallnumber) != nil){
                            
                            
                            var ref: FIRDatabaseReference!
                            
                            ref = FIRDatabase.database().reference()
                            
                            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(self.manholename).child(rest3.key).removeValue()
                            
                            ref.child("Nesductidutilization").child(self.manholename).child(self.nestduct).child(rest3.key).removeValue()
                            
                            //                         print(rest2.key)
                            //                         print(rest3.key)
                            //
                            
                            
                        }
                        
                        
                        
                        
                        
                    }
                    
                    
                }
                
                
                
                
            }
            
            
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        
        
        
        
    }
    
}
