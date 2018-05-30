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
  
  
    func getter()
   
}
class popoveroccupancy: UIViewController,UIPickerViewDelegate,UIPickerViewDataSource {
  
    @IBOutlet weak var occupancylabel: UILabel!
    @IBOutlet weak var occupancy: UILabel!
    @IBOutlet weak var slideroccupancy: UISlider!
    
    @IBOutlet weak var deleteduct: UIButton!
    @IBOutlet weak var updateoccupancy: UIButton!
    @IBOutlet weak var pickerview: UIPickerView!
    @IBOutlet weak var cablecodetext: UITextField!
    var updatebutton = UIButton()
    
    var delegate:protocolupdateoccupancy? = nil
    let statusoccupancy = ["AVAILABLE","PARTIALLY UTILIZED","FULLY UTILIZED","ABANDONED"]
    
    var occupancystr = "AVAILABLE"
    var ductname = ""
    var utilization = 0
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
        
        self.occupancylabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.occupancylabel.bottomAnchor.constraint(equalTo: slideroccupancy.topAnchor,constant:2).isActive = true
      
        self.occupancylabel.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
       
        
        view.addSubview(occupancy)
        occupancy.translatesAutoresizingMaskIntoConstraints = false
        
        self.occupancy.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.occupancy.bottomAnchor.constraint(equalTo: occupancylabel.topAnchor,constant:2).isActive = true
        
        self.occupancy.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        occupancy.text = String(utilization)
        
        
        view.addSubview(cablecodetext)
        cablecodetext.translatesAutoresizingMaskIntoConstraints = false
        
        self.cablecodetext.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.cablecodetext.topAnchor.constraint(equalTo: slideroccupancy.bottomAnchor,constant:2).isActive = true
        
        self.cablecodetext.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(pickerview)
        pickerview.translatesAutoresizingMaskIntoConstraints = false
        
     
        self.pickerview.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.pickerview.topAnchor.constraint(equalTo: cablecodetext.bottomAnchor,constant:2).isActive = true
        
        self.pickerview.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(updatebutton)
        updateoccupancy.translatesAutoresizingMaskIntoConstraints = false
        
       
        self.updateoccupancy.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.updateoccupancy.topAnchor.constraint(equalTo: pickerview.bottomAnchor,constant:2).isActive = true
        
        self.updateoccupancy.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(deleteduct)
        deleteduct.translatesAutoresizingMaskIntoConstraints = false
        
        
        self.deleteduct.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.deleteduct.topAnchor.constraint(equalTo: updateoccupancy.bottomAnchor,constant:2).isActive = true
        
        self.deleteduct.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        //set the uipicker view inital position
        
        if let i = statusoccupancy.index(of: occupancystr) {
            print("\(occupancystr) is at index \(i)")
            
            pickerview.selectRow(i, inComponent: 0, animated: true)
        } else {
            print("\(occupancystr) isn't in the array")
        }
        
    }

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return statusoccupancy.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return statusoccupancy[row]
    }
    
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        // print(group)
        let ne = statusoccupancy[row]
        occupancystr = ne
        print(occupancystr)
        
    }
    
    @IBAction func deleteduct(_ sender: Any) {
        
        
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
       
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(manholename).child("\(wallnumber)\(ductname)").removeValue()
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").removeValue()
            
            deleteductmysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)")
            
        }
        
        delegate?.getter()
        self.dismiss(animated: true, completion: nil)
        
        
    }
    
    @IBAction func onsliderchange(_ sender: UISlider) {
        
        occupancy.text = String(Int(sender.value))
        utilization = Int(sender.value)
    }
    
    
    @IBAction func updateduct(_ sender: Any) {
        
        
        let manholename = marker.title!
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        
        if(currentuser == createdby){
            
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            
            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(manholename).child("\(wallnumber)\(ductname)").child("occupancy").setValue(occupancystr)
            
            ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("occupancy").setValue(occupancystr)
            
              ref.child("Nesductidutilization").child(manholename).child(nestduct).child("\(wallnumber)\(ductname)").child("utilization").setValue(utilization)
            
            updateductmysql(manholeid: manholename, duct: "\(wallnumber)\(ductname)", occupancy: occupancystr, utilization: utilization)
            
        }
        
        delegate?.getter()
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
    
    
    func deleteductmysql(manholeid:String,duct:String) {
        let parameters = ["manholeid" : manholeid ,
                          "duct" : duct,
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
    
}
