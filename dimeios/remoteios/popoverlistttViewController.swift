//
//  popoverlistttViewController.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 18/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class popoverlistttViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    
    @IBOutlet weak var tableview: UITableView!
    
    
   
      let arraydatasources = ["Aging","Summary","List TT"]
      var summarys:[auditobject]? = []
     var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    var ttno = ""

    override func viewDidLoad() {
        super.viewDidLoad()
        
      
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
        fetchsummary2()

        // Do any additional setup after loading the view.
    }
    
    
    func fetchsummary2(){
        
       
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/details4.php?ttno="+ttno)!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
                self.summarys = [auditobject]()
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["audit"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            let summaryobjects = auditobject()
                            if let remark = summaryfromjson["remark"] as? String,let updateby = summaryfromjson["updatedby"] as? String,let lastmodified = summaryfromjson["lastmodified"] as? String{
                                
                                
                                summaryobjects.remark = remark
                                summaryobjects.updateby = updateby
                                summaryobjects.lastmodified = lastmodified
                               
                                                               
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
    

    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.summarys?.count ?? 0
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellmenupopover", for: indexPath) as! cellmenupopover
        
        
        cell.menuitem.text = summarys?[indexPath.item].remark
        cell.updateby.text = summarys?[indexPath.item].updateby
        cell.lastmodified.text = summarys?[indexPath.item].lastmodified

        
      //  print(cell.menuitem.text ?? "")
        
        return cell
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
