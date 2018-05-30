//
//  updatestatusview.swift
//  remoteios
//
//  Created by Hasanul Isyraf on 28/07/2017.
//  Copyright © 2017 Hasanul Isyraf. All rights reserved.
//

import UIKit
import Speech
import AVFoundation

class updatestatusview: UIViewController,UIPickerViewDelegate,UIPickerViewDataSource,SFSpeechRecognizerDelegate {
    @IBOutlet weak var callbutton1: UIButton!
    
   
    @IBOutlet weak var callbutton2: UIButton!
   
    @IBOutlet weak var bgview2: UIView!
    @IBOutlet weak var contactnumber: UILabel!
    @IBOutlet weak var referencenumber: UILabel!
    @IBOutlet weak var servicenumber: UILabel!
    
    @IBOutlet weak var button: UIButton!
    @IBOutlet weak var selectstatus: UIPickerView!
    @IBOutlet weak var bgview: UIView!
    var ttinfo = listttobject()
    
    @IBOutlet weak var remark: UITextView!
    
    @IBOutlet weak var speakbutton: UIButton!
    @IBOutlet weak var customername: UILabel!
    let muteForPickerData = ["VOICE OK AT DP","VOICE OK AT CABINET","VOICE NOT OK","PORT UP VOICE OK AT DP","PORT UP VOICE OK AT CABINET","PORT UP SPEED ISSUE"]
    
    var username = ""
    var statusmdf = ""
    var poststr = ""
    var activitiyindicator:UIActivityIndicatorView = UIActivityIndicatorView()
    
    // for speech recognizer
    let audioEngine = AVAudioEngine()
    let speechRecognizer : SFSpeechRecognizer =  SFSpeechRecognizer(locale:Locale.init(identifier:"ms-MY"))!
    let request = SFSpeechAudioBufferRecognitionRequest()
    var reconitionTask: SFSpeechRecognitionTask?
    var isRecording = false
    
    //voice synthesizer
    let synth = AVSpeechSynthesizer()
    var myUtterance = AVSpeechUtterance(string: "")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //to hide the keyboard when tap on the background
        self.hideKeyboardWhenTappedAround()
        
        bgview.layer.cornerRadius = 10
        bgview2.layer.cornerRadius = 10
        button.layer.cornerRadius = 10
        speakbutton.layer.cornerRadius = 10
        
      
       
        
        remark.layer.cornerRadius = 10
        
        customername.text = ttinfo.customername
        referencenumber.text = ttinfo.referencenumber
        servicenumber.text = ttinfo.servicenumber
        
        remark.text = ttinfo.remark
        contactnumber.text = ttinfo.contactnumber
        
        //set the default value of the uipickerview statusmdf
        
        if let i = muteForPickerData.index(where: { $0 == ttinfo.statusmdf }) {
             selectstatus.selectRow(i, inComponent: 0, animated: true)
             statusmdf = muteForPickerData[i]
        }
       
        // to query user infoo base on device unique id
        getuser()
        
     

