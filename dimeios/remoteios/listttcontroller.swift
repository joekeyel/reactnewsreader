//
//  listttcontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 03/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class listttcontroller: UIViewController,UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate {

    @IBOutlet weak var searchbar: UISearchBar!
    @IBOutlet weak var tableview: UITableView!
    
    var listtt:[listttobject]? = []
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    var menumanager3 = menumanager()
    
    var filteredtt:[listttobject]? = []
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
        
        fetchsummary()

        // Do any additional setup after loading the view.
    }
    
    
    func fetchsummary(){
        
        let uuid = UIDevice.current.identifierForVendor!.uuidString
        let urlrequest = URLRequest(url: URL(string:"http://58.27.84.166/mcconline/MCC%20Online%20V3/listttmobile_backup.php?uuid="+uuid)!)
        
        let task = URLSession.shared.dataTask(with: urlrequest){(data,response,error)  in
            
            if let data = data {
                
                self.listtt = [listttobject]()
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let summaryfromjson  = json["listtt"] as? [[String:AnyObject]]{
                        
                        for summaryfromjson in summaryfromjson {
                            let listttobjects = listttobject()
                            if let ttno = summaryfromjson["TTno"] as? String,
                                let servicenumber = summaryfromjson["ServiceNo"] as? String, let referencenumber = summaryfromjson["referencenumber"], let cabinetid = summaryfromjson["cabinetid"], let customer_mobile_no = summaryfromjson["customer_mobile_no"], let customer_name = summaryfromjson["customer_name"], let priority = summaryfromjson["priority"], let remark = summaryfromjson["remark"] ,let symtomcode = summaryfromjson["symtomcode"],let esdie = summaryfromjson["TargetHorizontal"],let dsdie = summaryfromjson["Vertical"],let createddate = summaryfromjson["Created_date"],let reasoncode = summaryfromjson["reasoncode"],let exchange = summaryfromjson["exchange"],let buildingid = summaryfromjson["buildingid"],let no = summaryfromjson["No"],let statusmdf = summaryfromjson["statusmdf"]{
                                
                           
                               
                                listttobjects.ttno = ttno
                                listttobjects.servicenumber = servicenumber
                                listttobjects.referencenumber = referencenumber as? String
                                listttobjects.cabinetid = cabinetid as? String
                                listttobjects.contactnumber = customer_mobile_no as? String
                                listttobjects.priority = priority as? String
                                listttobjects.remark = (remark as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)
                                listttobjects.symtomcode = symtomcode as? String
                                listttobjects.customername = customer_name as? String
                                listttobjects.eside = esdie as? String
                                listttobjects.dside = dsdie as? String
                                listttobjects.createddate = createddate as? String
                                listttobjects.reasoncode = reasoncode as? String
                                listttobjects.exchange = exchange as? String
                                listttobjects.building = buildingid as? String
                                listttobjects.no = no as? String
                                listttobjects.statusmdf = statusmdf as? String
                                listttobjects.aging = self.countaging(createdstr: (createddate as? String)!)
                               listttobjects.agingstr = self.countagingstr(createdstr: (createddate as? String)!)
                                
                                  // print(listttobjects.cabinetid!)
                            }
                            self.listtt?.append(listttobjects)
                            
                            
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

    // function to count tt aging that return hour in integer
    
    func countaging(createdstr:String)->Int{
        
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy-MM-dd HH:mm:ss"
        let dateend = dfmatter.date(from: createdstr)
        
        
        let dateRangeStart = Date()
        
        let components = Calendar.current.dateComponents([.hour, .minute], from: dateend!, to: dateRangeStart)
        
        
        return components.hour!
        
        //        print("\(String(describing: remarktext)) Aging is \(components.hour ?? 0) Hours and \(components.minute ?? 0) Minute")
        
    }
    func countagingstr(createdstr:String)->String{
        
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy-MM-dd HH:mm:ss"
        let dateend = dfmatter.date(from: createdstr)
        
        
        let dateRangeStart = Date()
        
        let components = Calendar.current.dateComponents([.hour, .minute], from: dateend!, to: dateRangeStart)
        
        
        
                return "Aging is \(components.hour ?? 0) Hours and \(components.minute ?? 0) Minute"
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ttcustomcell", for: indexPath) as! ttcustomcell
       
        if isSearching{
        
            cell.cabinetid.text = self.filteredtt![indexPath.item].cabinetid
            cell.servicenumber.text = self.filteredtt?[indexPath.item].servicenumber
            cell.eside.text = self.filteredtt?[indexPath.item].eside
            cell.dside.text = self.filteredtt?[indexPath.item].dside
            cell.loginid.text = self.filteredtt?[indexPath.item].referencenumber
            cell.symtomcode.text = self.filteredtt?[indexPath.item].symtomcode
            cell.customer.text = self.filteredtt?[indexPath.item].customername
            cell.contact.text = self.filteredtt?[indexPath.item].contactnumber
            cell.remark.text = self.filteredtt?[indexPath.item].remark
            cell.createddate.text = self.filteredtt?[indexPath.item].createddate
            cell.reasoncode.text = self.filteredtt?[indexPath.item].agingstr
            cell.priority.text =  self.filteredtt?[indexPath.item].priority
            cell.ttno.text = self.filteredtt?[indexPath.item].ttno
            
            let agingint : Int = (self.filteredtt?[indexPath.item].aging)!
            if(agingint > 23){
                cell.bgview.backgroundColor = UIColor.red
            }else{
                cell.bgview.backgroundColor = UIColor.white
            }
            
            return cell

        
        }
        else{
       cell.cabinetid.text = self.listtt![indexPath.item].cabinetid
       cell.servicenumber.text = self.listtt?[indexPath.item].servicenumber
       cell.eside.text = self.listtt?[indexPath.item].eside
       cell.dside.text = self.listtt?[indexPath.item].dside
       cell.loginid.text = self.listtt?[indexPath.item].referencenumber
       cell.symtomcode.text = self.listtt?[indexPath.item].symtomcode
       cell.customer.text = self.listtt?[indexPath.item].customername
       cell.contact.text = self.listtt?[indexPath.item].contactnumber
       cell.remark.text = self.listtt?[indexPath.item].remark
       cell.createddate.text = self.listtt?[indexPath.item].createddate
       cell.reasoncode.text = self.listtt?[indexPath.item].agingstr
       cell.priority.text =  self.listtt?[indexPath.item].priority
       cell.ttno.text = self.listtt?[indexPath.item].ttno
            
            let agingint : Int = (self.listtt?[indexPath.item].aging)!
            if(agingint > 23){
                cell.bgview.backgroundColor = UIColor.red
            }else{
                cell.bgview.backgroundColor = UIColor.white
            }
               
        return cell
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if isSearching{
        
            return filteredtt!.count
        }
        else{
        return self.listtt?.count ?? 0
        
        }
        
    }
    
    var valueToPass:String?
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
       
        let ttno : String
        let ttinfo : listttobject
        
        if isSearching{
         ttno = (self.filteredtt?[indexPath.item].ttno)!
         ttinfo = (self.filteredtt?[indexPath.item])!
        }
        
        else{
         ttno = (self.listtt?[indexPath.item].ttno)!
         ttinfo = (self.listtt?[indexPath.item])!
        }
        valueToPass = ttno
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "details") as! detailscontroller
        
        myVC.stringPassed = ttno
        myVC.ttinfo = ttinfo
        
        navigationController?.pushViewController(myVC, animated: true)
        
        
        let index = navigationController?.viewControllers.index(of: self)
       
        navigationController?.viewControllers.remove(at: index!)
    
      
        print(ttinfo.ttno ?? "nodata")
        
    }
    

    
    


    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
       
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
            filteredtt = listtt?.filter({$0.cabinetid?.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame || $0.servicenumber?.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame || $0.exchange?.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame || $0.building?.caseInsensitiveCompare(searchBar.text!) == ComparisonResult.orderedSame})
            
            tableview.reloadData()
            
            if (filteredtt?.count)!>0{
                view.endEditing(true)}

            
        
        }
    }
    

  
}
