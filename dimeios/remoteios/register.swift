//
//  register.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 20/06/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Firebase
import GooglePlaces
import GoogleMaps
import Firebase
import FirebaseStorageUI
import Speech
import AVFoundation




class register: UIViewController,UITextFieldDelegate ,UITableViewDelegate,UITableViewDataSource,GMSMapViewDelegate,CLLocationManagerDelegate,UIPopoverPresentationControllerDelegate,SFSpeechRecognizerDelegate,sendDataToViewProtocol,sendDataToViewProtocol2,addmarkeraftertagdelegate{
  
    
    
    
    @IBOutlet weak var mapView: GMSMapView!
    
    var locationManager = CLLocationManager()
    
    
    let arraydatasources = ["Map Type","Sign Out","Boundry List"]
    
    
 
    
    var registerfcmtoken: String = ""
    var username = ""
    
    
    //current maker
    var currentarker : GMSMarker?
    
    
    @IBOutlet weak var searchbtn: UIBarButtonItem!
    
    @IBOutlet weak var signout: UIBarButtonItem!
  
    
  
    @IBOutlet weak var reloadbutton: UIBarButtonItem!
    @IBOutlet weak var grouppicker: UIPickerView!
    @IBOutlet weak var name: UITextField!
    
    @IBOutlet weak var number: UITextField!
    
    @IBOutlet weak var drawtoggle: UIBarButtonItem!
    @IBOutlet weak var building: UITextField!
    
    @IBOutlet weak var drawanchor: UIBarButtonItem!
    @IBOutlet weak var penbutton: UIImageView!
    
    var textField = UITextField()
    
    @IBOutlet weak var display: UILabel!
    @IBOutlet weak var registerbutton: UIButton!
    
    
    //collect all marker appear
     var markerDict :[GMSMarker] = []
    var markerDictboundry :[GMSMarker] = []
    
    // for speech recognizer
    let audioEngine = AVAudioEngine()
    let speechRecognizer : SFSpeechRecognizer =  SFSpeechRecognizer(locale:Locale.init(identifier:"ms-MY"))!
    let request = SFSpeechAudioBufferRecognitionRequest()
    var reconitionTask: SFSpeechRecognitionTask?
    var isRecording = false
    
    //voice synthesizer
    let synth = AVSpeechSynthesizer()
    var myUtterance = AVSpeechUtterance(string: "")
    
    //for menu slide out
    
    @IBOutlet weak var menuanchor: UIBarButtonItem!
    
    
    @IBOutlet weak var leadingconstraint: NSLayoutConstraint!
    
    
    @IBOutlet weak var menutableview: UITableView!
    
    var users = userobject()
    var menushowing = false
    var mapshowing = true
    var drawstart = false
    var dragstart = false
    var maptype = true
    var group = "SB"
    let blackview = UIView()
    let reference = FIRDatabase.database().reference()
    
    
    //drawing of polyline
    
    var polylinecoordinates = [CLLocationCoordinate2D]()
    let pathdraw = GMSMutablePath()
    var polyline2 = GMSPolyline()
    
    
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
  //  var meNumanager = menumanager()
    
    
    //for custominfo windows
    
    var tappedMarker : GMSMarker?
    var customInfoWindow : infowindowsmaps?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        textField.delegate = self
        
        // for map view
        
        
        
        mapView.isMyLocationEnabled = true
        mapView.delegate = self
        mapView.mapType = GMSMapViewType(rawValue: 4)
        
        //Location Manager code to fetch current location
        self.locationManager.delegate = self
        self.locationManager.startUpdatingLocation()
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
        
    
      
        
        let value = UIInterfaceOrientation.portrait.rawValue
        UIDevice.current.setValue(value, forKey: "orientation")
        
        mapView.layer.cornerRadius = 5
        display.layer.cornerRadius = 5
      
        leadingconstraint.constant = -140
        
        self.tappedMarker = GMSMarker()
        self.customInfoWindow = infowindowsmaps().loadView()
        
        mapView.settings.myLocationButton = true
        
        
        //hide the draw toggle initially
       
