//
//  manholedetails.swift
//  idraw
//
//  Created by Hasanul Isyraf on 05/03/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Firebase
import GoogleMaps

class manholedetails: UIViewController,UIPopoverPresentationControllerDelegate,protocolupdateoccupancy,protocoladdnewnestduct,UIImagePickerControllerDelegate ,UINavigationControllerDelegate{
    
    
    
    var wall1dict = [String : AnyObject]()
    var wallnumbername : String? = nil
    var manholename : String? = nil

    @IBOutlet weak var manholelabel: UILabel!
    
    @IBOutlet weak var walllabel: UILabel!
    @IBOutlet dynamic var H2: UILabel!
    @IBOutlet dynamic var H1: UILabel!
    @IBOutlet dynamic var H3: UILabel!
    @IBOutlet dynamic var H4: UILabel!
    @IBOutlet dynamic var H5: UILabel!
    @IBOutlet dynamic var H6: UILabel!
    @IBOutlet dynamic var H7: UILabel!
    @IBOutlet dynamic var H8: UILabel!
    @IBOutlet dynamic var G1: UILabel!
    @IBOutlet dynamic var G2: UILabel!
    @IBOutlet dynamic var G3: UILabel!
    @IBOutlet dynamic var G4: UILabel!
    @IBOutlet dynamic var G5: UILabel!
    @IBOutlet dynamic var G6: UILabel!
    @IBOutlet dynamic var G7: UILabel!
    @IBOutlet dynamic var G8: UILabel!
    @IBOutlet dynamic var F1: UILabel!
    @IBOutlet dynamic var F2: UILabel!
    @IBOutlet dynamic var F3: UILabel!
    @IBOutlet dynamic var F4: UILabel!
    @IBOutlet dynamic var F5: UILabel!
    @IBOutlet dynamic var F6: UILabel!
    @IBOutlet dynamic var F7: UILabel!
    @IBOutlet dynamic var F8: UILabel!
    @IBOutlet dynamic var E1: UILabel!
    @IBOutlet dynamic var E2: UILabel!
    @IBOutlet dynamic var E3: UILabel!
    @IBOutlet dynamic var E4: UILabel!
    @IBOutlet dynamic var E5: UILabel!
    @IBOutlet dynamic var E6: UILabel!
    @IBOutlet dynamic var E7: UILabel!
    @IBOutlet dynamic var E8: UILabel!
    @IBOutlet dynamic var D1: UILabel!
    @IBOutlet dynamic var D2: UILabel!
    @IBOutlet dynamic var D3: UILabel!
    @IBOutlet dynamic var D4: UILabel!
    @IBOutlet dynamic var D5: UILabel!
    @IBOutlet dynamic var D6: UILabel!
    @IBOutlet dynamic var D7: UILabel!
    @IBOutlet dynamic var D8: UILabel!
    @IBOutlet dynamic var C1: UILabel!
    @IBOutlet dynamic var C2: UILabel!
    @IBOutlet dynamic var C3: UILabel!
    @IBOutlet dynamic var C4: UILabel!
    @IBOutlet dynamic var C5: UILabel!
    @IBOutlet dynamic var C6: UILabel!
    @IBOutlet dynamic var C7: UILabel!
    @IBOutlet dynamic var C8: UILabel!
    @IBOutlet dynamic var B1: UILabel!
    @IBOutlet dynamic var B2: UILabel!
    @IBOutlet dynamic var B3: UILabel!
    @IBOutlet dynamic var B4: UILabel!
    @IBOutlet dynamic var B5: UILabel!
    @IBOutlet dynamic var B6: UILabel!
    @IBOutlet dynamic var B7: UILabel!
    @IBOutlet dynamic var B8: UILabel!
    @IBOutlet dynamic var A1: UILabel!
    @IBOutlet dynamic var A2: UILabel!
    @IBOutlet dynamic var A3: UILabel!
    @IBOutlet dynamic var A4: UILabel!
    @IBOutlet dynamic var A5: UILabel!
    @IBOutlet dynamic var A6: UILabel!
    @IBOutlet dynamic var A7: UILabel!
    @IBOutlet dynamic var A8: UILabel!
    
    
    @IBOutlet weak var resetbutton: UIButton!
    @IBOutlet weak var imagecapture: UIImageView!
    var btn1 = UIButton(type: .custom)
    var item1 = UIBarButtonItem()
    
