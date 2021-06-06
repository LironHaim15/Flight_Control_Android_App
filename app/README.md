# Flight Control Android App

## About
Our Control Android App allows you to control the airplane in FlightGear Simpulator using your Android device.

Project developed in Android Studio.

### Features
  * Connect Button - connect to the FG server using IP and Port inserted in the text boxes.
  * Joystick - moving the joystick controls the aircraft's aileron and elevator (aileron values are between 0 to 1 , elevator values are between 0 to 1).
  * Throttle Slider - controls the throttle, its values are between 0 to 1.
  * Rudder Slider - controls the rudder, its values are between -1 to 1.
  * Reset Rudder Button - resets the value of rudder to zero and update the FG server.
  * Reset Throttle Button- resets the value of throttle to zero and update the FG server.

    
## Table of contents
> * [Flight Control Android App](#flight-control-android-app)
>   * [About](#about)
>   * [Features](#features)
>   * [Table of contents](#table-of-contents)
>   * [Starting The Android App](#starting-the-android-app)
>   * [Usage](#usage)
>   * [Requirements](#requirements)
>   * [Program Structure](#program-structure)
>   * [Video Demo](#video-demo)
>   * [Authors](#authors)
>   * [Screenshots](#screenshots)
---
## Starting The Android App

Download the project from this Git page, save it on your computer and run the project in Android Studio. You should have an Android device emulator coming with the Android Studio, emulate a chosed device and run our app.


## Requirements
Android version 21 and above.
Not recommanded to use tablet, we did not optimize it for multiple resolutions and ratios. Cellular Android device is recommanded.

## Program Structure

The project uses the design pattern of MVVM.
Our code files are organized in 3 folders:
* `views` folder contains  `MainActivity.kt`,`Joystick.kt` where the main activity responsible for user interface, and the joystick is an individual component that can be transferred to any other project and be responsible for user interface of a joystick.
* `veiw_models` folder contains `ViewModel.kt` which is responsible for connect between the Veiw to Model.
* `models` contains the `Model.kt` file which responsible to the logical part of Features, such as creating a socket and connecting, sending data over that connection.

Full UML picture can be found within the project and in the [Screenshots](#screenshots) below.

## Video Demo
 
[Watch here](https://www.youtube.com/watch?v=5fgQAOJnl9c)


## Authors

* [Stav Lidor](https://github.com/stavLidor)
* [Liron Haim](https://github.com/LironHaim15)

## Screenshots

### Home Screen (dark)
<img src="https://github.com/LironHaim15/anomalies_detection_web_app/blob/master/screenshots/dark_empty.jpg" alt="Home Screen"/>

### Anomalies Result Screen (dark)
<img src="https://github.com/LironHaim15/anomalies_detection_web_app/blob/master/screenshots/dark_ano.jpg" alt="Anomalies Result Screen"/>

### Home Screen (light)
<img src="https://github.com/LironHaim15/anomalies_detection_web_app/blob/master/screenshots/light_ano.jpg" alt="Home Screen"/>

### UML
<img src="https://github.com/LironHaim15/anomalies_detection_web_app/blob/master/screenshots/UML.png" alt="UML"/>
