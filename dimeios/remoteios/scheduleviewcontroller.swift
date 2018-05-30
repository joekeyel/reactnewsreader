//
//  scheduleviewcontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 19/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class scheduleviewcontroller: UIViewController,UITableViewDataSource,UITableViewDelegate,UISearchBarDelegate{

    @IBOutlet weak var searchbar: UISearchBar!
    @IBOutlet weak var tableview: UITableView!
    var shcdulearray:[shceduleobject]? = []
     var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()

    
    var filteredtt:[shceduleobject]? = []
   var shcdulearray_search = [shceduleobject]()
    var isSearching = false
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
        
        searchbar.delegate = self
        searchbar.returnKeyType = UIReturnKeyType.done
        
        fetchsummary3()

        // Do any additional setup after loading the view.
    }
    
    func fetchsummary3(){
        
        
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/scheduleyear1_json.php")!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
                self.shcdulearray = [shceduleobject]()
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["schedule"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            let summaryobjects = shceduleobject()
                            if let newcabinet = summaryfromjson["New Cabinet"] as? String?,let oldcabinet = summaryfromjson["Old Cabinet"] as? String?, let site = summaryfromjson["Site Name"] as? String!,let migdate = summaryfromjson["Migration Date"] as? String!,let stopdate = summaryfromjson["stopdate"] as? String!,let oldckc = summaryfromjson["ckcold"] as? String!,let newckc = summaryfromjson["ckcnew"] as? String!,let pmwno = summaryfromjson["pmwno"] as? String!,let status = summaryfromjson["Remark"] as? String!,let state = summaryfromjson["State"] as? String!,let building = summaryfromjson["Abbr"] as? String!,let projecttype = summaryfromjson["Projecttype"] as? String!,let abbrsite = summaryfromjson["Abbr"] as? String!{
                                
                                
                                //setting the query for webview
                                let parameters = ["building" : building!  ,
                                                  "stopdate" :  stopdate!,
                                                  "migrationdate" : migdate!,
                                                  "targetcabinet" : newcabinet!  ,
                                                  "oldcabinet" : oldcabinet! ,
                                                  "state" : state!,
                                                  "sitename" : site!,
                                                  "projecttype" : projecttype!]
                                
                                
//                                let query = parameters.joined(separator: "&")
//                                
//                                
                                summaryobjects.parameters = parameters
                                
                            //    print(summaryobjects.parameters)
                                
                                summaryobjects.url = "http://58.27.84.166/mcconline/MCC%20Online%20V3/ipmsan_android_graph2.php"
                                
                               // summaryobjects.parameter = query
                                summaryobjects.ckcnew = (newckc)!
                                summaryobjects.ckcold = (oldckc)!
                                summaryobjects.sitename = (site)!
                                summaryobjects.newcabinet = (newcabinet)!
                                summaryobjects.oldcabinet = (oldcabinet)!
                                summaryobjects.migrationdate = (migdate)!
                                summaryobjects.stopdate = (stopdate)!
                                summaryobjects.pmwno = (pmwno)!
                                summaryobjects.remark = (status)!
                                summaryobjects.ptt = (state)!
                                 summaryobjects.projecttype = (projecttype)!
                                 summaryobjects.abbrsite = (abbrsite)!
                                
                                if (site?.isEmpty)! {
                                    print("Nothing to see here")
                                }else{
                               print(site ?? "")}
//                          
                                
                            }
                            self.shcdulearray?.append(summaryobjects)
                            
                            
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
    
    func fetchsearchresult(abbr:String){
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
        shcdulearray_search = []
        
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/scheduleyear1__filter_json.php?abbr="+abbr)!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
               
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["schedule"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            let summaryobjects = shceduleobject()
                            if let newcabinet = summaryfromjson["New Cabinet"] as? String?,let oldcabinet = summaryfromjson["Old Cabinet"] as? String?, let site = summaryfromjson["Site Name"] as? String!,let migdate = summaryfromjson["Migration Date"] as? String!,let stopdate = summaryfromjson["stopdate"] as? String!,let oldckc = summaryfromjson["ckcold"] as? String!,let newckc = summaryfromjson["ckcnew"] as? String!,let pmwno = summaryfromjson["pmwno"] as? String!,let status = summaryfromjson["Remark"] as? String!,let state = summaryfromjson["State"] as? String!,let building = summaryfromjson["Abbr"] as? String!,let projecttype = summaryfromjson["Projecttype"] as? String!,let abbrsite = summaryfromjson["Abbr"] as? String!{
                                
                                
                                //setting the query for webview
                                let parameters = ["building" : building!  ,
                                                  "stopdate" :  stopdate!,
                                                  "migrationdate" : migdate!,
                                                  "targetcabinet" : newcabinet!  ,
                                                  "oldcabinet" : oldcabinet! ,
                                                  "state" : state!,
                                                  "sitename" : site!,
                                                  "projecttype" : projecttype!, "abbrsite" : abbrsite!]
                                
                                
                                //                                let query = parameters.joined(separator: "&")
                                //
                                //
                                summaryobjects.parameters = parameters
                                
                                //    print(summaryobjects.parameters)
                                
                                summaryobjects.url = "http://58.27.84.166/mcconline/MCC%20Online%20V3/ipmsan_android_graph2.php"
                                
                                // summaryobjects.parameter = query
                                summaryobjects.ckcnew = (newckc)!
                                summaryobjects.ckcold = (oldckc)!
                                summaryobjects.sitename = (site)!
                                summaryobjects.newcabinet = (newcabinet)!
                                summaryobjects.oldcabinet = (oldcabinet)!
                                summaryobjects.migrationdate = (migdate)!
                                summaryobjects.stopdate = (stopdate)!
                                summaryobjects.pmwno = (pmwno)!
                                summaryobjects.remark = (status)!
                                summaryobjects.ptt = (state)!
                                summaryobjects.projecttype = (projecttype)!
                                summaryobjects.abbrsite = (abbrsite)!
                                
                                if (site?.isEmpty)! {
                                    print("Nothing to see here")
                                }else{
                                    print(site ?? "")}
                                //
                                
                            }
                            self.shcdulearray_search.append(summaryobjects)
                            
                            
                        }
                        
                    }
                    DispatchQueue.main.async {
                       
                       self.tableview.reloadData()
                        self.activitiyindicator.stopAnimating()
                        self.view.endEditing(true)
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
        
       
        if isSearching{
        return shcdulearray_search.count
        }
        else{
        return shcdulearray!.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "shcedulecell", for: indexPath) as! shcedulecell
        
        if isSearching{
        
            cell.site.text = shcdulearray_search[indexPath.item].sitename
            cell.newcabinet.text = shcdulearray_search[indexPath.item].newcabinet
            cell.oldcabinet.text = shcdulearray_search[indexPath.item].oldcabinet
            cell.newckc.text = shcdulearray_search[indexPath.item].ckcnew
            cell.oldckc.text = shcdulearray_search[indexPath.item].ckcold
            cell.migrationdate.text = shcdulearray_search[indexPath.item].migrationdate
            cell.finishdate.text = shcdulearray_search[indexPath.item].stopdate
            cell.pmwno.text = shcdulearray_search[indexPath.item].pmwno
            cell.status.text = shcdulearray_search[indexPath.item].remark
            cell.ptt.text = shcdulearray_search[indexPath.item].ptt
            cell.projecttype.text = shcdulearray_search[indexPath.item].projecttype
        
        
        }else{
        
        cell.site.text = shcdulearray![indexPath.item].sitename
        cell.newcabinet.text = shcdulearray![indexPath.item].newcabinet
        cell.oldcabinet.text = shcdulearray![indexPath.item].oldcabinet
        cell.newckc.text = shcdulearray![indexPath.item].ckcnew
        cell.oldckc.text = shcdulearray![indexPath.item].ckcold
        cell.migrationdate.text = shcdulearray![indexPath.item].migrationdate
        cell.finishdate.text = shcdulearray![indexPath.item].stopdate
        cell.pmwno.text = shcdulearray![indexPath.item].pmwno
        cell.status.text = shcdulearray![indexPath.item].remark
        cell.ptt.text = shcdulearray![indexPath.item].ptt
        cell.projecttype.text = shcdulearray![indexPath.item].projecttype
        }
        return cell
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if searchBar.text == nil || searchBar.text == ""
        {
            
            isSearching = false
            view.endEditing(true)
            tableview.reloadData()
            
        }
        else{
            isSearching = true
//            filteredtt = shcdulearray?.filter({$0.sitename.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame || $0.abbrsite.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame })
             fetchsearchresult(abbr: searchBar.text!)
           
            
            if (shcdulearray_search.count)>0{
                view.endEditing(true)}
            
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let webVc = UIStoryboard(name:"Main",bundle:nil).instantiateViewController(withIdentifier: "web2") as! webviewcontroller
        
       // let myVC = storyboard?.instantiateViewController(withIdentifier: "web2")
       
        var parametersget = ["building" : ""  ,
                          "stopdate" :  "",
                          "migrationdate" : "",
                          "targetcabinet" : ""  ,
                          "oldcabinet" : "" ,
                          "state" : "",
                          "sitename" : "" ]
        
        
        if isSearching{
            
            parametersget = (self.shcdulearray_search[indexPath.item].parameters)
           
            webVc.parameters = parametersget
            
           
           self.navigationController?.pushViewController(webVc, animated: true)
            //self.present(webVc, animated: true, completion: nil)
        }
            
        else{
            
             parametersget = (self.shcdulearray?[indexPath.item].parameters)!
            print(parametersget)
             webVc.parameters = parametersget
           self.navigationController?.pushViewController(webVc, animated: true)
            
             //self.present(webVc, animated: true, completion: nil)
        }

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