    @IBOutlet weak var reloadbar: UIBarButtonItem!
    var markerdetails = GMSMarker()
    
     var ductdictionary = [String : String]()
    var nestductdictionary = [String : String]()
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
       
        if(wallnumbername != nil){
            
            manholelabel.text = manholename
            walllabel.text = wallnumbername
            
          
            setupductlabel()
            
           
            loadduct(manhole: manholename!, wallnumber: wallnumbername!)
        }
        
        
        //setup right bar button
         btn1 = UIButton(type: .custom)
        btn1.setImage(#imageLiteral(resourceName: "reloadicon"), for: .normal)
        btn1.frame = CGRect(x: 0, y: 0, width: 30, height: 30)
        btn1.addTarget(self, action: #selector(manholesummary.getter), for: .touchUpInside)
         item1 = UIBarButtonItem(customView: btn1)
          self.navigationItem.setRightBarButtonItems([item1], animated: true)
        
        
        view.addSubview(imagecapture)
        imagecapture.translatesAutoresizingMaskIntoConstraints = false
        
        self.imagecapture.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.imagecapture.topAnchor.constraint(equalTo: resetbutton.bottomAnchor,constant:2).isActive = true
        self.imagecapture.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        self.imagecapture.heightAnchor.constraint(equalToConstant: 50).isActive = true

    }
    
    
   
    func getter(){
        
        setupductlabel()
        loadduct(manhole: manholename!, wallnumber: wallnumbername!)
        ductdictionary.removeAll()
        
    }
    
    func setupductlabel(){
        
        A1.isUserInteractionEnabled = true
        let TA1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A1.addGestureRecognizer(TA1)
        A1.backgroundColor = UIColor.blue
        
        A2.isUserInteractionEnabled = true
        let TA2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A2.addGestureRecognizer(TA2)
        A2.backgroundColor = UIColor.blue
        
        A3.isUserInteractionEnabled = true
        let TA3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A3.addGestureRecognizer(TA3)
        A3.backgroundColor = UIColor.blue
        
        
        A4.isUserInteractionEnabled = true
        let TA4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A4.addGestureRecognizer(TA4)
        A4.backgroundColor = UIColor.blue
        
        A5.isUserInteractionEnabled = true
        let TA5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A5.addGestureRecognizer(TA5)
        A5.backgroundColor = UIColor.blue
        
        A6.isUserInteractionEnabled = true
        let TA6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A6.addGestureRecognizer(TA6)
        A6.backgroundColor = UIColor.blue
        
        A7.isUserInteractionEnabled = true
        let TA7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A7.addGestureRecognizer(TA7)
        A7.backgroundColor = UIColor.blue
        
        A8.isUserInteractionEnabled = true
        let TA8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        A8.addGestureRecognizer(TA8)
        A8.backgroundColor = UIColor.blue
        
        
        //b
        B1.isUserInteractionEnabled = true
        let TB1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B1.addGestureRecognizer(TB1)
        B1.backgroundColor = UIColor.blue
        
        B2.isUserInteractionEnabled = true
        let TB2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B2.addGestureRecognizer(TB2)
        B2.backgroundColor = UIColor.blue
        
        B3.isUserInteractionEnabled = true
        let TB3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B3.addGestureRecognizer(TB3)
        B3.backgroundColor = UIColor.blue
        
        B4.isUserInteractionEnabled = true
        let TB4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B4.addGestureRecognizer(TB4)
        B4.backgroundColor = UIColor.blue
        
        B5.isUserInteractionEnabled = true
        let TB5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B5.addGestureRecognizer(TB5)
        B5.backgroundColor = UIColor.blue
        
        B6.isUserInteractionEnabled = true
        let TB6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B6.addGestureRecognizer(TB6)
        B6.backgroundColor = UIColor.blue
        
        B7.isUserInteractionEnabled = true
        let TB7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B7.addGestureRecognizer(TB7)
        B7.backgroundColor = UIColor.blue
        
        B8.isUserInteractionEnabled = true
        let TB8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        B8.addGestureRecognizer(TB8)
        B8.backgroundColor = UIColor.blue
        
        //c
        
        C1.isUserInteractionEnabled = true
        let TC1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C1.addGestureRecognizer(TC1)
        C1.backgroundColor = UIColor.blue
        
        C2.isUserInteractionEnabled = true
        let TC2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C2.addGestureRecognizer(TC2)
        C2.backgroundColor = UIColor.blue
        
        C3.isUserInteractionEnabled = true
        let TC3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C3.addGestureRecognizer(TC3)
        C3.backgroundColor = UIColor.blue
        
        
        C4.isUserInteractionEnabled = true
        let TC4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C4.addGestureRecognizer(TC4)
        C4.backgroundColor = UIColor.blue
        
        C5.isUserInteractionEnabled = true
        let TC5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C5.addGestureRecognizer(TC5)
        C5.backgroundColor = UIColor.blue
        
        C6.isUserInteractionEnabled = true
        let TC6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C6.addGestureRecognizer(TC6)
        C6.backgroundColor = UIColor.blue
        
        C7.isUserInteractionEnabled = true
        let TC7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C7.addGestureRecognizer(TC7)
        C7.backgroundColor = UIColor.blue
        
        C8.isUserInteractionEnabled = true
        let TC8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        C8.addGestureRecognizer(TC8)
        C8.backgroundColor = UIColor.blue
        
        
        
        //d
        D1.isUserInteractionEnabled = true
        let TD1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D1.addGestureRecognizer(TD1)
        D1.backgroundColor = UIColor.blue
        
        D2.isUserInteractionEnabled = true
        let TD2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D2.addGestureRecognizer(TD2)
        D2.backgroundColor = UIColor.blue
        
        D3.isUserInteractionEnabled = true
        let TD3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D3.addGestureRecognizer(TD3)
        D3.backgroundColor = UIColor.blue
        
        
        D4.isUserInteractionEnabled = true
        let TD4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D4.addGestureRecognizer(TD4)
        D4.backgroundColor = UIColor.blue
        
        D5.isUserInteractionEnabled = true
        let TD5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D5.addGestureRecognizer(TD5)
        D5.backgroundColor = UIColor.blue
        
        D6.isUserInteractionEnabled = true
        let TD6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D6.addGestureRecognizer(TD6)
        D6.backgroundColor = UIColor.blue
        
        D7.isUserInteractionEnabled = true
        let TD7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D7.addGestureRecognizer(TD7)
        D7.backgroundColor = UIColor.blue
        
        D8.isUserInteractionEnabled = true
        let TD8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        D8.addGestureRecognizer(TD8)
        D8.backgroundColor = UIColor.blue
        
        //e
        
        E1.isUserInteractionEnabled = true
        let TE1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E1.addGestureRecognizer(TE1)
        E1.backgroundColor = UIColor.blue
        
        E2.isUserInteractionEnabled = true
        let TE2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E2.addGestureRecognizer(TE2)
        E2.backgroundColor = UIColor.blue
        
        E3.isUserInteractionEnabled = true
        let TE3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E3.addGestureRecognizer(TE3)
        E3.backgroundColor = UIColor.blue
        
        
        E4.isUserInteractionEnabled = true
        let TE4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E4.addGestureRecognizer(TE4)
        E4.backgroundColor = UIColor.blue
        
        E5.isUserInteractionEnabled = true
        let TE5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E5.addGestureRecognizer(TE5)
        E5.backgroundColor = UIColor.blue
        
        E6.isUserInteractionEnabled = true
        let TE6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E6.addGestureRecognizer(TE6)
        E6.backgroundColor = UIColor.blue
        
        E7.isUserInteractionEnabled = true
        let TE7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E7.addGestureRecognizer(TE7)
        E7.backgroundColor = UIColor.blue
        
        E8.isUserInteractionEnabled = true
        let TE8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        E8.addGestureRecognizer(TE8)
        E8.backgroundColor = UIColor.blue
        
        
        //f
        
        F1.isUserInteractionEnabled = true
        let TF1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F1.addGestureRecognizer(TF1)
        F1.backgroundColor = UIColor.blue
        
        F2.isUserInteractionEnabled = true
        let TF2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F2.addGestureRecognizer(TF2)
        F2.backgroundColor = UIColor.blue
        
        F3.isUserInteractionEnabled = true
        let TF3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F3.addGestureRecognizer(TF3)
        F3.backgroundColor = UIColor.blue
        
        
        F4.isUserInteractionEnabled = true
        let TF4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F4.addGestureRecognizer(TF4)
        F4.backgroundColor = UIColor.blue
        
        F5.isUserInteractionEnabled = true
        let TF5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F5.addGestureRecognizer(TF5)
        F5.backgroundColor = UIColor.blue
        
        F6.isUserInteractionEnabled = true
        let TF6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F6.addGestureRecognizer(TF6)
        F6.backgroundColor = UIColor.blue
        
        F7.isUserInteractionEnabled = true
        let TF7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F7.addGestureRecognizer(TF7)
        F7.backgroundColor = UIColor.blue
        
        F8.isUserInteractionEnabled = true
        let TF8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        F8.addGestureRecognizer(TF8)
        F8.backgroundColor = UIColor.blue
        
        
        //g
        
        G1.isUserInteractionEnabled = true
        let TG1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G1.addGestureRecognizer(TG1)
        G1.backgroundColor = UIColor.blue
        
        G2.isUserInteractionEnabled = true
        let TG2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G2.addGestureRecognizer(TG2)
        G2.backgroundColor = UIColor.blue
        
        G3.isUserInteractionEnabled = true
        let TG3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G3.addGestureRecognizer(TG3)
        G3.backgroundColor = UIColor.blue
        
        
        G4.isUserInteractionEnabled = true
        let TG4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G4.addGestureRecognizer(TG4)
        G4.backgroundColor = UIColor.blue
        
        G5.isUserInteractionEnabled = true
        let TG5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G5.addGestureRecognizer(TG5)
        G5.backgroundColor = UIColor.blue
        
        G6.isUserInteractionEnabled = true
        let TG6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G6.addGestureRecognizer(TG6)
        G6.backgroundColor = UIColor.blue
        
        G7.isUserInteractionEnabled = true
        let TG7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G7.addGestureRecognizer(TG7)
        G7.backgroundColor = UIColor.blue
        
        G8.isUserInteractionEnabled = true
        let TG8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        G8.addGestureRecognizer(TG8)
        G8.backgroundColor = UIColor.blue
        
        
        //h
        
        H1.isUserInteractionEnabled = true
        let TH1 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H1.addGestureRecognizer(TH1)
        H1.backgroundColor = UIColor.blue
        
        H2.isUserInteractionEnabled = true
        let TH2 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H2.addGestureRecognizer(TH2)
        H2.backgroundColor = UIColor.blue
        
        H3.isUserInteractionEnabled = true
        let TH3 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H3.addGestureRecognizer(TH3)
        H3.backgroundColor = UIColor.blue
        
        
        H4.isUserInteractionEnabled = true
        let TH4 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H4.addGestureRecognizer(TH4)
        H4.backgroundColor = UIColor.blue
        
        H5.isUserInteractionEnabled = true
        let TH5 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H5.addGestureRecognizer(TH5)
        H5.backgroundColor = UIColor.blue
        
        H6.isUserInteractionEnabled = true
        let TH6 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H6.addGestureRecognizer(TH6)
        H6.backgroundColor = UIColor.blue
        
        H7.isUserInteractionEnabled = true
        let TH7 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H7.addGestureRecognizer(TH7)
        H7.backgroundColor = UIColor.blue
        
        H8.isUserInteractionEnabled = true
        let TH8 = UITapGestureRecognizer(target: self, action: #selector(manholedetails.tapduct(sender:)))
        H8.addGestureRecognizer(TH8)
        H8.backgroundColor = UIColor.blue
        
    }
    
    
    func tapduct(sender:UITapGestureRecognizer){
        
        
        if let theLabel = (sender.view as? UILabel)?.text {
            print(theLabel)
        }
        
        
        if let color = (sender.view as? UILabel)?.backgroundColor {
          
            
            if(color == UIColor.red || color == UIColor.yellow || color == UIColor.green || color == UIColor.black){
              
                // get a reference to the view controller for the popover
                let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popoveroccupancy") as! popoveroccupancy
                
                
                // set the presentation style...this to make popover not cover all the area
                popController.modalPresentationStyle = UIModalPresentationStyle.popover
                
                
                // set up the popover presentation controller
              
                popController.popoverPresentationController?.delegate = self
                
             

                popController.popoverPresentationController?.barButtonItem = item1
                
                popController.delegate = self
                
                //seth the manhole name and wall number
                popController.marker = markerdetails
                popController.wallnumber = wallnumbername!
              
                // set the duct name
                if let theLabel = (sender.view as? UILabel)?.text {
                    
                    popController.ductname = theLabel
                    popController.nestduct = nestductdictionary[theLabel]!
                }
                //set the utilization
                if let utilization = (sender.view as? UILabel)?.tag {
                popController.utilization = utilization
                }
                
                //sett the occupancy
                if(color == UIColor.red){
                    
                    popController.occupancystr = "FULLY UTILIZED"
                }
                
                if(color == UIColor.green){
                    
                    popController.occupancystr = "AVAILABLE"
                }
                
                if(color == UIColor.yellow){
                    
                    popController.occupancystr = "PARTIALLY UTILIZED"
                }
                
                if(color == UIColor.black){
                    
                    popController.occupancystr = "ABANDON"
                }
                
                // present the popover
                self.present(popController, animated: true, completion: nil)
                
            }
           
            if(color == UIColor.white){
                sender.view?.backgroundColor = UIColor.blue
                if let theLabel = (sender.view as? UILabel)?.text {
                    print(theLabel)
                    
                    ductdictionary.removeValue(forKey: "\(wallnumbername ?? "")\(theLabel)")
                }
                
            }
            
            if(color == UIColor.blue){
                sender.view?.backgroundColor = UIColor.white
                
                if let theLabel = (sender.view as? UILabel)?.text {
                    print(theLabel)
                    
                    ductdictionary["\(wallnumbername ?? "")\(theLabel)"] = "AVAILABLE"
                }
                
            }
         
            
        }
        
        
    }
    
    func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
        return .none
    }
    
    func loadduct(manhole:String, wallnumber:String){
        
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("Nesductidutilization").child(manhole)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            var nestduct:String = ""
            
            for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//Nest Duct level
                
                nestduct = rest2.key
                
                for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {//duct level
                    
                    
                    
                    if(rest3.key.range(of:wallnumber) != nil){
                        
                    
                       
                        
                        let wallduct = rest3.key
                        let duct  = wallduct.replacingOccurrences(of: wallnumber, with: "")
                       
                        
                        //save the wallduct and nestduct name
                        self.nestductdictionary[duct] = nestduct

                        if let occupancy = rest3.childSnapshot(forPath: "occupancy").value as? String{
                            print("\(occupancy) \n")
                            
                            if(occupancy == "AVAILABLE"){
                            let outletName = duct
                            // Then you can access it via a key path (aka string)
                            if let myProperty = self.value(forKey: outletName) as? UILabel {
                                myProperty.backgroundColor = UIColor.green
                            }
                                
                            
                                
                            }
                            
                            
                            if(occupancy == "FULLY UTILIZED"){
                                let outletName = duct
                                // Then you can access it via a key path (aka string)
                                if let myProperty = self.value(forKey: outletName) as? UILabel {
                                    myProperty.backgroundColor = UIColor.red
                                }
                            }
                            
                            if(occupancy == "PARTIALLY UTILIZED"){
                                let outletName = duct
                                // Then you can access it via a key path (aka string)
                                if let myProperty = self.value(forKey: outletName) as? UILabel {
                                    myProperty.backgroundColor = UIColor.yellow
                                }
                            }
                            
                            
                            if(occupancy == "ABANDONED"){
                                let outletName = duct
                                // Then you can access it via a key path (aka string)
                                if let myProperty = self.value(forKey: outletName) as? UILabel {
                                    myProperty.backgroundColor = UIColor.black
                                }
                            }
                        }
                        
                        //tage the utilization value to the label
                        if let utilization = rest3.childSnapshot(forPath: "utilization").value as? Int{
                             let outletName = duct
                            if let myProperty = self.value(forKey: outletName) as? UILabel {
                                myProperty.tag = utilization
                                
                            }
                            
                        }
                        
                       
                        
                    }
                    
                   
                    
                    
                    
                    
                }
                
                
                
                
            }
            
          
            
           
          
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
    }
    
    
    @IBAction func addnewductnest(_ sender: Any) {
        
        if(ductdictionary.count>0){
        
        // get a reference to the view controller for the popover
        let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popovernewductnest") as! popovernewductnest
        
        
        // set the presentation style...this to make popover not cover all the area
        popController.modalPresentationStyle = UIModalPresentationStyle.popover
        
        
        // set up the popover presentation controller
        
        popController.popoverPresentationController?.delegate = self
        
        
        
        popController.popoverPresentationController?.barButtonItem = item1
        
        popController.delegate = self
        
        //seth the manhole name and wall number
        popController.marker = markerdetails
        popController.ductdictionary1 = ductdictionary
      
        
        // present the popover
        self.present(popController, animated: true, completion: nil)
       
        }
    }
    
   
    @IBAction func resetinput(_ sender: Any) {
        getter()
    }
    
    
    @IBAction func selectimage(_ sender: Any) {
        
        let createdby = "\(markerdetails.userData ?? "")"
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        if(currentuser == createdby){
        
        let imagepickercontroller = UIImagePickerController()
          imagepickercontroller.delegate = self
        
        imagepickercontroller.sourceType = .photoLibrary
        self.present(imagepickercontroller,animated: true,completion: nil)
        
        }
    }
    
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image2 = info[UIImagePickerControllerOriginalImage] as! UIImage
        
        
        imagecapture.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
        imagecapture?.contentMode = UIViewContentMode.scaleAspectFit
        
