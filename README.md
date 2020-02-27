 ![kotlin-version](https://img.shields.io/badge/kotlin-1.3.61-orange)

# Camera Sample

This project is intended to be a more complex example of Kotlin multiplatform than the usually encountered on the internet. It asks for permissions and opens the camera.

## Table of Contents

- [Libraries used](#libraries-used)
- [Requirements](#requirements)
- [How to Run](#how-to-run)

## Libraries used
This project has been created using the [moko template](https://github.com/icerockdev/moko-template), which includes other Kotlin multiplatform libraries such as: [moko mvvm](https://github.com/icerockdev/moko-mvvm), [moko permissions](https://github.com/icerockdev/moko-permissions) and some others not used in this project.

## Requirements
To properly run this project without issues you will need:
- Kotlin-1.3.61
- Gradle-3.5.1
- CocoaPods-1.6.0 or newer

## How to Run

Android - just open repository root directory in Android Studio and press `Run`.  
iOS - run `pod install` in directory `ios-app`. Then open `ios-app/ios-app.xcworkspace` and press `Run` on simulator/device.
