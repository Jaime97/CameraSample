
import Foundation
import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryMvvm
import SkyFloatingLabelTextField

class CameraViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    private var viewModel: CameraViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = AppComponent.factory.cameraFactory.createCameraViewModel(eventsDispatcher: EventsDispatcher(listener: self), permissionsController: PermissionsController())
        
    }
    
    @IBAction func onSubmitPressed() {
        viewModel.onSubmitPressed()
    }

    func showAlert(title: String, description: String) {
        let alert = UIAlertController(title: title, message: description, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: NSLocalizedString("Ok", comment: ""), style: UIAlertActionStyle.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    deinit {
        // clean viewmodel to stop all coroutines immediately
        viewModel.onCleared()
    }
}

extension CameraViewController: CameraViewModelEventsListener {
    func onDenied(exception: DeniedException) {
        showAlert(title: NSLocalizedString("permission_rejected", comment: ""), description: NSLocalizedString("permission_rejected_once", comment: ""))
    }
    
    func onDeniedAlways(exception: DeniedAlwaysException) {
        showAlert(title: NSLocalizedString("permission_rejected", comment: ""), description: NSLocalizedString("permission_rejected_always", comment: ""))
    }
    
    func openCamera() {
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = .camera;
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
    }
    
}
