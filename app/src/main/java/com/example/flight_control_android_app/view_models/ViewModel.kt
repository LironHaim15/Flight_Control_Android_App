package com.example.flight_control_android_app.view_models
import com.example.flight_control_android_app.models.Model

class ViewModel {
    var model = Model()

    fun connect(ip:String ,port:Int)
    {
        this.model.connect(ip,port)
    }

    fun disconnect(){
        this.model.disconnect()
    }

    fun isConnected():Boolean{
        return this.model.isConnected()
    }

    fun updateValues(value:Float, barType: String){
        when (barType) {
            "Rudder" -> model.setRudder(value)
            "Throttle" -> model.setThrottle(value)
            "Aileron" -> model.setAileron(value)
            "Elevator" -> model.setElevator(value)
        }
    }
}