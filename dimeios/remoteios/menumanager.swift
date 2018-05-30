//
//  menumanager.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 12/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit

class menumanager: NSObject,UITableViewDelegate,UITableViewDataSource{
    
    let blackview = UIView()
    let menutableview = UITableView()
    let arraydatasources = ["Aging","Summary","Register"]
    
    public func openmenu(){
    
        if let window = UIApplication.shared.keyWindow{
        
                blackview.frame = window.frame
                blackview.backgroundColor = UIColor(white: 0, alpha: 0.5)
            
            let heights:CGFloat = 150
            let y = window.frame.height - heights
            menutableview.frame = CGRect(x: 0, y: window.frame.height, width: window.frame.width, height: heights)
            
            
            blackview.addGestureRecognizer(UITapGestureRecognizer(target:self,action: #selector(self.dismiss)))
            window.addSubview(blackview)
            window.addSubview(menutableview)
            
            UIView.animate(withDuration: 0.5, animations: {
            
                self.blackview.alpha = 1
                self.menutableview.frame.origin.y = y
            
            })
            
           
            
            
            
        
        
        }
    
    }
    
    public func dismiss(){
    
        UIView.animate(withDuration: 0.5, animations: {
            
            self.blackview.alpha = 0
            
            if let window = UIApplication.shared.keyWindow{
               self.menutableview.frame.origin.y = window.frame.height
            }
            
        })

    
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return arraydatasources.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellid", for: indexPath)
        
        cell.textLabel?.text = arraydatasources[indexPath.item]
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
       
        
        let item = arraydatasources[indexPath.item]
        
        if(item == "Summary"){
            
       
            func fromStoryboard() -> ViewController {
                return UIStoryboard(name: "summary", bundle: nil).instantiateViewController(withIdentifier: "CNContactPickerViewController") as! ViewController
            }
            
            
           
            
                               }
        
    
    }
    
    override init(){
    
        super.init()
        
        menutableview.delegate = self
        menutableview.dataSource = self
        
        menutableview.isScrollEnabled = false
        menutableview.bounces = false
        
      
        
        
        menutableview.register(BaseViewCell.classForCoder(), forCellReuseIdentifier: "cellid")
        
    
    
    }
    

}
