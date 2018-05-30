//
//  agingviewcontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 15/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class agingviewcontroller: UIViewController,UITableViewDelegate,UITableViewDataSource {

    
    @IBOutlet weak var tableview: UITableView!
    
     var summarys:[agingobject]? = []
     var summarys2:[capacityobj]? = []
     var summarys3:[totalttmigratiion]? = []
     var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        fetchsummary()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func fetchsummary(){
        
    
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/listttmobile_summary_aging.php")!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
                self.summarys = [agingobject]()
                self.summarys2 = [capacityobj]()
                self.summarys3 = [totalttmigratiion]()
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    
                    
                    if  let summaryfromjson  = json["summaryaging"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            
                             let summaryobjects = agingobject()
                           
                            if let exchange = summaryfromjson["exchange"] as? String, let aging24hours = summaryfromjson["aginglessthan1days"],let aging48hours = summaryfromjson["agingbetween1to3days"],let aging72hours = summaryfromjson["agingmorethan3days"],let totalcurrent = summaryfromjson["totaltt"]{
                                
                                
                                summaryobjects.exchange = exchange
                                summaryobjects.aging24hour = aging24hours as? String
                                summaryobjects.aging48hour = aging48hours as? String
                                summaryobjects.aging72hour = aging72hours as? String
                                summaryobjects.totalcurrenttt = totalcurrent as? String
                                
                                                            print(summaryobjects.exchange ?? "nodata")
                                                            print(summaryobjects.aging24hour ?? "nodata")
                                                            print(  summaryobjects.aging72hour ?? "nodata")
                                
                                
                                
                                
                            }
                          
                              self.summarys?.append(summaryobjects)
                            
                        }
                        
                    }
                    
                    if  let capacity  = json["count"] as? [[String:AnyObject]]{
                        for capacity in capacity{
                         let capacityobject = capacityobj()
                            if let capacityexchange = capacity["capacity"] as? String{
                            
                                capacityobject.migratesub = capacityexchange
                                print(  capacityobject.migratesub ?? "nodata")
                            
                            }
                          self.summarys2?.append(capacityobject)
                        }
                        
                    
                        
                    }
                    if  let totaltt  = json["counttt"] as? [[String:AnyObject]]{
                        
                        for totaltt in totaltt{
                            let totalttobjects = totalttmigratiion()
                            if let totaltt2 = totaltt["totaltt"] as? String{
                                
                                totalttobjects.totalttmigrate = totaltt2
                                print(  totalttobjects.totalttmigrate ?? "nodata")
                                
                            }
                            self.summarys3?.append(totalttobjects)
                            
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

    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return (self.summarys?.count)!
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
         let cell = tableView.dequeueReusableCell(withIdentifier: "agingcell", for: indexPath) as! agingcell
        
        cell.exchange.text = summarys?[indexPath.item].exchange
        cell.totalmigratesub.text = summarys2?[indexPath.item].migratesub
        cell.totalttmigration.text = summarys3?[indexPath.item].totalttmigrate
        cell.totalcurrenttt.text = summarys?[indexPath.item].totalcurrenttt
        
        cell.aging24hours.text = summarys?[indexPath.item].aging24hour
        cell.aging48hours.text = summarys?[indexPath.item].aging48hour
        cell.aging72hours.text = summarys?[indexPath.item].aging72hour

        return cell
        
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
