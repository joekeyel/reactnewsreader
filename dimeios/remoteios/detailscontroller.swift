//
//  detailscontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 04/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class detailscontroller: UIViewController,UITableViewDelegate,UITableViewDataSource,UIPopoverPresentationControllerDelegate {

    @IBOutlet weak var menudetailview: UITableView!
       @IBOutlet weak var tableview: UITableView!
    var detailstt:[String]? = []
     var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    var menumanager4 = menumanager()
    
    var stringPassed = ""
    var ttinfo = listttobject()
    
    var servicenumberstr:String?
    var correctpairstr:String?
    var targetcabinetstr:String?
    var targetdsloutstr:String?
    var targetpotsoutstr:String?
    var speedstr:String?
    var loginidstr:String?
    var addressstr:String?
    
    var menushowing = false
    let arraydatasources = ["History","Maps"]
    
    @IBOutlet weak var leadingconstraint: NSLayoutConstraint!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //hide the menu when first loading 
        
        leadingconstraint.constant = -140
        
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })

        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        fetchsummary()

        // Do any additional setup after loading the view.
    }
    
    func fetchsummary(){
        
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/details_tt_mobile.php?ttno="+stringPassed)!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
                self.detailstt = [String]()
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["masterlist"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            _ = detailsttobject()
                            
                            if let servicenumber = summaryfromjson["SERVICE NUMBER"] as? String,  let cabinetid = summaryfromjson["TARGET CABINET"], let correctpair = summaryfromjson["CORRECT DSIDE PAIR"], let targetpotsout = summaryfromjson["TARGET POTS OUT"], let speed = summaryfromjson["SPEED"], let targetdslout = summaryfromjson["TARGET DSL OUT"]{
                                
                                
                                
                                self.servicenumberstr = servicenumber
                                
                                self.correctpairstr = correctpair as? String
                                self.targetpotsoutstr = targetpotsout as? String
                                self.targetdsloutstr = targetdslout as? String
                                self.targetcabinetstr = cabinetid as? String
                                self.speedstr = speed as? String
                                
                                 self.detailstt?.append(self.stringPassed)
                                 self.detailstt?.append("Servicenumber:"+servicenumber)
                                 self.detailstt?.append("Correct Pair:"+(correctpair as? String)!)
                                 self.detailstt?.append("Target Pots Out:"+(targetpotsout as?String)!)
                                self.detailstt?.append("Target DSL Out:"+(targetdslout as? String)!)
                                 self.detailstt?.append("Cabinet ID:"+(cabinetid as? String)!)
                                self.detailstt?.append("Speed:"+(speed as? String)!)
                                
                                
                                
//                                listttobjects.ttno = ttno
//                                listttobjects.servicenumber = servicenumber
//                                listttobjects.referencenumber = referencenumber as? String
//                                listttobjects.cabinetid = cabinetid as? String
//                                listttobjects.contactnumber = customer_mobile_no as? String
//                                listttobjects.priority = priority as? String
//                                listttobjects.remark = remark as? String
//                                listttobjects.symtomcode = symtomcode as? String
//                                listttobjects.customername = customer_name as? String
//                                listttobjects.eside = esdie as? String
//                                listttobjects.dside = dsdie as? String
//                                listttobjects.createddate = createddate as? String
//                                listttobjects.reasoncode = reasoncode as? String
                                
                                
                                // print(listttobjects.cabinetid!)
                            }
                         
                            
                            
                        }
                        
                    }
                    
                    if  let summaryfromjson2  = json["tt_info"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson2 in summaryfromjson2 {
                         
                            
                            if let loginid = summaryfromjson2["referencenumber"] as? String{
                                
                                
                                
                                self.loginidstr = loginid
                                
                                self.detailstt?.append("Login ID:"+loginid)

                                
                                
                              
                            }
                            
                            
                        }
                        
                    }
                    
                    if  let address  = json["address"] as? String{
                        
                       
                        self.addressstr = address.trimmingCharacters(in: .whitespacesAndNewlines)
                        
                        self.detailstt?.append("Address:"+address)
                        
                        
                    }


                    DispatchQueue.main.async {
                   
                    self.tableview.reloadData()

                        
                    self.activitiyindicator.stopAnimating()
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
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        var cell1:UITableViewCell?
        
        if(tableView == self.tableview){
          let cell = tableView.dequeueReusableCell(withIdentifier: "detailcell", for: indexPath) as! detailscell
        
          cell.itemdetails.text = self.detailstt![indexPath.item]
        
          cell1 = cell
        
        
        }
        if(tableView == self.menudetailview){
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "menucelldetail", for: indexPath) as! viewcellmenudetail
            
            cell.itemdetails.text = self.arraydatasources[indexPath.item]
            cell1 = cell
            
        }
        
        return cell1!
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        var count:Int = 0
        if(tableView == self.tableview){
        
         count = self.detailstt?.count ?? 0
            
        }
        if(tableView == self.menudetailview){
        
        count = self.arraydatasources.count 
        }
        return count
        }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
       
        if(tableView == self.tableview){
        
        let updatevie = UIStoryboard(name:"userupdating",bundle:nil).instantiateViewController(withIdentifier: "updatestatus") as! updatestatusview
        
        updatevie.ttinfo = ttinfo
        
        navigationController?.pushViewController(updatevie, animated: true)
        
        let index = navigationController?.viewControllers.index(of: self)
        
        navigationController?.viewControllers.remove(at: index!)
            
        }
        if(tableView == self.menudetailview){
            
        let textselected = arraydatasources[indexPath.item]
        
            if(textselected == "History"){
                
                //open the popover modal
                
                self.performSegue(withIdentifier: "poplisttt", sender: self)
            }
            if(textselected == "Maps"){
                
                let updatevie = UIStoryboard(name:"maps",bundle:nil).instantiateViewController(withIdentifier: "mapview") as! mapsviews
                
                
                navigationController?.pushViewController(updatevie, animated: true)
                
                
            }

        
        }

    }
    
    
     //for popover modal history of every tt
    
    @IBAction func openmenu4(_ sender: Any) {
        
        
       
        if(menushowing){
            leadingconstraint.constant = -140
            
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
        else{
            leadingconstraint.constant = 0
            
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
        }
        
        menushowing = !menushowing

    }
    
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "poplisttt"{
            

            let popoverViewController = segue.destination as! popoverlistttViewController
            
            popoverViewController.ttno = stringPassed
            popoverViewController.popoverPresentationController?.delegate = self
            
           
            
        }
    }
    
    func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
        return .none
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