        // to setup the label being able to recognice on tapping
        
        
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(updatestatusview.tapFunction))
         let tap2 = UITapGestureRecognizer(target: self, action: #selector(updatestatusview.tapFunction2))
       
        servicenumber.isUserInteractionEnabled = true
        servicenumber.addGestureRecognizer(tap)
        
        
        referencenumber.isUserInteractionEnabled = true
        referencenumber.addGestureRecognizer(tap2)
        
        
        //check permission for speech recognition
        
        self.requestSpeechAuthorization()
        
        //set audio session to default speaker
        let audioSession = AVAudioSession.sharedInstance()
        do{
        try audioSession.setCategory(AVAudioSessionCategoryPlayAndRecord, with: AVAudioSessionCategoryOptions.defaultToSpeaker)}
        catch{
        
        print("error")
        }
        
        
        //checking aging tt
        
        
        
    }
    
    //function to calculate aging from created date of TT
    func countaging()->Int{
        
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy-MM-dd HH:mm:ss"
        let dateend = dfmatter.date(from: ttinfo.createddate!)
       
        
        let dateRangeStart = Date()
        
        let components = Calendar.current.dateComponents([.hour, .minute], from: dateend!, to: dateRangeStart)
        
       return components.hour!
        
//        print("\(String(describing: remarktext)) Aging is \(components.hour ?? 0) Hours and \(components.minute ?? 0) Minute")
        
    }
    func tapFunction(sender:UITapGestureRecognizer) {
        
         let servicenumberstr: String = servicenumber.text!
       
       // print(servicenumberstr)
        radiuscheck(login: servicenumberstr)
        
        //status indicator
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
    }
    func tapFunction2(sender:UITapGestureRecognizer) {
        
        let servicenumberstr: String = referencenumber.text!
        
        print(servicenumberstr)
        radiuscheck(login: servicenumberstr)
        
        //status indicator
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
    }
    
    //function speech recognizer
    
    func cancelRecording() {
        audioEngine.stop()
        if let node = audioEngine.inputNode {
            node.removeTap(onBus: 0)
        }
        reconitionTask?.cancel()
    }
    
    func recordAndRecognizeSpeech() {
        guard let node = audioEngine.inputNode else { return }
        node.removeTap(onBus: 0)
        let recordingFormat = node.outputFormat(forBus: 0)
        node.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, _ in
            self.request.append(buffer)
        }
        audioEngine.prepare()
        do {
            try audioEngine.start()
        } catch {
            self.sendAlert(message: "There has been an audio engine error.")
            return print(error)
        }
        guard let myRecognizer = SFSpeechRecognizer() else {
            self.sendAlert(message: "Speech recognition is not supported for your current locale.")
            return
        }
        if !myRecognizer.isAvailable {
            self.sendAlert(message: "Speech recognition is not currently available. Check back at a later time.")
            // Recognizer is not available right now
            return
        }
        reconitionTask = speechRecognizer.recognitionTask(with: request, resultHandler: { result, error in
            if let result = result {
                
                let bestString = result.bestTranscription.formattedString
                let currentremark = self.remark.text
                self.remark.text = "\(currentremark ?? "") \(bestString)"
                
                var lastString: String = ""
                for segment in result.bestTranscription.segments {
                    let indexTo = bestString.index(bestString.startIndex, offsetBy: segment.substringRange.location)
                    lastString = bestString.substring(from: indexTo)
                }
                self.checkForColorsSaid(resultString: lastString)
            } else if let error = error {
                self.sendAlert(message: "Stop")
                print(error)
            }
        })
    }
    
    //MARK: - Check Authorization Status
    
    func sendAlert(message: String) {
        let alert = UIAlertController(title: "Speech Recognizer", message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    func requestSpeechAuthorization() {
        SFSpeechRecognizer.requestAuthorization { authStatus in
            OperationQueue.main.addOperation {
                switch authStatus {
                case .authorized:
                    self.speakbutton.isEnabled = true
                case .denied:
                    self.speakbutton.isEnabled = false
                    self.remark.text = "User denied access to speech recognition"
                case .restricted:
                    self.speakbutton.isEnabled = false
                    self.remark.text = "Speech recognition restricted on this device"
                case .notDetermined:
                    self.speakbutton.isEnabled = false
                    self.remark.text = "Speech recognition not yet authorized"
                }
            }
        }
    }
    
    func checkForColorsSaid(resultString: String) {
        switch resultString {
       
            
        case "Jadual":
           
            myUtterance = AVSpeechUtterance(string: "Showing Schedule")
            myUtterance.rate = 0.3
            myUtterance.volume = 1
            synth.speak(myUtterance)
            
            audioEngine.stop()
            reconitionTask?.cancel()
            isRecording = false
            speakbutton.backgroundColor = UIColor(white: 1, alpha: 0.0)
           
            
            let webVc = UIStoryboard(name:"Main",bundle:nil).instantiateViewController(withIdentifier: "scheduleview") as! scheduleviewcontroller
            
            
            navigationController?.pushViewController(webVc, animated: true)
          
            
      
        default: break
        }
    }
    
    func radiuscheck(login:String){
        
        let parameters = ["login" : login].map { "\($0)=\(String(describing: $1 ))" }
        
        _ = UIDevice.current.identifierForVendor!.uuidString
        var request = URLRequest(url: URL(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/proxy_radius.php?login="+login)! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
         request.httpBody = postString.data(using: String.Encoding.utf8)
        
        
        let task2 = URLSession.shared.dataTask(with: request){(data,response,error)  in
            
            if let data = data {
                
                var sessionresult =  [String:AnyObject]()
                var stationmodelresp : sessionresponse = sessionresponse()
                
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [[String : Any]]
                    
                    
                    var remarktext : String = self.remark.text
                    var result: String = ""
                    
                // print(json[0])
                        for json in json {
                          
                            // print(json)
                            
                          
                                if let session = json["sessionInfo"] as? Dictionary<String, AnyObject> {
                                   
                                    if let station  = session["callingStationID"] as? String{
                                        print(station )
                                    
                                        if (station.range(of:"#streamyx#") != nil) {
                                           
                                            result = "\(login) session test success, result: \(station) \(remarktext)"
                                       
                                            
                                          break
                                           
                                        }
                                        if (station.range(of:"#tmnet#") != nil) {
                                            
                                            result = "\(login) session test success, result: \(station) \(remarktext)"
                                            
                                            
                                            break
                                            
                                        }
                                       
                                      
                                    
                                    
                                    }else{
                                        
                                        if (!remarktext.isEmpty){
                                            result = "\(remarktext),   \(login),  test session OFFLINE"}else
                                        {
                                            
                                             result = "  \(login) test session OFFLINE"
                                        }
                                      
                                    }
                                    
                                   
                                  
                                    
                                }
                            
                            
                            
                                
                            }
                           
                    
                            
                    DispatchQueue.main.async {
                        
                       
                            
                       // _ = self.remark.text
                            self.remark.text = result
                        self.activitiyindicator.stopAnimating()
                     
                     
                    }
                        
                    }
                    
                    
                
                    
                    
                catch let error as NSError {
                    print(error.localizedDescription)
                }
                
            }
                
                
            else if let error = error {
                print(error.localizedDescription)
            }
            
            
        }
        
        task2.resume()
        
        
        
    }
    
    func getuser(){
        
        let uuid = UIDevice.current.identifierForVendor!.uuidString
        var request = URLRequest(url: URL(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/listttmobile_summary_test.php?uuid="+uuid)! as URL)
        request.httpMethod = "POST"
        
        
        
        let task2 = URLSession.shared.dataTask(with: request){(data,response,error)  in
            
            if let data = data {
                
                
                
                do{
                    
                    let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! [String : AnyObject]
                    
                    if  let name  = json["name"] as? String,let groupand  = json["group"] as? String{
                        
                        self.username = name+" "+groupand
                        print(name)
                        
                    }
                    
                    
                }
                    
                    
                catch let error as NSError {
                    print(error.localizedDescription)
                }
                
            }
                
                
            else if let error = error {
                print(error.localizedDescription)
            }
            
            
        }
        
        task2.resume()
        
        
        
    }
    
    func updatestatus(){
        
        let remark = self.remark.text
          let uuid = UIDevice.current.identifierForVendor!.uuidString
        
        
        let parameters = ["uuid" : uuid,
                          "MM_Username" :  username,
                          "ttno" : ttinfo.ttno!,
                          "servicenumber" : ttinfo.servicenumber!,
                          "No" : ttinfo.no!,
                          "created_date" : ttinfo.createddate!,
                           "select" : statusmdf,
                          "textarea" : remark!].map { "\($0)=\(String(describing: $1 ))" }
        
        
        let request = NSMutableURLRequest(url: NSURL(string: "http://58.27.84.166/mcconline/MCC%20Online%20V3/update_status_mobile.php")! as URL)
        request.httpMethod = "POST"
        let postString = parameters.joined(separator: "&")
        
        poststr = postString
        print(postString)
        
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
            
            let responseString = String(data: data!, encoding: String.Encoding.utf8)
            DispatchQueue.main.async {
                
                if(self.countaging() <= 23)//if aging less than 24 hours
                {
                let updatevie = UIStoryboard(name:"userupdating",bundle:nil).instantiateViewController(withIdentifier: "updatereason") as! reasoncodeview
                
                updatevie.ttinfo = self.ttinfo
                updatevie.statusmdf = self.statusmdf
                updatevie.remark = self.remark.text
                
                 self.navigationController?.pushViewController(updatevie, animated: true)
                
                let index = self.navigationController?.viewControllers.index(of: self)
                self.navigationController?.viewControllers.remove(at: index!)
                
                self.activitiyindicator.stopAnimating()
                }
                else{
                    let updatevie = UIStoryboard(name:"userupdating",bundle:nil).instantiateViewController(withIdentifier: "updatereasonaging") as! reasoncodeviewaging
                    
                    updatevie.ttinfo = self.ttinfo
                    updatevie.statusmdf = self.statusmdf
                    updatevie.remark = self.remark.text
                    
                    self.navigationController?.pushViewController(updatevie, animated: true)
                    
                    let index = self.navigationController?.viewControllers.index(of: self)
                    self.navigationController?.viewControllers.remove(at: index!)
                    
                    self.activitiyindicator.stopAnimating()
                }
                
            }
            
            print("responseString = \(String(describing: responseString))")
        }
        task.resume()
        
    }
    


    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return muteForPickerData.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return muteForPickerData[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
         //print(muteForPickerData[row])
        statusmdf = muteForPickerData[row]
    }
    
    
    
    @IBAction func testradius1(_ sender: Any) {
        let servicenumberstr: String = servicenumber.text!
        
        print(servicenumberstr)
        radiuscheck(login: servicenumberstr)
        
        //status indicator
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
    }
    
    @IBAction func testradius2(_ sender: Any) {
        
        let servicenumberstr: String = referencenumber.text!
        
        print(servicenumberstr)
        radiuscheck(login: servicenumberstr)
        
        //status indicator
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
    }
    
    @IBAction func smartdial1(_ sender: Any) {
        
         var servicenumberstr: String = servicenumber.text!
        
       
        
        if(servicenumberstr.count>8){
            servicenumberstr = ("0")+servicenumberstr
        }
        
        guard let number = URL(string: "tel://" + servicenumberstr) else { return }
        UIApplication.shared.open(number)

    }
    
    @IBAction func smartdial2(_ sender: Any) {
        
        var servicenumberstr: String = referencenumber.text!
        
       
        
        if(servicenumberstr.count>8){
            servicenumberstr = ("0")+servicenumberstr
        }
        
        guard let number = URL(string: "tel://" + servicenumberstr) else { return }
        UIApplication.shared.open(number)
    }
    
    @IBAction func smartdial3(_ sender: Any) {
        
        let servicenumberstr: String = contactnumber.text!
        
        
        guard let number = URL(string: "tel://0" + servicenumberstr) else { return }
        UIApplication.shared.open(number)
    }
    
    @IBAction func speakaction(_ sender: Any) {
      
        if isRecording == true {
            audioEngine.stop()
            reconitionTask?.cancel()
            isRecording = false
            speakbutton.backgroundColor = UIColor(white: 1, alpha: 0.0)
        } else {
            self.recordAndRecognizeSpeech()
            isRecording = true
            speakbutton.backgroundColor = UIColor.red
        }
    }
    
    @IBAction func updatestatus(_ sender: Any) {
        
        activitiyindicator.center = self.view.center
        activitiyindicator.hidesWhenStopped = true
        activitiyindicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.gray
        view.addSubview(activitiyindicator)
        activitiyindicator.startAnimating()
        
        
        let No = ttinfo.no
        
        
        let alertController = UIAlertController(title: No, message: "Update:"+poststr+" Username "+username+" Status:"+statusmdf, preferredStyle: UIAlertControllerStyle.alert)
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.cancel) { (result : UIAlertAction) -> Void in
            self.activitiyindicator.stopAnimating()
            
        }
        let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.default) { (result : UIAlertAction) -> Void in
                      self.updatestatus()
           
        }
        alertController.addAction(cancelAction)
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
   
        
       
        
        
       
    }
   
        override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
