//
//  manholesummary.swift
//  idraw
//
//  Created by Hasanul Isyraf on 06/03/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import Firebase
import FirebaseStorageUI

class manholesummary: UIViewController {
    
    @IBOutlet weak var wall1btn: UIButton!
    
    @IBOutlet weak var wall2btn: UIButton!
    @IBOutlet weak var label2: UILabel!
    
    @IBOutlet weak var wall4btn: UIButton!
    @IBOutlet weak var wall3btn: UIButton!
    @IBOutlet weak var label5: UILabel!
    @IBOutlet weak var label4: UILabel!
    @IBOutlet weak var label3: UILabel!
    @IBOutlet weak var scrollview: UIScrollView!
    
    @IBOutlet weak var imagewall4: UIImageView!
    
    @IBOutlet weak var imagewall1: UIImageView!
    
    @IBOutlet weak var imagewall2: UIImageView!
    
    @IBOutlet weak var imagewall3: UIImageView!
    var marker = GMSMarker()
    let labelutilization1 = UILabel()
    let labelutilization2 = UILabel()
    let labelutilization3 = UILabel()
    let labelutilization4 = UILabel()
    let labelutilizationall = UILabel()
    
    
    let labelutilization1value = UILabel()
    let labelutilization2value = UILabel()
    let labelutilization3value = UILabel()
    let labelutilization4value = UILabel()
    let labelutilizationallvalue = UILabel()
    
    
    var wall1dict = [String : AnyObject]()
    var wall2dict = [String : AnyObject]()
    var wall3dict = [String : AnyObject]()
    var wall4dict = [String : AnyObject]()
    
    let manholeidlabel = UILabel()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
          let manholenamestr:String = marker.title!
        
        scrollview.addSubview(manholeidlabel)
        
        manholeidlabel.translatesAutoresizingMaskIntoConstraints = false
        
