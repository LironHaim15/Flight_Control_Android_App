package com.example.flight_control_android_app.models

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.io.IOException
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.Charset


class Model {
    private var connected: Boolean = false
    private var client : Socket? = null
    private var writer: OutputStream? = null
    private var thread: Thread? = null
    var status = "Disconnected"
    private var aileron:Float = 0.0f
    private var elevator:Float = 0.0f
    private var throttle:Float = 0.0f
    private var rudder:Float = 0.0f

    fun connect(ip:String ,port:Int){
        try {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            this.client = Socket()
            this.thread = Thread {
                threadFunc(ip, port)
            }
            this.thread!!.start()
        }
        catch (e: Exception){}
    }

    @Throws(IOException::class)
    private fun threadFunc(ip:String ,port:Int){
        try {
            this.client!!.connect(InetSocketAddress(ip, port), 500)
            this.writer = this.client!!.getOutputStream()
        }
        catch (e: Exception){return}

        this.connected = true
        this.status = "Connected"
        while (connected){
            try {
                Thread.sleep(100)
                this.sendValues()
            }
            catch (e: Exception){
                this.connected = false
                this.status = "Disconnected"
            }
        }
    }
    private fun sendValues() {
        writer?.write(("set /controls/flight/aileron[0] " + this.aileron + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/flight/elevator " + this.elevator + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/engines/current-engine/throttle " + this.throttle + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/flight/rudder " + this.rudder + "\r\n").toByteArray(Charset.defaultCharset()))
    }
    private fun setAileron(a:Float){
        this.aileron = a
    }
    private fun setElevator(e:Float){
        this.elevator = e
    }
    private fun setThrottle(t:Float){
        this.throttle = t
    }
    private fun setRudder(r:Float){
        this.rudder = r
    }

    fun disconnect(){
        if (connected) {
            try {
                this.status = "Disconnected"
                this.connected = false
                this.thread?.join()
                this.client?.close()
            }
            catch (e: Exception){
                this.connected = false
                this.status = "Disconnected"
            }
        }
    }

    fun isConnected():Boolean{
        return this.connected
    }

    fun updateValues(value:Float, barType: String){
        when (barType) {
            "Rudder" -> setRudder(value)
            "Throttle" -> setThrottle(value)
            "Aileron" -> setAileron(value)
            "Elevator" -> setElevator(value)
        }
    }
}