/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.camera.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.permissions.PermissionsController
import org.example.library.feature.camera.presentation.CameraViewModel

class CameraFactory {
    fun createCameraViewModel(
        eventsDispatcher: EventsDispatcher<CameraViewModel.EventsListener>,
        permissionsController: PermissionsController
    ) = CameraViewModel(
        eventsDispatcher = eventsDispatcher,
        permissionsController = permissionsController
    )
}