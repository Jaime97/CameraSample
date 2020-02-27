/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app.view

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmEventsActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.PermissionsController
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.ActivityCameraBinding
import org.example.library.feature.camera.presentation.CameraViewModel

const val REQUEST_IMAGE_CAPTURE = 101

// MvvmEventsActivity for simplify creation of MVVM screen with https://github.com/icerockdev/moko-mvvm
class CameraActivity :
    MvvmEventsActivity<ActivityCameraBinding, CameraViewModel, CameraViewModel.EventsListener>(),
    CameraViewModel.EventsListener {

    override val layoutId: Int = R.layout.activity_camera
    override val viewModelClass: Class<CameraViewModel> = CameraViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    var image_uri: Uri? = null

    // createViewModelFactory is extension from https://github.com/icerockdev/moko-mvvm
    // ViewModel not recreating at configuration changes
    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {

        // Prepares the permissions controller and binds it to the activity lifecycle.
        val permissionsController = PermissionsController(applicationContext = this).also {
            it.bind(lifecycle, supportFragmentManager)
        }

        AppComponent.factory.cameraFactory.createCameraViewModel(
            eventsDispatcher = eventsDispatcherOnMain(),
            permissionsController = permissionsController
        )
    }

    // route called by EventsDispatcher from ViewModel (https://github.com/icerockdev/moko-mvvm)
    override fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent,
            REQUEST_IMAGE_CAPTURE
        )
    }

    override fun onDenied(exception: DeniedException) {
        showAlert(getString(R.string.permission_rejected), getString(R.string.permission_rejected_once))
    }

    override fun onDeniedAlways(exception: DeniedAlwaysException) {
        showAlert(getString(R.string.permission_rejected), getString(R.string.permission_rejected_always))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun showAlert(title: String, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(description)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }
        builder.show()
    }
}
