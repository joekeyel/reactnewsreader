//
//  logincontroller.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 28/08/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import FirebaseAuth
import Firebase

class logincontroller: UIViewController {

    @IBOutlet weak var passwordfield: UITextField!
    @IBOutlet weak var usernamefield: UITextField!
    
    @IBOutlet weak var displaystatus: UILabel!
    
    @IBOutlet weak var toregisternewuser: UIButton!
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
       
      usernamefield.leftViewMode = UITextFieldViewMode.always
       usernamefield.leftView = UIImageView(image: #imageLiteral(resourceName: "usericon-1"))
        
        passwordfield.leftViewMode = UITextFieldViewMode.always
        passwordfield.leftView = UIImageView(image: #imageLiteral(resourceName: "password"))
        
        
        //check info of user login from previous login from server
        
        //getuser()
       
       
            if FIRAuth.auth()?.currentUser != nil {
                
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                
                let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "register") as! register
                
                self.navigationController?.pushViewController(initialViewController2, animated: true)
                
            } else {
                // do nothing
                // go to login controller
            }
        
        
        
        
          }

    @IBAction func SignInAction(_ sender: Any) {
        
        if let username = usernamefield.text, !username.isEmpty,
            let password = passwordfield.text, !password.isEmpty {
            
            activitiyindicator.center = self.view.center
            activitiyindicator.hidesWhenStopped = true
            activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
            view.addSubview(activitiyindicator)
            activitiyindicator.startAnimating()
            
            
            
              authenticate()
            
           
            
           
            
            
        }
        else{
            
            let alertController2 = UIAlertController(title: "Alert", message: "Pls insert value in text field", preferredStyle: UIAlertControllerStyle.alert)
            let okaction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                self.activitiyindicator.stopAnimating()
                
                
            }
           
            
            alertController2.addAction(okaction)
            self.present(alertController2, animated: true, completion: nil)
            
            
        }

       
        
    }
    
    func  authenticate(){
        
         self.activitiyindicator.startAnimating()
        
       // let uuid = UIDevice.current.identifierForVendor!.uuidString
        let username = self.usernamefield.text ?? ""
        let password = self.passwordfield.text ?? ""
        
        
        //if get token sent to firebase authentication
        
        FIRAuth.auth()?.signIn(withEmail: username, password: password) { (user, error) in
            if (error == nil)
            {
                
                // print(user?.uid ?? "cannot get user")
                
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                
                let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "register") as! register
                
                self.navigationController?.pushViewController(initialViewController2, animated: true)
                
            }
            else{
                
                let alertController2 = UIAlertController(title: "Cannot Login", message: error.debugDescription, preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
            }
            
        }
        
        
        self.activitiyindicator.stopAnimating()
        
       
        
        
    }
   
    
    @IBAction func gotoregister(_ sender: Any) {
        let storyboard = UIStoryboard(name: "login", bundle: nil)
        
        let initialViewController2 = storyboard.instantiateViewController(withIdentifier: "registeruser") as! registercontroller
        
        self.navigationController?.pushViewController(initialViewController2, animated: true)
        
    }
    
    
    @IBAction func resetpassword(_ sender: Any) {
        activitiyindicator.startAnimating()
        
        let emailreset = usernamefield.text
        
        if(emailreset != ""){
        
        FIRAuth.auth()?.sendPasswordReset(withEmail: emailreset!)  { (error) in
            if (error == nil)
            {
                
                // print(user?.uid ?? "cannot get user")
                
                let alertController2 = UIAlertController(title: "Reset", message: "Email has been sent to "+emailreset!, preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
            }
            else{
                
                let alertController2 = UIAlertController(title: "Reset", message: "Error Reset Password", preferredStyle: UIAlertControllerStyle.alert)
                let okaction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                    self.activitiyindicator.stopAnimating()
                    
                }
                
                
                alertController2.addAction(okaction)
                self.present(alertController2, animated: true, completion: nil)
                
            }
        }
        }else{
            
            
            let alertController2 = UIAlertController(title: "Alert", message: "Pls enter email address", preferredStyle: UIAlertControllerStyle.alert)
            let okaction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                self.activitiyindicator.stopAnimating()
                
            }
            
            
            alertController2.addAction(okaction)
            self.present(alertController2, animated: true, completion: nil)
            
        }
        
}
    

    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    
   
}
