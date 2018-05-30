//
//  mapsviews.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 18/08/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces
import FirebaseDatabase


class mapsviews: UIViewController,GMSMapViewDelegate,CLLocationManagerDelegate {

    @IBOutlet weak var mapView: GMSMapView!
    let reference = FIRDatabase.database().reference()

   
    var locationManager = CLLocationManager()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.isMyLocationEnabled = true
        mapView.delegate = self
        
        //Location Manager code to fetch current location
        self.locationManager.delegate = self
        self.locationManager.startUpdatingLocation()
        
        let currentuser = reference.observe(.value, with: { (snapshot) in
            print(snapshot.value ?? "no user")
            
            let value = snapshot.value as? NSDictionary
            let username = value?["currentuser"] as? String ?? ""
            let latitude = value?[username+"Lat"] as? Double ?? 0
            let longitude = value?[username+"Lng"] as? Double ?? 0
            
            
            print(username)
            print(latitude)
            print(longitude)
            
              self.movemarker(lat: latitude, lng: longitude, username: username)
            
            
        }) { (nil) in
            print("error firebase listner")
        }

    }
    
    
    //Location Manager delegates
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        let location = locations.last
        
        
        let marker = GMSMarker(position: (location?.coordinate)!)
        marker.title = "My Location"
        marker.map = mapView
        
        let camera = GMSCameraPosition.camera(withLatitude: (location?.coordinate.latitude)!, longitude:(location?.coordinate.longitude)!, zoom:14)
        mapView.animate(to: camera)
        
        //Finally stop updating location otherwise it will come again and again in this delegate
        self.locationManager.stopUpdatingLocation()
        
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

        
    
    }

    

   

