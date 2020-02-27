/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import org.example.library.feature.camera.di.CameraFactory

class SharedFactory(
    antilog: Antilog
) {
    val cameraFactory = CameraFactory()

    init {
        Napier.base(antilog)
    }
}
