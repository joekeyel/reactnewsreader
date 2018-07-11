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
    
    @IBOutlet weak var ductnestname: UILabel!
    @IBOutlet weak var ductlistlabel: UILabel!
   
    @IBOutlet weak var ndradio1: DLRadioButton!
    
    @IBOutlet weak var ndradio4: DLRadioButton!
    @IBOutlet weak var ndradio3: DLRadioButton!
    @IBOutlet weak var ndradio2: DLRadioButton!
    @IBOutlet weak var update: UIButton!
    @IBOutlet weak var labelpopover: UILabel!
    
    var consecutivesrow = false
    var consecutivescol = false
    var collist:[Int] = []
    var rowlist:[String] = []
    var startduct:String = ""
    var wall:String = ""
    
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
        
        
        view.addSubview(ndradio1)
        ndradio1.translatesAutoresizingMaskIntoConstraints = false
        
        self.ndradio1.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ndradio1.topAnchor.constraint(equalTo: labelpopover.bottomAnchor,constant:10).isActive = true
        self.ndradio1.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-2).isActive = true
        ndradio1.isHidden = true
        
        view.addSubview(ndradio2)
        ndradio2.translatesAutoresizingMaskIntoConstraints = false
        
        self.ndradio2.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ndradio2.topAnchor.constraint(equalTo: ndradio1.bottomAnchor,constant:10).isActive = true
        self.ndradio2.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        ndradio2.isHidden = true
        
        view.addSubview(ndradio3)
        ndradio3.translatesAutoresizingMaskIntoConstraints = false
        
        self.ndradio3.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ndradio3.topAnchor.constraint(equalTo: ndradio2.bottomAnchor,constant:10).isActive = true
        self.ndradio3.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        ndradio3.isHidden = true
        
        view.addSubview(ndradio4)
        ndradio4.translatesAutoresizingMaskIntoConstraints = false
        
        self.ndradio4.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ndradio4.topAnchor.constraint(equalTo: ndradio3.bottomAnchor,constant:10).isActive = true
        self.ndradio4.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-5).isActive = true
        ndradio4.isHidden = true
        
        
        
        view.addSubview(ductnestname)
        ductnestname.translatesAutoresizingMaskIntoConstraints = false
        
        self.ductnestname.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ductnestname.topAnchor.constraint(equalTo: ndradio4.bottomAnchor,constant:10).isActive = true
        self.ductnestname.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        view.addSubview(update)
        update.translatesAutoresizingMaskIntoConstraints = false
        
        self.update.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.update.topAnchor.constraint(equalTo: ductnestname.bottomAnchor,constant:10).isActive = true
        self.update.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        
        
        
        
        view.addSubview(ductlistlabel)
        ductlistlabel.translatesAutoresizingMaskIntoConstraints = false
        
        self.ductlistlabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        self.ductlistlabel.topAnchor.constraint(equalTo: update.bottomAnchor,constant:10).isActive = true
        self.ductlistlabel.widthAnchor.constraint(equalTo: view.widthAnchor,constant:-24).isActive = true
        
        
        
        let sorted = Array(ductdictionary1.keys).sorted()
        
        //print(sorted)
        
        
        
        
        
        
        for duct in sorted {
            
            ductlist = "\(ductlist) \(duct) "
            
            
            let endIndex = duct.index(duct.endIndex, offsetBy: -2)
            wall = duct.substring(to: endIndex)
            
            
            let endIndex2 = duct.index(duct.startIndex, offsetBy: 3)
            let col = duct.substring(from: endIndex2)
            
            
            let endIndex3 = duct.index(duct.startIndex, offsetBy: 2)
            var row = duct.substring(from: endIndex3)
            let endIndex4 = row.index(row.endIndex, offsetBy: -1)
            row = row.substring(to: endIndex4)
            
            
            
            
            
            if(!collist.contains(Int(col)!)){
                collist.append(Int(col)!)
                
            }
            
            if(!rowlist.contains(row)){
                rowlist.append(row)
                
            }
            //print(wall)
        }
        print(collist)
        print(rowlist)
        //print(sorted[0])
        
        
        consecutivescol = collist.map { $0 - 1 }.dropFirst() == collist.dropLast()
        
        
        //let consecutivesrow = rowlist.map { $0 - 1 }.dropFirst() == rowlist.dropLast()
        if(consecutivescol){
            
            print("Column In Order")
        }
        
        
        for row in rowlist{
            
            let nextletterlist = nextLetter(row)
            if(rowlist.count>1){
                if(!(row == rowlist.last)){
                    if(rowlist.contains(nextletterlist!)){
                        consecutivesrow = true
                    }
                    
                }
                
            }
            
        }
        
        if(rowlist.count == 1){
            
            consecutivesrow = true
            
        }
        
        if(consecutivesrow){
            
            print("Row In Order")
        }
        
        startduct = sorted[0]
        let endIndex = startduct.index(startduct.startIndex, offsetBy: 2)
        startduct = startduct.substring(from: endIndex)
        
        
        self.ductlistlabel.numberOfLines = 0
        self.ductlistlabel.lineBreakMode = .byWordWrapping
        self.ductlistlabel.sizeToFit()
        self.ductlistlabel.font  = UIFont.italicSystemFont(ofSize: 10)
        ductlistlabel.text = "\(ductlist) Start Duct is \(startduct)"
        
        let manholename:String = marker.title!
        
        checkavailablenesduct(manhole:manholename , wallnumber: wall)
    }
    
    //function to check next letter
    func nextLetter(_ letter: String) -> String? {
        
        // Check if string is build from exactly one Unicode scalar:
        guard let uniCode = UnicodeScalar(letter) else {
            return nil
        }
        switch uniCode {
        case "A" ..< "Z":
            return String(UnicodeScalar(uniCode.value + 1)!)
        default:
            return nil
        }
    }
    
    @IBAction func addaction(_ sender: Any) {
        
        let ductneststr = ductnestname.text as! String
        let manholename = marker.title as! String
        
        if(consecutivesrow == true && consecutivescol == true){
            
            let width:Int = collist.count
            let height:Int = rowlist.count
            
            let endIndex = startduct.index(startduct.endIndex, offsetBy: -1)
            let startcharacter:String = startduct.substring(to: endIndex)
            
            let endIndex2 = startduct.index(startduct.startIndex, offsetBy: 1)
            let col:Int = Int(startduct.substring(from: endIndex2))!
            
            let char : UnicodeScalar = UnicodeScalar(startcharacter)!
            let startingValue = Int((char).value)
            var ductdictionaryrecreate = [String]()
            
            //recreateback the dimention of nestduct base on width startduct and height to ductdictionaryrecreate
            
            for j in 0 ..< height {
                
                for i in col ..< col+width {
                    print("\(wall)\(Character(UnicodeScalar(j + startingValue)!))\(i)")
                    ductdictionaryrecreate.append("\(wall)\(Character(UnicodeScalar(j + startingValue)!))\(i)")
                    
                }
            }
            
            for duct1 in ductdictionaryrecreate {
                
                
                addnewnestduct(duct: duct1, nesduct: ductneststr, manholeid: manholename, startduct: startduct, width: width, height: height)
                
            }
            
            //        for duct1 in ductdictionary1 {
            //
            //
            //            addnewnestduct(duct: duct1.key, nesduct: ductneststr, manholeid: manholename, startduct: startduct, width: width, height: height)
            //
            //           }
        }
        
    }
    
    
    func addnewnestduct(duct:String,nesduct:String,manholeid:String,startduct:String,width:Int,height:Int){
        
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
                
                ref.child("Nesductidutilization").child(marker.title!).child(nesduct).child(duct).child("startduct").setValue(startduct)
                
                ref.child("Nesductidutilization").child(marker.title!).child(nesduct).child(duct).child("width").setValue(width)
                
                ref.child("Nesductidutilization").child(marker.title!).child(nesduct).child(duct).child("height").setValue(height)
                
                addnewductmysql(manholeid:marker.title!,nesduct:nesduct,duct:duct,occupancy:"AVAILABLE",utilization:0,height: height,width:width,startduct: startduct)
                
            }
            
        }
        
        delegate?.getter()
        self.dismiss(animated: true, completion: nil)
        
    }
    
    
    func addnewductmysql(manholeid:String,nesduct:String,duct:String,occupancy:String,utilization:Int,height:Int,width:Int,startduct:String) {
        let parameters = ["manholeid" : manholeid  ,
                          "nestduct" :  nesduct,
                          "occupancy" : occupancy,
                          "duct" : duct,
                          "startduct" : startduct,
                          "height" : height,
                          "width" : width,
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
    
    
    
    func checkavailablenesduct(manhole:String, wallnumber:String){
        
        
        var nestduct:String = ""
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("Nesductidutilization").child(manhole)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            
            
            for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {//Nest Duct level
                
                print(rest2.key)
                
                for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {//duct level
                    
                    
                    
                    if(rest3.key.range(of:wallnumber) != nil){
                        
                        
                        nestduct = "\(nestduct) \(rest2.key)"
                        // print(rest2.key)
                        
                        
                        
                    }
                    
                    
                    
                    
                    
                    
                }
                
                
                
                
            }
            //show radio button if the nesduct is available
            
            
            print("Nesduct \(nestduct)")
            
            if(nestduct.range(of:"DN1") == nil){
                
                self.ndradio1.isHidden = false
                
            }
            if(nestduct.range(of:"DN2") == nil){
                
                self.ndradio2.isHidden = false
                
            }
            if(nestduct.range(of:"DN3") == nil){
                
                self.ndradio3.isHidden = false
                
            }
            if(nestduct.range(of:"DN4") == nil){
                
                self.ndradio4.isHidden = false
                
            }
            
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        
        
        
        
    }
    
    @IBAction func radiobuttonaction(_ sender: DLRadioButton) {
        
         ductnestname.text = "DN1"
        
    }
    
    @IBAction func radiobuttonaction2(_ sender: DLRadioButton) {
        
         ductnestname.text = "DN2"
    }
    
    @IBAction func radiobuttonaction3(_ sender: DLRadioButton) {
         ductnestname.text = "DN3"
    }
    
    
    @IBAction func radiobuttonaction4(_ sender: Any) {
         ductnestname.text = "DN4"
    }
}
