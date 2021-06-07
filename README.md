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


Launch your Android device and the App

Run the FlightGear Launcher and go to the `Settings` tab. In the `Additional Settings` catagory at the end of the page, enter this line in the text box:
```
--telnet=socket,in,10,127.0.0.1,6400,tcp
```

>(this line establishes a one way TCP socket with the port 6400 and ip of Local Host 127.0.0.1, these can be changed here at your wish. The paramater '10' indicates the amount of data FG can expect in a single second.)

Start FlightGear by pressing the 'Fly!' button in the launcher.
Once the simulator has loaded up, press on the aircraft model's name in the top menu and start the engine.

Now go back to our Android app, enter the IP of the machine running the FG simulator and the chosen port. Then click on the 'Connect' button. If the connection is established successfully a toast message will pop-up saying `Connected`, otherwise another message will pop-up to indicate a connection hasn't been established.

Now the app is connected to FG and you may take off!. Pull up the throttle and fly away, fly safe!

In order to disconnet from FligtGear press 'Disconnet' button.


## Requirements
Android version 21 and above.
Not recommanded to use tablet, we did not optimize it for multiple resolutions and ratios. Cellular Android device is recommanded.

## Program Structure

The project uses the design pattern of MVVM.
Our code files are organized in 3 folders:
* `views` folder contains  `MainActivity.kt`,`Joystick.kt` where the main activity responsible for user interface, and the joystick is an individual component that can be transferred to any other project and be responsible for user interface of a joystick.
* `veiw_models` folder contains `ViewModel.kt` which is responsible for connect between the Veiw to Model.
* `models` contains the `Model.kt` file which responsible to the logical part of Features, such as creating a socket and connecting, sending data over that connection. Every method functuality is sent as a
* lambda expression to a thread pool as part of the Active Object design pattern.

Full UML picture can be found within the project and in the [Screenshots](#screenshots) below.

## Video Demo
 
[Watch here](https://www.youtube.com/watch?v=AEZzq0wkDuA)


## Authors

* [Stav Lidor](https://github.com/stavLidor)
* [Liron Haim](https://github.com/LironHaim15)

## Screenshots

### App's Home Screen
<img src="https://github.com/LironHaim15/Flight_Control_Android_App/blob/master/screenshots/Picture1.png" alt="Home Screen"/>

### UML
<img src="https://github.com/LironHaim15/Flight_Control_Android_App/blob/master/screenshots/UML3.png" alt="UML"/>
