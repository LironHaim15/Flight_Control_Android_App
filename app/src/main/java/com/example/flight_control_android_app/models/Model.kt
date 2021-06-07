package com.example.flight_control_android_app.models


import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class Model {
    private var es : ExecutorService? = null
    private var connected: Boolean = false
    private var client : Socket? = null
    private var writer: OutputStream? = null
    private var aileron:Float = 0.0f
    private var elevator:Float = 0.0f
    private var throttle:Float = 0.0f
    private var rudder:Float = 0.0f
    init{
        es= Executors.newSingleThreadExecutor()
    }

    fun connect(ip:String ,port:Int) {
        this.es?.execute {
            try {
                this.client = Socket()
                this.client!!.connect(InetSocketAddress(ip, port), 500)
                this.writer = this.client!!.getOutputStream()
                this.connected = true
            } catch (e: Exception) {}
        }
    }
    private fun setAileron(a:Float){
        this.aileron = a
        if(!this.connected)
            return
        this.es?.execute {
            writer?.write(
                ("set /controls/flight/aileron[0] " + this.aileron + "\r\n").toByteArray(
                    Charset.defaultCharset()
                )
            )
            writer?.flush()
        }
    }
    private fun setElevator(e:Float){
        this.elevator = e
        if(!this.connected)
            return
        this.es?.execute {
            writer?.write(
                ("set /controls/flight/elevator " + this.elevator + "\r\n").toByteArray(
                    Charset.defaultCharset()
                )
            )
            writer?.flush()
        }
    }
    private fun setThrottle(t:Float){
        this.throttle = t
        if(!this.connected)
            return
        this.es?.execute {
            writer?.write(
                ("set /controls/engines/current-engine/throttle " + this.throttle + "\r\n").toByteArray(
                    Charset.defaultCharset()
                )
            )
            writer?.flush()
        }
    }
    private fun setRudder(r:Float){
        this.rudder = r
        if(!this.connected)
            return
        this.es?.execute {
            writer?.write(
                ("set /controls/flight/rudder " + this.rudder + "\r\n").toByteArray(
                    Charset.defaultCharset()
                )
            )
            writer?.flush()
        }
    }

    fun disconnect(){
        if (!connected) { return}
        this.es?.execute {
            try {
                this.connected = false
                this.client?.close()
            } catch (e: Exception) {
                this.connected = false
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