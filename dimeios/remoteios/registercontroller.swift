//
//  registercontroller.swift
//  idraw
//
//  Created by Hasanul Isyraf on 14/01/2018.
//  Copyright © 2018 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Firebase

class registercontroller: UIViewController {

    
    @IBOutlet weak var emailtext: UITextField!
    
  
   
    @IBOutlet weak var passwordtext: UITextField!
    
      var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
       
        emailtext.leftViewMode = UITextFieldViewMode.always
        emailtext.leftView = UIImageView(image: #imageLiteral(resourceName: "usericon-1"))
        
        passwordtext.leftViewMode = UITextFieldViewMode.always
        passwordtext.leftView = UIImageView(image: #imageLiteral(resourceName: "password"))
        
       
        
        
    }

    @IBAction func gotologinpage(_ sender: Any) {
        
        let storyboard = UIStoryboard(name: "login", bundle: nil)
        
        let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "login") as! logincontroller
        
        self.navigationController?.pushViewController(initialViewController2, animated: true)
    }
    
    @IBAction func registerfirebase(_ sender: Any) {
        
        let email : String = self.emailtext.text!
        let password : String = "1234567890"
        
        FIRAuth.auth()?.createUser(withEmail: email, password: password){ (user, error) in
            print(error as Any)
            if (error != nil){
                
                
                print(error.debugDescription)
                let errormsg : String = error.debugDescription.description
                
                let alertController2 = UIAlertController(title: "Error", message: errormsg, preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
                
            }
            else
            {
                let firebaseAuth = FIRAuth.auth()
                do {
                    try firebaseAuth?.signOut()
                    
                    //logout then go to login page
                    
                    
                    
                    self.senresetemail(email: email)
                    
                    
                    
                    
                } catch let signOutError as NSError {
                    print ("Error signing out: %@", signOutError)
                }
            }
                
            
                
                
              
                
            
        }
    }
    
    
    func senresetemail(email:String){
        
        FIRAuth.auth()?.sendPasswordReset(withEmail: email)  { (error) in
            if (error == nil)
            {
                
                // print(user?.uid ?? "cannot get user")
                
                let alertController2 = UIAlertController(title: "Email Activation", message: "Email has been sent to "+email, preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
                let storyboard = UIStoryboard(name: "login", bundle: nil)
                
                let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "login") as! logincontroller
                
                self.navigationController?.pushViewController(initialViewController2, animated: true)
                
            }
            else{
                
                
                let alertController2 = UIAlertController(title: "Reset", message: error.debugDescription, preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
            }
        }
        
    }
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    
    
    
}
