//
//  popovernewductnest.swift
//  idraw
//
//  Created by Hasanul Isyraf on 11/03/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import Firebase

protocol protocoladdnewnestduct {
    
    
    func getter()
    
}

class popovernewductnest: UIViewController {
    
    @IBOutlet weak var ductlistlabel: UILabel!
    @IBOutlet weak var ductnesttv: UITextField!
    
    @IBOutlet weak var update: UIButton!
    @IBOutlet weak var labelpopover: UILabel!
    
     var delegate:protocoladdnewnestduct? = nil
    var marker = GMSMarker()
    
      var ductlist: String = ""
    
      var ductdictionary1 = [String : String]()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addSubview(labelpopover)
        labelpopover.translatesAutoresizingMaskIntoConstraints = false
        
        self.labelpopover.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.labelpopover.topAnchor.constraint(equalTo: view.topAnchor,constant:10).isActive = true
        self.labelpopover.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true

        
        view.addSubview(ductnesttv)
        ductnesttv.translatesAutoresizingMaskIntoConstraints = false
        
        self.ductnesttv.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ductnesttv.topAnchor.constraint(equalTo: labelpopover.bottomAnchor,constant:10).isActive = true
        self.ductnesttv.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(update)
        update.translatesAutoresizingMaskIntoConstraints = false
        
        self.update.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.update.topAnchor.constraint(equalTo: ductnesttv.bottomAnchor,constant:10).isActive = true
        self.update.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(ductlistlabel)
        ductlistlabel.translatesAutoresizingMaskIntoConstraints = false
        
        self.ductlistlabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ductlistlabel.topAnchor.constraint(equalTo: update.bottomAnchor,constant:10).isActive = true
        self.ductlistlabel.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
       
        
        
        for duct in ductdictionary1 {
            
            ductlist = "\(ductlist)\n\(duct.key)\n"
          
            print(duct)
        }
        self.ductlistlabel.numberOfLines = 0
        self.ductlistlabel.lineBreakMode = .byWordWrapping
        self.ductlistlabel.sizeToFit()
        self.ductlistlabel.font  = UIFont.italicSystemFont(ofSize: 10)
        ductlistlabel.text = "\(ductlist)"
    }

   
    @IBAction func addaction(_ sender: Any) {
        
         let ductneststr = ductnesttv.text as! String
      let manholename = marker.title as! String
        
        for duct1 in ductdictionary1 {
            
           
           addnewnestduct(duct: duct1.key, nesduct: ductneststr, manholeid: manholename)
           
        }
        
        
    }
    
    
    func addnewnestduct(duct:String,nesduct:String,manholeid:String){
        
        let currentuser:String = (FIRAuth.auth()?.currentUser?.email)!
        let creatdby: String = marker.userData as! String
        
        if(!nesduct.isEmpty){
            
            if(currentuser == creatdby ){
                
                var ref: FIRDatabaseReference!
                
                ref = FIRDatabase.database().reference()
                
                
                ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child(duct).child("nestductid").setValue(nesduct)
                
                ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child(duct).child("occupancy").setValue("AVAILABLE")
                
                ref.child("Nesductidutilization").child(marker.title!).child(nesduct).child(duct).child("occupancy").setValue("AVAILABLE")
                
                ref.child("Nesductidutilization").child(marker.title!).child(nesduct).child(duct).child("utilization").setValue(0)
                
                addnewductmysql(manholeid:marker.title!,nesduct:nesduct,duct:duct,occupancy:"AVAILABLE",utilization:0)
                
            }
            
        }
        
        delegate?.getter()
        self.dismiss(animated: true, completion: nil)
        
    }
    
    
    func addnewductmysql(manholeid:String,nesduct:String,duct:String,occupancy:String,utilization:Int) {
        let parameters = ["manholeid" : manholeid  ,
                          "nestduct" :  nesduct,
                          "occupancy" : occupancy,
                          "duct" : duct,
                          "createdby" : FIRAuth.auth()?.currentUser?.email! ?? "",
                          "utilization" : utilization ].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/addnewduct.php")! as URL)
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
