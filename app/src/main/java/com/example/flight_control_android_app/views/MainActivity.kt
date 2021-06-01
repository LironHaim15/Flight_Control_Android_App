package com.example.flight_control_android_app.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import com.example.flight_control_android_app.R
import com.example.flight_control_android_app.view_models.ViewModel

class MainActivity : AppCompatActivity() {

    private val vm = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectBtn: Button = this.findViewById(R.id.connectButton)
        val disconnectBtn: Button = this.findViewById(R.id.disconnect_button)
        val rudderBar: SeekBar = this.findViewById(R.id.rudderBar)
        val throttleBar: SeekBar = this.findViewById(R.id.throttleBar)
        val joystickView : Joystick = this.findViewById(R.id.draggableJoystick)

        joystickView.onChange = { a:Float ,e:Float ->
            this.vm.updateValues(a,"Aileron")
            this.vm.updateValues(e,"Elevator")
        }

        connectBtn.setOnClickListener { this.connectClickButton() }
        disconnectBtn.setOnClickListener { this.disconnectClickButton() }
        seekBarListenerGeneric(rudderBar, "Rudder")
        seekBarListenerGeneric(throttleBar, "Throttle")
    }

    private fun connectClickButton() {
        if (this.vm.isConnected()){
            this.displayMessage("Already connected! Please disconnect first.")
        }
        val ip = findViewById<TextView>(R.id.ip)
        val port: TextView = findViewById(R.id.port)
        try {
            val ipString: String = ip.text.toString()
            val portInt :Int = Integer.valueOf(port.text.toString())
            this.vm.connect(ipString, portInt) //////try catch
            this.displayMessage("Connected!")
        }
        catch (e: Exception){
            this.displayMessage("Could not connect! Please enter valid IP and Port")
        }
    }

    private fun disconnectClickButton() {
        if (this.vm.isConnected()){
            this.vm.disconnect()
            this.displayMessage("Disconnected!")
        }
        else{
            this.displayMessage("Not connected!")
        }
    }

    private fun seekBarListenerGeneric(bar: SeekBar, barType: String) {
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                vm.updateValues(seekBar.progress / 1000.0f, barType)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
    private fun displayMessage(message: String) {
        val myToast =
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT, 200, 200)
        myToast.show()
    }

}