//
//  searchlist.swift
//  idraw
//
//  Created by Hasanul Isyraf on 12/01/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import Firebase


protocol sendDataToViewProtocol2 {
    func zoomtomarker(marker:GMSMarker)
}

class searchlist: UITableViewController,UISearchBarDelegate{
    
     var delegate:sendDataToViewProtocol2? = nil
    var markerDict :[GMSMarker] = []
    var filteredtt:[GMSMarker] = []
    var isSearching = false
    
    @IBOutlet var tableview: UITableView!
    
   

    @IBOutlet weak var searchbar: UISearchBar!
    override func viewDidLoad() {
        super.viewDidLoad()

        searchbar.delegate = self
        searchbar.returnKeyType = UIReturnKeyType.done
    }
    
    
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if isSearching{
            
            return filteredtt.count
        }
        else{
            return self.markerDict.count
            
        }
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell1:searchcell? = nil
        
        if(!isSearching){
           let cell = tableView.dequeueReusableCell(withIdentifier: "searchcell", for: indexPath) as? searchcell

        let markername:String = markerDict[indexPath.item].title!
        let marker:GMSMarker = markerDict[indexPath.item]

            cell?.labelmarker.text = markername
     
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        // Create a reference to the file you want to download
            let createdby:String = marker.userData as! String
            let islandRef = storageRef.child("remote_camera/"+createdby+"/"+marker.title!+".jpg")
        
            let imageviewe =  cell?.imagemarker
        
        imageviewe?.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
       imageviewe?.contentMode = UIViewContentMode.scaleAspectFit
        
        //using firebase UI to view image directly from firebase referrence ui
        imageviewe?.sd_setImage(with: islandRef)
        cell1 = cell
            
        }
        else{
           let cell = tableView.dequeueReusableCell(withIdentifier: "searchcell", for: indexPath) as? searchcell
            
            let markername:String = filteredtt[indexPath.item].title!
            let marker:GMSMarker = filteredtt[indexPath.item]
            
            cell?.labelmarker.text = markername
            
            let storage = FIRStorage.storage()
            let storageRef = storage.reference()
            // Create a reference to the file you want to download
            let createdby:String = marker.userData as! String
            let islandRef = storageRef.child("remote_camera/"+createdby+"/"+marker.title!+".jpg")
            
            let imageviewe =  cell?.imagemarker
            
            imageviewe?.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
        imageviewe?.contentMode = UIViewContentMode.scaleAspectFit
            
            //using firebase UI to view image directly from firebase referrence ui
            imageviewe?.sd_setImage(with: islandRef)
            cell1 = cell        }
        
        return cell1!
            

    }
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if isSearching{
            if(delegate != nil){
                // let tappedImage = tapGestureRecognizer.view as! UIImageView
                
                
                delegate?.zoomtomarker(marker: filteredtt[indexPath.item])
                self.dismiss(animated: true, completion: nil)
                
                
                
            }
            
        }else{
        
        if(delegate != nil){
            // let tappedImage = tapGestureRecognizer.view as! UIImageView
         
            
            delegate?.zoomtomarker(marker: markerDict[indexPath.item])
            self.dismiss(animated: true, completion: nil)
            
            
            
          }
        }
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
            
             filteredtt = markerDict.filter{($0.title?.contains(searchbar.text!))!}
           
            

            
            tableview.reloadData()
            
            if (filteredtt.count)>0{
               // view.endEditing(true)
                
            }
            
            
        }
    }

}
