//
//  ViewController.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 21/03/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class ViewController: UIViewController ,UITableViewDelegate,UITableViewDataSource{
 
    @IBOutlet weak var tableview: UITableView!
    
    @IBOutlet weak var leadingconstraint: NSLayoutConstraint!

   
  
   
    @IBOutlet weak var tableviewmenu: UITableView!
    var menishowing = false
    var summarys:[summaryobject]? = []
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    var meNumanager2 = menumanager()
   let arraydatasources = ["Aging","Register","List TT"]

    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
      
        // Do any additional setup after loading the view, typically from a nib.
        
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
       // leadingconstraint.constant = -140

        
        fetchsummary()
    }
    override func viewWillDisappear(_ animated: Bool) {
        leadingconstraint.constant = 0
        
        UIView.animate(withDuration: 0.3, animations: {
            self.view.layoutIfNeeded()
        })
    }
    func fetchsummary(){
    
        let uuid = UIDevice.current.identifierForVendor!.uuidString
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/listttmobile_summary.php?uuid="+uuid)!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
        
        if let data = data {
            
            self.summarys = [summaryobject]()
            
            do{
            
            let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                
               if  let summaryfromjson  = json["summary"] as? [[String:AnyObject]]{
                
                    for summaryfromjson in summaryfromjson {
                        let summaryobjects = summaryobject()
                        if let building = summaryfromjson["buildingid"] as? String,let basket = summaryfromjson["basket"] as? String, let total = summaryfromjson["totaltt"]{
                            
                            
                            summaryobjects.buildingid = building
                            summaryobjects.basket = basket
                            summaryobjects.total = total as? String
                            
//                            print(summaryobjects.buildingid!)
//                            print(summaryobjects.basket!)
//                            print(summaryobjects.total!)

                        }
                        self.summarys?.append(summaryobjects)
                     
                    
                    }
                
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
        
        var cell:UITableViewCell?
        
        if (tableView == self.tableview)
        {
        
          let cell1 = tableView.dequeueReusableCell(withIdentifier: "summarycell", for: indexPath) as? summarycell
        
        cell1?.building.text = self.summarys?[indexPath.item].buildingid
        cell1?.basket.text = self.summarys?[indexPath.item].basket
        cell1?.total.text = self.summarys?[indexPath.item].total
            
         cell = cell1
          }
        if(tableView == self.tableviewmenu){
        
           let cell2 = tableView.dequeueReusableCell(withIdentifier: "cellmenu2", for: indexPath) as? cellmenu2
            cell2?.menuitem.text = arraydatasources[indexPath.item]
         cell = cell2!
        }
        return cell!
        
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
      
        
        var count:Int?
        
        if tableView == self.tableview {
        
        count = self.summarys?.count ?? 0
        }
        
        if tableView == self.tableviewmenu{
        count = arraydatasources.count
        }
        
        return count!
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if(tableView == self.tableviewmenu){
        
            tableviewmenu.layer.opacity = 5
            tableviewmenu.layer.shadowRadius = 10
            
            let textselected = arraydatasources[indexPath.item]
            
            if(textselected=="Register"){
                let myVC = storyboard?.instantiateViewController(withIdentifier: "register") as! register
                
                
                navigationController?.pushViewController(myVC, animated: true)
                
                
                
            }
            
            if(textselected=="List TT"){
                let myVC = storyboard?.instantiateViewController(withIdentifier: "listtt") as! listttcontroller
                
                
                navigationController?.pushViewController(myVC, animated: true)
                
            }
            
            if(textselected=="Aging"){
                let myVC = storyboard?.instantiateViewController(withIdentifier: "agingview") as! agingviewcontroller
                
                
                navigationController?.pushViewController(myVC, animated: true)
                
            }
            

        
        }
    }
   
    
    
    @IBAction func openthemenu(_ sender: Any) {
  
        
        if(menishowing){
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
        
        menishowing = !menishowing

       
    }
   
   
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

   
   }

