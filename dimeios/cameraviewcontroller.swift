//
//  cameraviewcontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 19/09/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Firebase
import GooglePlaces
import GoogleMaps


protocol addmarkeraftertagdelegate {
    func addmarkertomaps(marker:GMSMarker)
}
class cameraviewcontroller: UIViewController,UINavigationControllerDelegate,UIImagePickerControllerDelegate,UIPickerViewDelegate,UIPickerViewDataSource {

    @IBOutlet weak var uploadbutton: UIButton!
    @IBOutlet weak var imagecapture: UIImageView!
    
    @IBOutlet weak var imagename: UITextField!
    @IBOutlet weak var selectelement: UIPickerView!
    //for pickerview type of element
    
     let arrayregion = ["Cabinet","ManHole","DP"]
    
    var networkelement = "Cabinet"
    var marker1 = GMSMarker()
    
     var delegate:addmarkeraftertagdelegate? = nil
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        uploadbutton.isHidden = true
    }
    
    
    @IBAction func opencamera(_ sender: Any) {
        
        let imagepickercontroller = UIImagePickerController()
               imagepickercontroller.delegate = self
        let actionsheet = UIAlertController(title:"Photo Source",message:"Choose a source",preferredStyle:.actionSheet)
        
        actionsheet.addAction(UIAlertAction(title:"Camera",style:.default,handler:{(action:UIAlertAction) in
            
            imagepickercontroller.sourceType = .camera
            self.present(imagepickercontroller,animated: true,completion: nil)
            
        }))
        
        actionsheet.addAction(UIAlertAction(title:"Photo Library",style:.default,handler:{(action:UIAlertAction) in
            
            imagepickercontroller.sourceType = .photoLibrary
            self.present(imagepickercontroller,animated: true,completion: nil)
            
        }))
        
        actionsheet.addAction(UIAlertAction(title:"Cancel",style:.cancel,handler:nil))
        
        self.present(actionsheet,animated: true,completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        
        
        imagecapture.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
        imagecapture?.contentMode = UIViewContentMode.scaleAspectFit
        
        imagecapture.image = resizeToScreenSize(image: image)
        
           picker.dismiss(animated: true, completion: nil)
        uploadbutton.isHidden = false
        
       
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
  
    func resizeImage(image: UIImage, newWidth: CGFloat) -> UIImage {
        
        let scale = newWidth / image.size.width
        let newHeight = image.size.height * scale
        
        UIGraphicsBeginImageContext(CGSize(width: newWidth, height: newHeight))
        
        
        image.draw(in: CGRect(x: 0, y: 0,width: newWidth, height: newHeight))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
    
    func resizeToScreenSize(image: UIImage)->UIImage{
        
        let screenSize = self.view.bounds.size
        
        
        return resizeImage(image: image, newWidth: screenSize.width)
    }
   
    @IBAction func uploadimage(_ sender: Any) {
        
        // Create a root reference
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        
        let imagename = self.imagename.text
        
        if(!(imagename?.isEmpty)!){
            
            
            
        marker1.title = networkelement+"_"+imagename!
        
        var data = NSData()
        data = UIImageJPEGRepresentation(imagecapture.image!, 0.1)! as NSData
        let metaData = FIRStorageMetadata()
        metaData.contentType = "image/jpg"
            let createdby : String = (FIRAuth.auth()?.currentUser?.email)!
        // Create a reference to the file you want to upload
        let riversRef = storageRef.child("remote_camera/"+createdby+"/"+networkelement+"_"+imagename!+".jpg")
        
        // Upload the file to the path "images/rivers.jpg"
        _ = riversRef.put(data as Data, metadata: metaData) { (metadata, error) in
            guard let metadata = metadata else {
                // Uh-oh, an error occurred!
                return
            }
            // Metadata contains file metadata such as size, content-type, and download URL.
            _ = metadata.downloadURL
            let tmpController :UIViewController! = self.presentingViewController;
            
                            self.dismiss(animated: false, completion: {()->Void in
            
                                tmpController.dismiss(animated: false, completion: nil);
                                
                                 });
            
            //save the marker location to firebase storage
            var ref: FIRDatabaseReference!
            
            ref = FIRDatabase.database().reference()
            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(self.marker1.title!).setValue(["lat": self.marker1.position.latitude,"lng":self.marker1.position.longitude,"createdby":FIRAuth.auth()?.currentUser?.email ?? ""])
            
            //update mysql database
            
            self.updatemysql(markerin: self.marker1)
            
            if(self.delegate != nil){
                // let tappedImage = tapGestureRecognizer.view as! UIImageView
                
                
                self.delegate?.addmarkertomaps(marker: self.marker1)
                self.dismiss(animated: true, completion: nil)
                
                
                
            }
            
            }
        }
 
    }
    
    
    //reverse gecoding to get address base on marker click
    
    func updatemysqlserver(manholeid:String,state:String,postalcode:String,city:String,street:String,knownname:String,latitude:Double,longitude:Double,createdby:String) {
        let parameters = ["manholeid" : manholeid  ,
                          "state" :  state,
                          "city" : city,
                          "poskod" : postalcode,
                          "street" : street  ,
                          "knownname" : knownname ,
                          "createdby" : createdby ,
                          "latitude" : latitude ,
                          "longitude" : longitude].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.188/addnewmanhole.php")! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
        
        
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
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode == 200 {           // check for http errors
                
                print("response = \(String(describing: response))")
                self.showToast(message: "Updated")
                
            }
            
            
            
            
            
        }
        task.resume()
   
    }
    
    //function to update mysql server
    
    func updatemysql(markerin:GMSMarker){
        
       
         let geocoder = GMSGeocoder()
        
        var state: String = ""
        var postalcode: String = ""
        var city: String = ""
        var knownname: String = ""
        var addressline: String = ""
        
        
        geocoder.reverseGeocodeCoordinate(markerin.position, completionHandler:{ response, error in
            
            if let address = response?.firstResult() {
            
            
                if(address.lines != nil){
                    addressline = (address.lines?.joined(separator: "\n"))!
                    
                }
                
                if(address.locality != nil){
                    state = address.administrativeArea!
                    
                }
            
                
                if(address.postalCode != nil){
                postalcode = address.postalCode!
                }
                if(address.subLocality != nil){
                    city = address.locality!
                    
                }
                if(address.thoroughfare != nil){
                    knownname = address.thoroughfare!
                    
                }
                
                let manholeid = markerin.title!
                let createdby = FIRAuth.auth()?.currentUser?.email!
                let latitude = markerin.position.latitude
                let longitude = markerin.position.longitude
                
                
                self.updatemysqlserver(manholeid: manholeid,state: state,postalcode: postalcode,city: city,street: addressline,knownname: knownname,latitude: latitude,longitude:longitude,createdby:createdby!)
               
                
                print(addressline)
                print(state)
                print(postalcode)
                print(city)
                print(knownname)
                
            }
            
            
        })
        
      
        
      
        
      
        
        
        
        
    }
    //uipickerview
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return arrayregion.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return arrayregion[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
       // print(group)
        let ne = arrayregion[row]
        networkelement = ne
        print(networkelement)
        
    }
    

    

}
