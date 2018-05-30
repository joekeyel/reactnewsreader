//
//  reasoncodeview.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 29/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class reasoncodeviewaging: UIViewController,UIPickerViewDataSource,UIPickerViewDelegate {
    
    var ttinfo = listttobject()
    var reasoncodelist:[String] = []
    var reasoncode = ""
    var username = ""
    var statusmdf = ""
    var remark = ""
    var delayreason = ""
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()

 
    @IBOutlet weak var customername: UILabel!
    @IBOutlet weak var servicenumber: UILabel!
    @IBOutlet weak var latereason: UITextView!
   
    @IBOutlet weak var contactnumber: UILabel!
    @IBOutlet weak var bgview: UIView!
    @IBOutlet weak var selectview: UIPickerView!
    @IBOutlet weak var updatebutton: UIButton!
   
    @IBOutlet weak var selectvalue: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        contactnumber.text = ttinfo.contactnumber
        customername.text = ttinfo.customername
        servicenumber.text = ttinfo.servicenumber
        
        
        bgview.layer.cornerRadius = 10
        selectvalue.layer.cornerRadius = 10
        updatebutton.layer.cornerRadius = 10
        latereason.layer.cornerRadius = 10
        
        fetchreasoncode()
        getuser()

        //hide keyboard when tapping
       self.hideKeyboardWhenTappedAround()
    }
    
    func fetchreasoncode(){
        
       
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/select_reasoncode_ipmsan.php")!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
               
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["reasoncode"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                           
                            if let reasoncode = summaryfromjson["closed_code"] as? String{
                                
                                
                            self.reasoncodelist.append(reasoncode)
                      
                                
                            }
                            
                            
                            
                        }
                        
                    }
                    DispatchQueue.main.async {
//                        self.tableview.reloadData()
//                        self.activitiyindicator.stopAnimating()
                        
                      self.selectview.reloadAllComponents()
                        if let i = self.reasoncodelist.index(where: { $0 == self.ttinfo.reasoncode }) {
                            self.selectview.selectRow(i, inComponent: 0, animated: true)
                            self.reasoncode = self.reasoncodelist[i]
                        }
                    }
                    
                }
                    
                    
                catch let error as NSError {
                    print(error.localizedDescription)
                }
                
            }
                
                
            else if let error = error {
                print(error.localizedDescription)
            }
            
            
        }
        
        task.resume()
    }
    
    func updatereasoncode(){
        
        if(latereason.text.isEmpty){
            
             let alertController = UIAlertController(title: "Delay Reason Code", message: " Pls Insert Delay Reason Code", preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.cancel) { (result : UIAlertAction) -> Void in
                self.activitiyindicator.stopAnimating()
                
            }
          
            alertController.addAction(cancelAction)
           
            self.present(alertController, animated: true, completion: nil)
            
        }else{
        
        let reasoncodeinput = reasoncode
         delayreason = latereason.text
        let uuid = UIDevice.current.identifierForVendor!.uuidString
        
        
        let parameters = ["uuid" : uuid,
                          "MM_Username" :  username,
                           "updateby" :  username,
                          "ttno" : ttinfo.ttno!,
                          "serviceno" : ttinfo.servicenumber!,
                          "servicenumber" : ttinfo.servicenumber!,
                          "No" : ttinfo.no!,
                          "created_date" : ttinfo.createddate!,
                          "select" : statusmdf,
                          "textarea" : remark,
                          "basket" : "mcc",
                          "reasoncode" : reasoncodeinput,
                          "delayreason" : delayreason].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/update_status_mobile.php")! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
        
       
        print(postString)
        
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
            
            let responseString = String(data: data!, encoding: String.Encoding.utf8)
            DispatchQueue.main.async {
                
                
                let updatevie = UIStoryboard(name:"Main",bundle:nil).instantiateViewController(withIdentifier: "listtt") as! listttcontroller
                
                
                
                self.navigationController?.pushViewController(updatevie, animated: true)
                
                let index = self.navigationController?.viewControllers.index(of: self)
                self.navigationController?.viewControllers.remove(at: index!)
                
                self.activitiyindicator.stopAnimating()
                
                
            }
            
            print("responseString = \(String(describing: responseString))")
        }
        task.resume()
            
        }
        
    }
    
    func getuser(){
        
        let uuid = UIDevice.current.identifierForVendor!.uuidString
        var request = URLRequest(url: URL(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/listttmobile_summary_test.php?uuid="+uuid)! as URL)
        request.httpMethod = "POST"
        
        
        
        let task2 = URLSession.shared.dataTask(with: request){(data,response,error)  in
            
            if let data = data {
                
                
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let name  = json["name"] as? String,let groupand  = json["group"] as? String{
                        
                        self.username = name+" "+groupand
                        print(name)
                        
                    }
                    
                    
                }
                    
                    
                catch let error as NSError {
                    print(error.localizedDescription)
                }
                
            }
                
                
            else if let error = error {
                print(error.localizedDescription)
            }
            
            
        }
        
        task2.resume()
        
        
        
    }

    

    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return reasoncodelist.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return reasoncodelist[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        print(reasoncodelist[row])
        
        selectvalue.text = reasoncodelist[row]
        reasoncode = reasoncodelist[row]
    }
    
    

  
    @IBAction func updatereason(_ sender: Any) {
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
        
        
        
        
        let alertController = UIAlertController(title: "Submit to MCC?", message: " Reasoncode:"+reasoncode, preferredStyle: UIAlertControllerStyle.alert)
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.cancel) { (result : UIAlertAction) -> Void in
            self.activitiyindicator.stopAnimating()
            
        }
        let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
            self.updatereasoncode()
        }
        alertController.addAction(cancelAction)
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
        
        
    }
    
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
