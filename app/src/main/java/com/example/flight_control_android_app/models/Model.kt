package com.example.flight_control_android_app.models

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.lang.Thread
import java.nio.charset.Charset

class Model {
    private var connected: Boolean = false
    private var client : Socket? = null
    private var writer: OutputStream? = null
    private var thread: Thread? = null

    private var aileron:Float = 0.0f
    private var elevator:Float = 0.0f
    private var throttle:Float = 0.0f
    private var rudder:Float = 0.0f

    fun connect(ip:String ,port:Int) {
        try {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            this.client = Socket(ip,port)
            this.writer = this.client!!.getOutputStream()
            this.thread = Thread(this::threadFunc)
            this.thread!!.start()
            this.connected = true

        } catch (e: Exception) {
            println(e)
            this.connected = false
        }
    }

    private fun threadFunc(){
        this.connected = true
        while (connected){
            try {
                Thread.sleep(100)
                this.sendValues()
            }
            catch (e: Exception){
                println(e)
                this.connected = false
            }
        }
    }
    private fun sendValues() {
        writer?.write(("set /controls/flight/aileron[0] " + this.aileron + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/flight/elevator " + this.elevator + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/engines/current-engine/throttle " + this.throttle + "\r\n").toByteArray(Charset.defaultCharset()))
        writer?.write(("set /controls/flight/rudder " + this.rudder + "\r\n").toByteArray(Charset.defaultCharset()))
    }
    fun setAileron(a:Float){
        this.aileron = a
    }
    fun setElevator(e:Float){
        this.elevator = e
    }
    fun setThrottle(t:Float){
        this.throttle = t
    }
    fun setRudder(r:Float){
        this.rudder = r
    }

    fun disconnect(){
        if (connected) {
            try {
                this.connected = false
                this.thread?.join()
                this.client?.close()
            }
            catch (e: Exception){
                this.connected = false
            }
        }
    }

    fun isConnected():Boolean{
        return this.connected
    }
}