        imagecapture.image = resizeToScreenSize(image: image2)
        
        picker.dismiss(animated: true, completion: nil)
       
        
        
        // upload image to firebase
        
        var imagename : String = ""
        
        if(wallnumbername == "W1"){
            
              imagename = "\(manholename ?? "testimage")_wall1"
            
        }
        
        if(wallnumbername == "W2"){
            
             imagename = "\(manholename ?? "testimage")_wall2"
            
        }
        
        if(wallnumbername == "W3"){
            
             imagename = "\(manholename ?? "testimage")_wall3"
            
        }
        
        if(wallnumbername == "W4"){
            
             imagename = "\(manholename ?? "testimage")_wall4"
            
        }
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        
       
        
     
            var data = NSData()
            data = UIImageJPEGRepresentation(imagecapture.image!, 0.1)! as NSData
            let metaData = FIRStorageMetadata()
            metaData.contentType = "image/jpg"
     
            // Create a reference to the file you want to upload
            let riversRef = storageRef.child("DCIM/\(imagename).jpg")
            
        // Upload the file to the path "images/rivers.jpg"
        _ = riversRef.put(data as Data, metadata: metaData) { (metadata, error) in
            guard let metadata = metadata else {
                // Uh-oh, an error occurred!
                return
            }
            // Metadata contains file metadata such as size, content-type, and download URL.
            _ = metadata.downloadURL
        
                
            
                
            }
        
        
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    func resizeImage(image: UIImage, newWidth: CGFloat) -> UIImage {
        
        let scale = newWidth / image.size.width
        let newHeight = image.size.height * scale
        
        UIGraphicsBeginImageContext(CGSize(width: newWidth, height: newHeight))
        
        
        image.draw(in: CGRect(x: 0, y: 0,width: newWidth, height: newHeight))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
    
    func resizeToScreenSize(image: UIImage)->UIImage{
        
        let screenSize = self.view.bounds.size
        
        
        return resizeImage(image: image, newWidth: screenSize.width)
    }
        
    
    
    
}