        penbutton.isHidden = true
      
        
      
        
    }
    
    //firebase listener
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidLoad()
        
       
        
        self.firebaselistenr()
        
        //check if user is login
       
            if FIRAuth.auth()?.currentUser == nil {
                
                let storyboard = UIStoryboard(name: "login", bundle: nil)
                
                let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "login") as! logincontroller
                
                self.navigationController?.pushViewController(initialViewController2, animated: true)
                
            } else {
                // do nothing
                // go to login controller
            }
            
        
        
        
        
    }
    
   
    
    
    @IBAction func reloadaction(_ sender: Any) {
        
        self.firebaselistenr()
        self.pathdraw.removeAllCoordinates()
        self.polylinecoordinates.removeAll()
        self.drawtoggle.title = "Draw"
        self.drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
      
        self.mapView.isUserInteractionEnabled = true
       
     
    }
    
    //function to move to streetview
    func inputData(marker: GMSMarker) {
       
                let myVC = storyboard?.instantiateViewController(withIdentifier: "streetview") as! streetview
                myVC.marker1 = marker
        
                navigationController?.pushViewController(myVC, animated: true)
        
    }
    
    
    func gotomanholesummary(marker: GMSMarker) {
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "manholesummary") as! manholesummary
        myVC.marker = marker
        
        navigationController?.pushViewController(myVC, animated: true)
        
    }
    
    func gotoextrainfo(marker: GMSMarker) {
        
        let myVC = storyboard?.instantiateViewController(withIdentifier: "manholeextrainfo") as! manholeextrainfo
        myVC.marker = marker
        
        navigationController?.pushViewController(myVC, animated: true)
        
    }
    
    func reloadmarker(){
        firebaselistenr()
    }
    func zoomtomarker(marker: GMSMarker) {
        
        let camera = GMSCameraPosition.camera(withLatitude: (marker.position.latitude), longitude:(marker.position.longitude), zoom:20)
        mapView.animate(to: camera)
        
    }
    //firebase listener
    
    func firebaselistenr(){
        
        mapView.clear()
        markerDict = []
        
        reference.observe(.value, with: { (snapshot) in
            // print(snapshot.value ?? "no user")
            
            let value = snapshot.value as? NSDictionary
            let username = value?["currentuser"] as? String ?? ""
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        
        //will listen to new event added
        let referencesketch = FIRDatabase.database().reference().child("sketch")
        
        referencesketch.observeSingleEvent(of: .value, with: { (snapshot) in
            
            var lastccordinates:CLLocationCoordinate2D? = nil
            var polylinename :String? = nil
             var createdby :String? = nil
            
            for rest in snapshot.children.allObjects as! [FIRDataSnapshot]{
                
                for rest2 in rest.children.allObjects as! [FIRDataSnapshot] {//user sketch name level
                    
                    let path = GMSMutablePath()
                    polylinename = rest2.key // to get the name of polyline
                    
                    for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {//polylinename
                        
                        if rest3.key == "createdby"{
                            createdby = rest3.value as? String
                           
                        }
                        
                        for rest4 in rest3.children.allObjects as! [FIRDataSnapshot] {//coordinate of each poliline
                            // print(rest4.value as! String)
                            let latlngstr:String = (rest4.value as? String)!
                            let latlngarray : [String] = latlngstr.components(separatedBy: ",")
                            let lat = (latlngarray[0] as NSString).doubleValue
                            let lng = (latlngarray[1] as NSString).doubleValue
                            
                            
                            //print(latlngarray[0]+","+latlngarray[1])
                            
                            path.add(CLLocationCoordinate2D(latitude: lat, longitude: lng))
                            
                            lastccordinates = CLLocationCoordinate2D(latitude: lat, longitude: lng)
                            
                            
                            
                        }
                    }
                    
                    let polyline2 = GMSPolyline(path: path)
                    polyline2.strokeColor = .green
                    polyline2.map = self.mapView
                    
                    // add marker to polyline base on names
                    let marker = GMSMarker()
                    marker.position = lastccordinates!
                    marker.title = polylinename
                    marker.userData = createdby
                    marker.icon = UIImage(named:"flagpole")
                  
                    
                    marker.map = self.mapView
                    
                    //putt all marker here
                   //  self.markerDict.append(marker)
                    
                    
                }
                
            
        }
            
            //  self.movemarker(lat: latitude, lng: longitude, username: username)
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        // initally load photo marker once
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("photomarkeridraw")
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
           
                var lat : Double = 1.1
                var lng : Double = 1.1
                var markername :String = ""
                var createdby : String = ""
            
            
            for rest in snapshot.children.allObjects as! [FIRDataSnapshot]{
               
                for rest2 in rest.children.allObjects as! [FIRDataSnapshot] {//photo marker user name level
                    for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {
                    
                    
                    if(rest3.key == "lat"){
                        
                        lat = rest3.value as! Double
                        
                    }
                    if(rest3.key == "lng"){
                        
                        lng = rest3.value as! Double
                        
                    }
                        
                    if(rest3.key == "createdby"){
                            
                        createdby = rest3.value as! String;
                            
                    }
                    
                    
                    }
                    markername = rest2.key
                    
                    let marker = GMSMarker()
                    marker.position = CLLocationCoordinate2D(latitude: lat, longitude: lng)
                    marker.title = markername
                    marker.userData = createdby
//
//                    if markername.range(of:"Cabinet_") != nil   {
//                        marker.icon = UIImage(named:"cabinet")
//                    }
                    if markername.range(of:"ManHole_") != nil {
                        marker.icon = UIImage(named:"mainhole")
                        //save marker for man hole only....this is for dime server
                         self.markerDict.append(marker)
                         marker.map = self.mapView
                    }
//                    if markername.range(of:"DP_") != nil {
//                        marker.icon = UIImage(named:"dppole")
//                    }
                    
                    
                   
                    marker.isDraggable = true
                    
                    //save all marker here
                    
                    // self.markerDict.append(marker)
                    
                }
            }
                
            
            
            
            
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
        
        
    }
    
    // for google maps
    
    //Location Manager delegates
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        let location = locations.last
        
        
        let marker = GMSMarker(position: (location?.coordinate)!)
        marker.title = "My Location"
       // marker.map = mapView
        
       let camera = GMSCameraPosition.camera(withLatitude: (location?.coordinate.latitude)!, longitude:(location?.coordinate.longitude)!, zoom:16)
        mapView.animate(to: camera)
        
        
    
        // print(manager.location!.horizontalAccuracy)
        //Finally stop updating location otherwise it will come again and again in this delegate
        self.locationManager.stopUpdatingLocation()
       // showToast(message: "Accuracy is \(manager.location!.horizontalAccuracy)")
    }
    
    //when map is tap hide the menu or keyboard
    func movemarker(lat:Double,lng:Double,username:String){
        
        
        // Creates a marker in the center of the map.
        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: lat, longitude: lng)
        marker.title = username
        marker.snippet = username
        marker.map = mapView
        
        self.mapView.selectedMarker = marker
        
        
        
        let camera = GMSCameraPosition.camera(withLatitude: (lat), longitude:(lng), zoom:16)
        mapView.animate(to: camera)
        
        
        
    }
    
    //function to toggle map
    
    func togglemap(){
        mapView.isUserInteractionEnabled = true
        
        if(mapshowing == true){
            
            drawtoggle.isEnabled = true
            reloadbutton.isEnabled = true
            
            //check if the path not null
            
            if(pathdraw.count()>0){
                drawtoggle.title = "Save"
                drawtoggle.image = #imageLiteral(resourceName: "saveicon")
            }
            else{
                drawtoggle.title = "Draw"
                drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
            }
            

            
            mapshowing = !mapshowing
            
            
        }else{
            
            drawtoggle.isEnabled = false
            reloadbutton.isEnabled = false
            

            mapshowing = !mapshowing
            
            
        }
    }
    
    
    
    func mapView(_ mapView: GMSMapView, didTapAt coordinate: CLLocationCoordinate2D) {
        print("You tapped at \(coordinate.latitude), \(coordinate.longitude)")
        
        self.view.endEditing(true) //This will hide the keyboard
        
        //hide the menu
        if(menushowing){
            leadingconstraint.constant = -140
            
            UIView.animate(withDuration: 0.3, animations: {
                self.view.layoutIfNeeded()
            })
            
            menushowing = !menushowing
            
        }
        
    }
    
    
    
    //action on the long tap on google maps
    func mapView(_ mapView: GMSMapView, didLongPressAt coordinate: CLLocationCoordinate2D) {
        
        //if in draggable marker mode this will not enable
        if(dragstart == false){
            
            //make marker on long tap on the map
            
            let marker1 = GMSMarker()
            marker1.position = CLLocationCoordinate2D(latitude: coordinate.latitude, longitude: coordinate.longitude)
            marker1.title = "tagging"
            marker1.snippet = "tagging"
            // marker1.map = mapView
            
            // get a reference to the view controller for the popover
            let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popovercamera") as! cameraviewcontroller
            
            // set the presentation style...this to make popover not cover all the area
            popController.modalPresentationStyle = UIModalPresentationStyle.popover
            
            // set up the popover presentation controller
            popController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.up
            popController.popoverPresentationController?.delegate = self
      
            //button
            popController.popoverPresentationController?.barButtonItem = drawanchor
          
            popController.delegate = self
            popController.marker1 = marker1
            
            // present the popover
            self.present(popController, animated: true, completion: nil)
            
        }
    }
    
    func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
        return .none
    }
    
    
    
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        return false
    }
    
    func mapView(_ mapView: GMSMapView, markerInfoWindow marker: GMSMarker) -> UIView? {
        
        let customInfoWindow = Bundle.main.loadNibNamed("infowindowsmaps", owner: self, options: nil)?[0] as! infowindowsmaps
        
        let storage = FIRStorage.storage()
        let storageRef = storage.reference()
        // Create a reference to the file you want to download
        let createdby:String = marker.userData as! String
        let islandRef = storageRef.child("remote_camera/"+createdby+"/"+marker.title!+".jpg")
        
        let imageviewe =  customInfoWindow.imageview
        
        imageviewe?.autoresizingMask = UIViewAutoresizing(rawValue: UIViewAutoresizing.RawValue(UInt8(UIViewAutoresizing.flexibleBottomMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleHeight.rawValue) | UInt8(UIViewAutoresizing.flexibleRightMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleLeftMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleTopMargin.rawValue) | UInt8(UIViewAutoresizing.flexibleWidth.rawValue)))
        imageviewe?.contentMode = UIViewContentMode.scaleAspectFit
        
        //using firebase UI to view image directly from firebase referrence ui
        imageviewe?.sd_setImage(with: islandRef)
        
        
        customInfoWindow.titleimage.text = marker.title
        
        customInfoWindow.latitude.text = "\(marker.position.latitude)"
        customInfoWindow.longitude.text = "\(marker.position.longitude)"
        
        
        return customInfoWindow
        
    }
    
    
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return arraydatasources.count    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellmenu", for: indexPath) as! cellmenu
        
        
        cell.menuitem.text = arraydatasources[indexPath.item]
        // print(cell.menuitem.text ?? "")
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        menutableview.layer.opacity = 5
        menutableview.layer.shadowRadius = 10
        
        let textselected = arraydatasources[indexPath.item]
        
       
        
        
        if(textselected=="Map Type"){
            
            if(maptype == true){
                
                mapView.mapType = GMSMapViewType(rawValue: 4)
                maptype = !maptype
            }else
            {
                mapView.mapType = GMSMapViewType(rawValue: 1)
                maptype = !maptype
                
            }
            
        }
        
        if(textselected == "Sign Out"){
        let firebaseAuth = FIRAuth.auth()
        do {
            try firebaseAuth?.signOut()
            
            //logout then go to login page
            
            
            
            let storyboard = UIStoryboard(name: "login", bundle: nil)
            
            let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "login") as! logincontroller
            
            self.navigationController?.pushViewController(initialViewController2, animated: true)
            
            
        } catch let signOutError as NSError {
            print ("Error signing out: %@", signOutError)
        }
        }
        
        
        if(textselected == "Boundry List"){
            
            if(markerDictboundry.count > 0){
            
            // get a reference to the view controller for the popover
            let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "searchlist") as! searchlist
            
            popController.delegate = self
            // set the presentation style...this to make popover not cover all the area
            popController.modalPresentationStyle = UIModalPresentationStyle.popover
            
            // set up the popover presentation controller
            popController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.up
            popController.popoverPresentationController?.delegate = self
            
            //button
            popController.popoverPresentationController?.barButtonItem = searchbtn
            
            popController.markerDict = markerDictboundry
            
            
            // present the popover
            self.present(popController, animated: true, completion: nil)
            
           }
        }
        
    }
    
    
    private func supportedInterfaceOrientations() -> UIInterfaceOrientationMask {
        return UIInterfaceOrientationMask.portrait
    }
    private func shouldAutorotate() -> Bool {
        return false
    }
    
    
    //for textfiled on action sheet hide keyboard on second touch
    var touchtext = true
    func myTargetFunction(textField: UITextField) {
        print("touch")
        // user touch field
        if(touchtext == true){
            
            touchtext = !touchtext
        }
        else{
            
            textField.resignFirstResponder()
            touchtext = !touchtext
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
    
    
    //when button tap draw start this will to start the drawing
    
    @IBAction func tapdrawbutton(_ sender: Any) {
    
        
        if(self.drawstart == false){
            self.mapView.isUserInteractionEnabled = false
         
            drawanchor.image = #imageLiteral(resourceName: "penicon")
            
            self.drawstart = !drawstart
        }
        else{
            
            
            
            
            //popup action sheet to save the polyline
            if(pathdraw.count()>0){
                
                penbutton.isHidden = true
                self.mapView.isUserInteractionEnabled = true
                self.drawstart = !drawstart
                
                let actionSheetController = UIAlertController(title: "Save Drawing", message: "Enter Name", preferredStyle: .alert)
                
                //customview to action sheet cntroller
                let margin:CGFloat = 10.0
                let rect = CGRect(x: margin, y: margin, width: actionSheetController.view.bounds.size.width - margin * 4.0, height: 60)
                //let customView = UIView(frame: rect)
                
                
//                self.textField = UITextField(frame:rect);
//
//
//                textField.clipsToBounds = true
//                textField.layer.cornerRadius = 5
//                textField.backgroundColor = .gray
//                actionSheetController.view.addSubview(textField)
                
            
                actionSheetController.addTextField { (textField: UITextField) in
                    textField.keyboardAppearance = .dark
                    textField.keyboardType = .default
                    textField.autocorrectionType = .default
                    textField.placeholder = "Type something here"
                    textField.clearButtonMode = .whileEditing
                    
                      textField.addTarget(self, action: #selector(self.myTargetFunction(textField:)), for: UIControlEvents.touchDown)
                }
                
                let cancelActionButton = UIAlertAction(title: "Cancel", style: .cancel) { action -> Void in
                    print("Cancel")
                   
                    self.firebaselistenr()
                    self.pathdraw.removeAllCoordinates()
                    self.polylinecoordinates.removeAll()
                    self.drawtoggle.title = "Draw"
                    self.drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
                }
                actionSheetController.addAction(cancelActionButton)
                
                let saveActionButton = UIAlertAction(title: "Save", style: .default) { action -> Void in
                    
                    
                    let polyname: String = actionSheetController.textFields![0].text!
                    
                    if(polyname != ""){
                    self.savepolyline(polycoordinates: self.polylinecoordinates, polyname: polyname)
                    
                    self.pathdraw.removeAllCoordinates()
                    self.polylinecoordinates.removeAll()
                    self.drawtoggle.title = "Draw"
                    self.drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
                    }else{
                        
                        self.firebaselistenr()
                        self.pathdraw.removeAllCoordinates()
                        self.polylinecoordinates.removeAll()
                        self.drawtoggle.title = "Draw"
                        self.drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
                    }
                }
                actionSheetController.addAction(saveActionButton)
                
               
                self.present(actionSheetController, animated: true, completion: nil)
                
                
                
                
            }
            else
            {
                
                
                drawanchor.image = #imageLiteral(resourceName: "pencilicon")
                self.mapView.isUserInteractionEnabled = true
                self.drawstart = !drawstart
            }
        }
        
    }
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first!
        
        
        let touchPoint = touch.location(in: mapView)
        
        
        let point = mapView.projection.coordinate(for: touchPoint)
        //print(point)
        
        pathdraw.add(point)
        polylinecoordinates.append(point)
        
        
        
        
        
    }
    
    
    
    
    func mapView(_ mapView: GMSMapView, didDrag marker: GMSMarker) {
        dragstart = true
    }
    
    
    
    func mapView(_ mapView: GMSMapView, didEndDragging marker: GMSMarker) {
        print(marker.position)
        
        dragstart = false
        
        let referencephotomarkerinitial = FIRDatabase.database().reference().child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!)
        referencephotomarkerinitial.observeSingleEvent(of: .value, with: { (snapshot) in
            
            for rest2 in snapshot.children.allObjects as! [FIRDataSnapshot] {
              
                
              
                    
                    let markername:String = rest2.key
                    
                    
                    if(markername == marker.title){
                      
                    print(markername)
                    for rest3 in rest2.children.allObjects as! [FIRDataSnapshot] {
                        
                        
                        if(rest3.key == "createdby"){
                            
                            let createdby:String = rest3.value as! String
                            if createdby == FIRAuth.auth()?.currentUser?.email{
                                
                                print(createdby)
                                
                                            //save the marker location to firebase storage
                                            var ref: FIRDatabaseReference!
                                
                                            ref = FIRDatabase.database().reference()
                                
                                
                                            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child("lat").removeValue()
                                ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child("lng").removeValue()
                                
                                            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child("lat").setValue( marker.position.latitude)
                                ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).child("lng").setValue( marker.position.longitude)
                                
//                                ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).setValue(["lat": marker.position.latitude,"lng":marker.position.longitude])
                                
                                
                                
                            }
                            
                         }
                        
                        }
                        
                        
                    
                    
                    
                  
                }
                
                
            }
            
            
            
            
            
        }) { (nil) in
            print("error firebase listner")
        }
        
//        if marker.title?.range(of:"Cabinet_") != nil || marker.title?.range(of:"ManHole_") != nil || marker.title?.range(of:"DP_") != nil {
//            //save the marker location to firebase storage
//            var ref: FIRDatabaseReference!
//
//            ref = FIRDatabase.database().reference()
//
//
//            ref.child("photomarkeridraw").child((FIRAuth.auth()?.currentUser?.uid)!).child(marker.title!).removeValue()
//
//            ref.child("photomarker").child(marker.title!).setValue(["lat": marker.position.latitude,"lng":marker.position.longitude])
//        }
//
        
        
        
    }
    
    //delete the tap marker
    
    
    @IBAction func deletmarker(_ sender: Any) {
        var ref: FIRDatabaseReference!
        
        ref = FIRDatabase.database().reference()
        
        
        ref.child("photomarker").child((self.currentarker?.title!)!).removeValue()
        ref.child("currentphotomarker").child((self.currentarker?.title!)!).removeValue()
        
        self.currentarker?.map = nil
        
        
        self.dragstart = !dragstart
        
        
        self.mapView.settings.scrollGestures = true
       
        
        
    }
    
    
    func mapView(_ mapView: GMSMapView, didTapInfoWindowOf marker: GMSMarker) {
        
        // get a reference to the view controller for the popover
        let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "markerdetail") as! markerdetail
        popController.delegate = self
        // set the presentation style...this to make popover not cover all the area
        popController.modalPresentationStyle = UIModalPresentationStyle.popover
        
        // set up the popover presentation controller
        popController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.up
        popController.popoverPresentationController?.delegate = self
        
        //button
        popController.popoverPresentationController?.barButtonItem = reloadbutton
        
        
        popController.marker1 = marker
        
        // present the popover
        self.present(popController, animated: true, completion: nil)
    }
    
    
    //this touchmove will be enable when user gesture googlemaps is set to false
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        if(pathdraw.count()>0){
            drawtoggle.title = "Save"
             drawtoggle.image = #imageLiteral(resourceName: "saveicon")
        }
        else{
            drawtoggle.title = "Draw"
            drawtoggle.image = #imageLiteral(resourceName: "pencilicon")
        }
        
        let touch = touches.first!
        
        
        let touchPoint = touch.location(in: mapView)
        
        
        let point = mapView.projection.coordinate(for: touchPoint)
        //print(point)
        
        pathdraw.add(point)
        polylinecoordinates.append(point)
        
        polyline2 = GMSPolyline(path: pathdraw)
        polyline2.map = self.mapView
        polyline2.strokeColor = .green
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        markerDictboundry.removeAll()
        
        for points in markerDict {
            
            if(GMSGeometryContainsLocation(points.position, pathdraw, true)){
                
                markerDictboundry.append(points)
            }
            
        }
        
        //pathdraw.removeAllCoordinates()
    }
    //this fucntion will save polylinecoordinates to firebase database
    func savepolyline(polycoordinates:[CLLocationCoordinate2D],polyname:String){
        
        print(polyname)
        let userID : String = (FIRAuth.auth()?.currentUser?.uid)!
        
        var latlngstrarray = [String]()
        
        var distance:Float = 0.0
        
        
        
        for var i in (0..<polycoordinates.count-1)
        {
            var loca1: CLLocation
            var loca2: CLLocation
            
            loca1 = CLLocation(latitude: polycoordinates[i].latitude, longitude: polycoordinates[i].longitude)
            loca2 = CLLocation(latitude: polycoordinates[i+1].latitude, longitude: polycoordinates[i+1].longitude)
            
            distance += Float(loca2.distance(from: loca1))
            print(distance)
            print(i)
            i+=1
            
            
        }
        
        
        var distancestr: String = String(format:"%2f", distance)
        let newdistancestr = distancestr.replacingOccurrences(of: ".", with: "_", options: .literal, range: nil)
        
        let ref = FIRDatabase.database().reference()
        for coords in polycoordinates {
            
            let lat : Double = coords.latitude
            let lng : Double = coords.longitude
            let latstr : String = String(format:"%f", lat)
            let lngstr : String = String(format:"%f", lng)
            
            let latlng : String = "\(latstr),\(lngstr)"
            latlngstrarray.append(latlng)
            
            
            
            
            //print(latlng)
            ref.child("sketch").child(userID).child("\(polyname):\(newdistancestr)m").child("Lat").setValue(latlngstrarray)
            // print(coords)
            
            ref.child("currentsketch").removeValue()
            ref.child("currentsketch").child(userID).child("\(polyname):\(newdistancestr)").child("Lat").setValue(latlngstrarray)
            
            
            
            
        }
           ref.child("sketch").child(userID).child("\(polyname):\(newdistancestr)m").child("createdby").setValue(FIRAuth.auth()?.currentUser?.email)
        
    }
    
    
    //uipickerview
  
    
    
    //when speakbutton press
    
   
    //action to open menu
    @IBAction func openmenuitem(_ sender: UIBarButtonItem) {
        
        
        
        
        if let window = UIApplication.shared.keyWindow{
            
            blackview.frame = window.frame
            blackview.backgroundColor = UIColor(white: 0, alpha: 0.5)
            
        }
        
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
    
    @IBAction func signout(_ sender: Any) {
        
        let marker1 = GMSMarker()
        marker1.position.latitude = (mapView.myLocation?.coordinate.latitude)!
         marker1.position.longitude = (mapView.myLocation?.coordinate.longitude)!
        
      
        // marker1.map = mapView
        
        // get a reference to the view controller for the popover
        let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "popovercamera") as! cameraviewcontroller
        
        // set the presentation style...this to make popover not cover all the area
        popController.modalPresentationStyle = UIModalPresentationStyle.popover
        
        // set up the popover presentation controller
        popController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.up
        popController.popoverPresentationController?.delegate = self
        
        //button
        popController.popoverPresentationController?.barButtonItem = signout
        
        popController.delegate = self
        popController.marker1 = marker1
        
        // present the popover
        self.present(popController, animated: true, completion: nil)
        
    }
    
    
    
    @IBAction func searchpopover(_ sender: Any) {
        
        // get a reference to the view controller for the popover
        let popController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "searchlist") as! searchlist
        
        popController.delegate = self
        // set the presentation style...this to make popover not cover all the area
        popController.modalPresentationStyle = UIModalPresentationStyle.popover
        
        // set up the popover presentation controller
        popController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection.up
        popController.popoverPresentationController?.delegate = self
        
        //button
        popController.popoverPresentationController?.barButtonItem = searchbtn
        
        popController.markerDict = markerDict
       
        
        // present the popover
        self.present(popController, animated: true, completion: nil)
    }
    
    
    
    
    //function when tagging image complete...trigger thru protocol from popover camereview
    func addmarkertomaps(marker: GMSMarker) {
       
     firebaselistenr()
    }
    
}
extension UIViewController {
    
    func showToast(message : String) {
        
        let toastLabel = UILabel(frame: CGRect(x: self.view.frame.size.width/2 - 75, y: self.view.frame.size.height-100, width: 150, height: 35))
        toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        toastLabel.textColor = UIColor.white
        toastLabel.textAlignment = .center;
        toastLabel.font = UIFont(name: "Montserrat-Light", size: 12.0)
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 10;
        toastLabel.clipsToBounds  =  true
        self.view.addSubview(toastLabel)
        UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
            toastLabel.alpha = 0.0
        }, completion: {(isCompleted) in
            toastLabel.removeFromSuperview()
        })
    } }
