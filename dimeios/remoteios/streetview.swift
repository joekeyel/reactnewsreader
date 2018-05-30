//
//  streetview.swift
//  idraw
//
//  Created by Hasanul Isyraf on 11/01/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import GooglePlaces
import GoogleMaps

class streetview: UIViewController {
    
    var marker1 = GMSMarker()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let panoView = GMSPanoramaView(frame: .zero)
        self.view = panoView
        
        
        panoView.moveNearCoordinate(CLLocationCoordinate2D(latitude: marker1.position.latitude, longitude: marker1.position.longitude))
        marker1.panoramaView = panoView
        
       
      
    }

    
}
