//
//  mapviewgoogle.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 21/06/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GoogleMaps
import GooglePlaces

class mapviewgoogle: UIView {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        func loadView() {
            // Create a GMSCameraPosition that tells the map to display the
            // coordinate -33.86,151.20 at zoom level 6.
            let camera = GMSCameraPosition.camera(withLatitude: -33.86, longitude: 151.20, zoom: 6.0)
            let mapView = GMSMapView.map(withFrame: CGRect.zero, camera: camera)
            view = mapView
            
            // Creates a marker in the center of the map.
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: -33.86, longitude: 151.20)
            marker.title = "Sydney"
            marker.snippet = "Australia"
            marker.map = mapView
        }
        
        
        
    }


}
