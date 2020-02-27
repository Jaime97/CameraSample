/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.camera.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class CameraViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val permissionsController: PermissionsController
) : ViewModel(), EventsDispatcherOwner<CameraViewModel.EventsListener> {

    fun onSubmitPressed() {
        requestPermission()
    }

    private fun requestPermission() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.CAMERA)
                permissionsController.providePermission(Permission.STORAGE)
                eventsDispatcher.dispatchEvent { openCamera() }
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                eventsDispatcher.dispatchEvent { onDeniedAlways(deniedAlwaysException) }
            } catch (deniedException: DeniedException) {
                eventsDispatcher.dispatchEvent { onDenied(deniedException) }
            }
        }
    }

    interface EventsListener {
        fun openCamera()
        fun onDenied(exception: DeniedException)
        fun onDeniedAlways(exception: DeniedAlwaysException)
    }
}