        self.manholeidlabel.topAnchor.constraint(equalTo: scrollview.topAnchor, constant: 10).isActive = true
        self.manholeidlabel.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 12).isActive = true
        self.manholeidlabel.widthAnchor.constraint(equalTo:view.widthAnchor,constant:-12).isActive = true
        
        
        self.manholeidlabel.font  = UIFont.italicSystemFont(ofSize: 15)
        self.manholeidlabel.textColor = UIColor.black
        self.manholeidlabel.textAlignment = NSTextAlignment.center
       
        manholeidlabel.text = manholenamestr
        
        
        
        scrollview.addSubview(labelutilization1)
       
        labelutilization1.translatesAutoresizingMaskIntoConstraints = false

        self.labelutilization1.topAnchor.constraint(equalTo: manholeidlabel.topAnchor, constant: 20).isActive = true
        self.labelutilization1.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 12).isActive = true
        self.labelutilization1.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        
        self.labelutilization1.font  = UIFont.italicSystemFont(ofSize: 10)
        self.labelutilization1.textColor = UIColor.black
        self.labelutilization1.textAlignment = NSTextAlignment.center
        self.labelutilization1.backgroundColor = UIColor.green
        labelutilization1.text = "Wall 1"
        
        scrollview.addSubview(labelutilization1value)
        
        labelutilization1value.translatesAutoresizingMaskIntoConstraints = false
        self.labelutilization1value.topAnchor.constraint(equalTo: labelutilization1.bottomAnchor, constant: 2).isActive = true
        self.labelutilization1value.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 12).isActive = true
        self.labelutilization1value.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        self.labelutilization1value.font  = UIFont.italicSystemFont(ofSize: 20)
        self.labelutilization1value.textColor = UIColor.white
        self.labelutilization1value.textAlignment = NSTextAlignment.center
        self.labelutilization1value.backgroundColor = UIColor.green
        labelutilization1value.text = "na"
     
        //wall 2
        
        scrollview.addSubview(labelutilization2)
        labelutilization2.translatesAutoresizingMaskIntoConstraints = false
        
        self.labelutilization2.topAnchor.constraint(equalTo: manholeidlabel.topAnchor, constant: 20).isActive = true
        self.labelutilization2.leftAnchor.constraint(equalTo: labelutilization1.rightAnchor, constant: 12).isActive = true
        self.labelutilization2.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        self.labelutilization2.font  = UIFont.italicSystemFont(ofSize: 10)
        self.labelutilization2.textColor = UIColor.black
        self.labelutilization2.textAlignment = NSTextAlignment.center
        self.labelutilization2.backgroundColor = UIColor.green
        labelutilization2.text = "Wall 2"
        
         scrollview.addSubview(labelutilization2value)
        labelutilization2value.translatesAutoresizingMaskIntoConstraints = false
        self.labelutilization2value.topAnchor.constraint(equalTo: labelutilization2.bottomAnchor, constant: 2).isActive = true
        self.labelutilization2value.leftAnchor.constraint(equalTo: labelutilization1value.rightAnchor, constant: 12).isActive = true
        self.labelutilization2value.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        
        self.labelutilization2value.font  = UIFont.italicSystemFont(ofSize: 20)
        self.labelutilization2value.textColor = UIColor.white
        self.labelutilization2value.textAlignment = NSTextAlignment.center
        self.labelutilization2value.backgroundColor = UIColor.green
        labelutilization2value.text = "na"
        
        
        //wall 3 label
        
        scrollview.addSubview(labelutilization3)
        labelutilization3.translatesAutoresizingMaskIntoConstraints = false
        
         self.labelutilization3.topAnchor.constraint(equalTo: manholeidlabel.topAnchor, constant: 20).isActive = true
        self.labelutilization3.leftAnchor.constraint(equalTo: labelutilization2.rightAnchor, constant: 12).isActive = true
        self.labelutilization3.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        self.labelutilization3.font  = UIFont.italicSystemFont(ofSize: 10)
        self.labelutilization3.textColor = UIColor.black
        self.labelutilization3.textAlignment = NSTextAlignment.center
        self.labelutilization3.backgroundColor = UIColor.green
        labelutilization3.text = "Wall 3"
        
        
        scrollview.addSubview(labelutilization3value)
        labelutilization3value.translatesAutoresizingMaskIntoConstraints = false
        self.labelutilization3value.topAnchor.constraint(equalTo: labelutilization3.bottomAnchor, constant: 2).isActive = true
        self.labelutilization3value.leftAnchor.constraint(equalTo: labelutilization2value.rightAnchor, constant: 12).isActive = true
        self.labelutilization3value.widthAnchor.constraint(equalToConstant: 50).isActive = true
        
        
        self.labelutilization3value.font  = UIFont.italicSystemFont(ofSize: 20)
        self.labelutilization3value.textColor = UIColor.white
        self.labelutilization3value.textAlignment = NSTextAlignment.center
        self.labelutilization3value.backgroundColor = UIColor.green
        labelutilization3value.text = "na"
        
        
     //wall 4 label
        scrollview.addSubview(labelutilization4)
        labelutilization4.translatesAutoresizingMaskIntoConstraints = false
        
         self.labelutilization4.topAnchor.constraint(equalTo: manholeidlabel.topAnchor, constant: 20).isActive = true
        self.labelutilization4.leftAnchor.constraint(equalTo: labelutilization3.rightAnchor, constant: 12).isActive = true
        self.labelutilization4.widthAnchor.constraint(equalToConstant: 50).isActive = true
       
        self.labelutilization4.font  = UIFont.italicSystemFont(ofSize: 10)
        self.labelutilization4.textColor = UIColor.black
        self.labelutilization4.textAlignment = NSTextAlignment.center
        self.labelutilization4.backgroundColor = UIColor.green
        labelutilization4.text = "Wall 4"
        
        
        scrollview.addSubview(labelutilization4value)
        labelutilization4value.translatesAutoresizingMaskIntoConstraints = false
        
     
        self.labelutilization4value.topAnchor.constraint(equalTo: labelutilization4.bottomAnchor, constant: 2).isActive = true
        self.labelutilization4value.leftAnchor.constraint(equalTo: labelutilization3value.rightAnchor, constant: 12).isActive = true
        self.labelutilization4value.widthAnchor.constraint(equalToConstant: 50).isActive = true
       
        self.labelutilization4value.font  = UIFont.italicSystemFont(ofSize: 20)
        self.labelutilization4value.textColor = UIColor.white
        self.labelutilization4value.textAlignment = NSTextAlignment.center
        self.labelutilization4value.backgroundColor = UIColor.green
        labelutilization4value.text = "na"
        
   //overall label
        
        scrollview.addSubview(labelutilizationallvalue)
        labelutilizationallvalue.translatesAutoresizingMaskIntoConstraints = false
      
        self.labelutilizationallvalue.topAnchor.constraint(equalTo: manholeidlabel.topAnchor, constant: 20).isActive = true
        self.labelutilizationallvalue.leftAnchor.constraint(equalTo: labelutilization4.rightAnchor, constant: 12).isActive = true
        self.labelutilizationallvalue.widthAnchor.constraint(equalToConstant: 50).isActive = true
        self.labelutilizationallvalue.heightAnchor.constraint(equalToConstant: 50).isActive = true
       
        self.labelutilizationallvalue.font  = UIFont.italicSystemFont(ofSize: 20)
         self.labelutilizationallvalue.font  = UIFont.boldSystemFont(ofSize: 20)
        self.labelutilizationallvalue.textColor = UIColor.white
        self.labelutilizationallvalue.textAlignment = NSTextAlignment.center
        
        self.labelutilizationallvalue.backgroundColor = UIColor.green
        
        labelutilizationallvalue.text = "0"
        
        
        let btn1 = UIButton(type: .custom)
        btn1.setImage(#imageLiteral(resourceName: "reloadicon"), for: .normal)
        btn1.frame = CGRect(x: 0, y: 0, width: 30, height: 30)
        btn1.addTarget(self, action: #selector(manholesummary.getter), for: .touchUpInside)
        let item1 = UIBarButtonItem(customView: btn1)
        
        self.navigationItem.setRightBarButtonItems([item1], animated: true)
        
      
       // label.text = manholenamestr
        print(manholenamestr)
        loadsummary(manhole: manholenamestr)
        loadwallimage(imagewall: imagewall1, wall: "wall1")
          loadwallimage(imagewall: imagewall2, wall: "wall2")
          loadwallimage(imagewall: imagewall3, wall: "wall3")
          loadwallimage(imagewall: imagewall4, wall: "wall4")
       
        
        
        //setup wall button
       
       
        scrollview.addSubview(wall1btn)
        wall1btn.translatesAutoresizingMaskIntoConstraints = false
        self.wall1btn.topAnchor.constraint(equalTo: label2.bottomAnchor, constant: 20).isActive = true
          self.wall1btn.leftAnchor.constraint(equalTo: imagewall1.rightAnchor, constant: 12).isActive = true
        
      
       
        scrollview.addSubview(wall2btn)
        wall2btn.translatesAutoresizingMaskIntoConstraints = false
        self.wall2btn.topAnchor.constraint(equalTo: label3.bottomAnchor, constant: 20).isActive = true
        self.wall2btn.leftAnchor.constraint(equalTo: imagewall2.rightAnchor, constant: 12).isActive = true
        
        
        
        scrollview.addSubview(wall3btn)
        wall3btn.translatesAutoresizingMaskIntoConstraints = false
        self.wall3btn.topAnchor.constraint(equalTo: label4.bottomAnchor, constant: 20).isActive = true
        self.wall3btn.leftAnchor.constraint(equalTo: imagewall3.rightAnchor, constant: 12).isActive = true
        
        
        scrollview.addSubview(wall4btn)
        wall4btn.translatesAutoresizingMaskIntoConstraints = false
        self.wall4btn.topAnchor.constraint(equalTo: label5.bottomAnchor, constant: 20).isActive = true
        self.wall4btn.leftAnchor.constraint(equalTo: imagewall4.rightAnchor, constant: 12).isActive = true
        
    }
    
    func getter(){
        
        loadsummary(manhole: marker.title!)
        loadwallimage(imagewall: imagewall1, wall: "wall1")
        loadwallimage(imagewall: imagewall2, wall: "wall2")
        loadwallimage(imagewall: imagewall3, wall: "wall3")
        loadwallimage(imagewall: imagewall4, wall: "wall4")
        
    }

    func loadsummary(manhole:String){
        
        // initally load photo marker once
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("Nesductidutilization").child(manhole)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
        
            var nestduct_main : String = ""
            var nestduct_main2 : String = ""
            var nestduct_main3 : String = ""
            var nestduct_main4 : String = ""
         
            var countwall1 = 0
            var utilizationwall1 = 0
            
            
            var countwall2 = 0
            var utilizationwall2 = 0
            
            
            var countwall3 = 0
            var utilizationwall3 = 0
            
            var countwall4 = 0
            var utilizationwall4 = 0
            
            var countall = 0
            var utilizationall = 0
           
            
                for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//Nest Duct level
                    
                    let nestduct : String = rest2.key
                   
                    
            
                    
                    for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {
                        
                        
                        
                       
                        if(rest3.key.range(of:"W1") != nil){
                            
                            countwall1 = countwall1 + 1
                             countall = countall + 1
                            if let utilization  = rest3.childSnapshot(forPath: "utilization").value as? Int{
                               
                               
                                utilizationwall1 = utilizationwall1 + utilization
                                utilizationall = utilizationall + utilization
                                
                                
                            }
                          
                            
                            if(nestduct_main.range(of: nestduct) == nil){
                               // print(nestduct)
                                nestduct_main = "\(nestduct_main) \(nestduct)  \n"
                                
                            }
                            
                            let duct = rest3.key
                            
                            nestduct_main = "\(nestduct_main)  \(duct) \(rest3.childSnapshot(forPath: "occupancy").value ?? "") \(rest3.childSnapshot(forPath: "utilization").value ?? "")\n"
                            
                            self.wall1dict[duct] = rest3.childSnapshot(forPath: "occupancy").value as AnyObject
                            
                        }
                        
                        if(rest3.key.range(of:"W2") != nil){
                            
                            countwall2 = countwall2 + 1
                            countall = countall + 1
                            if let utilization  = rest3.childSnapshot(forPath: "utilization").value as? Int{
                                
                                
                                utilizationwall2 = utilizationwall2 + utilization
                                utilizationall = utilizationall + utilization

                                
                            }
                            
                            if(nestduct_main2.range(of: nestduct) == nil){
                                // print(nestduct)
                                nestduct_main2 = "\(nestduct_main2) \(nestduct)  \n"
                                
                            }
                            
                            let duct = rest3.key
                            
                            nestduct_main2 = "\(nestduct_main2)  \(duct) \(rest3.childSnapshot(forPath: "occupancy").value ?? "") \(rest3.childSnapshot(forPath: "utilization").value ?? "")\n"
                            
                             self.wall2dict[duct] = rest3.childSnapshot(forPath: "occupancy").value as AnyObject
                        }
                        
                        if(rest3.key.range(of:"W3") != nil){
                            
                            
                            countwall3 = countwall3 + 1
                            countall = countall + 1
                            if let utilization  = rest3.childSnapshot(forPath: "utilization").value as? Int{
                                
                                
                                utilizationwall3 = utilizationwall3 + utilization
                                utilizationall = utilizationall + utilization

                                
                            }
                            
                            if(nestduct_main3.range(of: nestduct) == nil){
                                // print(nestduct)
                                nestduct_main3 = "\(nestduct_main3) \(nestduct)  \n"
                                
                            }
                            
                            let duct = rest3.key
                            
                            nestduct_main3 = "\(nestduct_main3)  \(duct) \(rest3.childSnapshot(forPath: "occupancy").value ?? "") \(rest3.childSnapshot(forPath: "utilization").value ?? "")\n"
                             self.wall3dict[duct] = rest3.childSnapshot(forPath: "occupancy").value as AnyObject
                            
                        }
                        
                        if(rest3.key.range(of:"W4") != nil){
                            
                            countwall4 = countwall4 + 1
                            countall = countall + 1
                            if let utilization  = rest3.childSnapshot(forPath: "utilization").value as? Int{
                                
                                
                                utilizationwall4 = utilizationwall4 + utilization
                                utilizationall = utilizationall + utilization

                                
                            }
                            
                            if(nestduct_main4.range(of: nestduct) == nil){
                                // print(nestduct)
                                nestduct_main4 = "\(nestduct_main4) \(nestduct)  \n"
                                
                            }
                            
                            let duct = rest3.key
                            
                            nestduct_main4 = "\(nestduct_main4)  \(duct) \(rest3.childSnapshot(forPath: "occupancy").value ?? "") \(rest3.childSnapshot(forPath: "utilization").value ?? "")\n"
                            
                            self.wall4dict[duct] = rest3.childSnapshot(forPath: "occupancy").value as AnyObject
                            
                        }

                        
                      
                        
                    }
               
               
               
                
            }
            
             print(nestduct_main)
            
            self.label2.text = "Wall 1\n\(nestduct_main)\n"
            self.label2.numberOfLines = 0
            self.label2.lineBreakMode = .byWordWrapping
            self.label2.sizeToFit()
            self.label2.font  = UIFont.italicSystemFont(ofSize: 10)
            
            self.label2.translatesAutoresizingMaskIntoConstraints = false
            self.label2.topAnchor.constraint(equalTo: self.labelutilizationallvalue.topAnchor, constant: 100).isActive = true
         
            
            self.label3.text = "Wall 2 \n\(nestduct_main2)\n "
            self.label3.numberOfLines = 0
            self.label3.lineBreakMode = .byWordWrapping
            self.label3.sizeToFit()
             self.label3.font  = UIFont.italicSystemFont(ofSize: 10)
           
            self.label4.text = "Wall 3 \n\(nestduct_main3)\n "
            self.label4.numberOfLines = 0
            self.label4.lineBreakMode = .byWordWrapping
            self.label4.sizeToFit()
             self.label4.font  = UIFont.italicSystemFont(ofSize: 10)
            
            self.label5.text = "Wall 4 \n\(nestduct_main4)\n "
            self.label5.numberOfLines = 0
            self.label5.lineBreakMode = .byWordWrapping
            self.label5.sizeToFit()
             self.label5.font  = UIFont.italicSystemFont(ofSize: 10)
           
            var wall1utilization = 0
            if(countwall1>0){
             wall1utilization = utilizationwall1/countwall1
            
           self.labelutilization1value.text = "\(wall1utilization)"
            }
            
            var wall2utilization = 0
            if(countwall2>0){
                
                 wall2utilization = utilizationwall2/countwall2
                
                self.labelutilization2value.text = "\(wall2utilization)"
            }
            
            var wall3utilization = 0
            if(countwall3>0){
                 wall3utilization = utilizationwall3/countwall3
                
                self.labelutilization3value.text = "\(wall3utilization)"
            }
            var wall4utilization = 0
            if(countwall4>0){
                 wall4utilization = utilizationwall4/countwall4
                
                self.labelutilization4value.text = "\(wall4utilization)"
            }
            
            if(countall>0){
                let utilizationallfinal = utilizationall/countall
                
                self.labelutilizationallvalue.text = "\(utilizationallfinal)"
            }
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
    }
    
    
    func loadwallimage(imagewall:UIImageView,wall:String){
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        
        let islandRef = storageRef.child("DCIM/\(marker.title!)_\(wall).jpg")
        
        let imageviewe =  imagewall
        
        imageviewe.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
        imageviewe.contentMode = UIViewContentMode.scaleAspectFit
        
        //using firebase UI to view image directly from firebase referrence ui
        imageviewe.sd_setImage(with: islandRef)
        
        
        
    }
    
   
    @IBAction func gotowall1(_ sender: Any) {
        
      
            
            let myVC = storyboard?.instantiateViewController(withIdentifier: "manholedetails") as! manholedetails
            myVC.manholename = marker.title
           myVC.wallnumbername = "W1"
            myVC.markerdetails = marker
            navigationController?.pushViewController(myVC, animated: true)
            
        
    }
    
    @IBAction func gotowall2(_ sender: Any) {
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "manholedetails") as! manholedetails
        myVC.manholename = marker.title
        myVC.wallnumbername = "W2"
        myVC.markerdetails = marker
        
        navigationController?.pushViewController(myVC, animated: true)
    }
    
    @IBAction func gotowall3(_ sender: Any) {
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "manholedetails") as! manholedetails
        myVC.manholename = marker.title
        myVC.wallnumbername = "W3"
         myVC.markerdetails = marker
        
        navigationController?.pushViewController(myVC, animated: true)
    }
    
    @IBAction func gotowall4(_ sender: Any) {
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "manholedetails") as! manholedetails
        myVC.manholename = marker.title
        myVC.wallnumbername = "W4"
        myVC.markerdetails = marker
        
        navigationController?.pushViewController(myVC, animated: true)
    }
    
    
}
