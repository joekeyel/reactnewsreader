//
//  webviewcontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 21/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class webviewcontroller: UIViewController,UIWebViewDelegate,UITableViewDelegate,UITableViewDataSource {

   
    @IBOutlet weak var menutable: UITableView!
    @IBOutlet weak var webview: UIWebView!
    var menushowing = false
    
    @IBOutlet weak var leadingconstraint: NSLayoutConstraint!
    
    var parameters = ["building" : ""  ,
                      "stopdate" :  "",
                      "migrationdate" : "",
                      "targetcabinet" : ""  ,
                      "oldcabinet" : "" ,
                      "state" : "",
                      "sitename" : "" ]
    
    
    let arraydatasources = ["Aging","Summary","List TT","Schedule"]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
         AppUtility.lockOrientation(.landscapeLeft)
      
        
        webview.isUserInteractionEnabled = true
        webview.scalesPageToFit = true
        webview.scrollView.isScrollEnabled = true
        
        if(!menushowing){
            leadingconstraint.constant = -140
            
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })

           menushowing = !menushowing
        
        }
         print(parameters)
        let urlComp = NSURLComponents(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/ipmsan_android_graph4.php")!
        
        var items = [URLQueryItem]()
        
        for (key,value) in parameters {
            items.append(URLQueryItem(name: key, value: value))
        }
        
        items = items.filter{!$0.name.isEmpty}
        
        if !items.isEmpty {
            urlComp.queryItems = items
        }
        
        var urlRequest = URLRequest(url: urlComp.url!)
        urlRequest.httpMethod = "GET"
        
        self.navigationItem.title = "Graph Trend"
        webview.loadRequest(urlRequest)
        
        webview.delegate = self
        menutable.delegate = self
        
      
       
        
           }
   
    
    private func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.landscapeLeft
    }
    private func shouldAutorotate() -> Bool {
        return true
    }

    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
  
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.arraydatasources.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
          let cell = tableView.dequeueReusableCell(withIdentifier: "menugraphcell", for: indexPath) as! menugraphcell
        
          cell.item.text = arraydatasources[indexPath.item]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        menutable.layer.opacity = 5
        menutable.layer.shadowRadius = 10
        
        let textselected = arraydatasources[indexPath.item]
        
        if(textselected=="Summary"){

            let webVc = UIStoryboard(name:"Main",bundle:nil).instantiateViewController(withIdentifier: "summary1") as! ViewController
            
            
                
                navigationController?.pushViewController(webVc, animated: true)
            
        }
        
        if(textselected=="List TT"){
            let myVC = storyboard?.instantiateViewController(withIdentifier: "listtt")
            
            print(textselected)
            navigationController?.pushViewController(myVC!, animated: true)
            
        }
        
        if(textselected=="Aging"){
            let myVC = storyboard?.instantiateViewController(withIdentifier: "agingview") as! agingviewcontroller
            
            print(textselected)
            navigationController?.pushViewController(myVC, animated: true)
            
        }
        
        if(textselected=="Schedule"){
            let myVC = storyboard?.instantiateViewController(withIdentifier: "scheduleview") as! scheduleviewcontroller
            print(textselected)
            
            navigationController?.pushViewController(myVC, animated: true)
            
        }
        
        
    }

   
    @IBAction func openmenu(_ sender: Any) {
    
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
   
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        // Don't forget to reset when view is being removed
        AppUtility.lockOrientation(.all)
    }
}
