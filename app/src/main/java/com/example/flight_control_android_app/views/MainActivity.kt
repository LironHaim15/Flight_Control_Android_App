package com.example.flight_control_android_app.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.flight_control_android_app.R
import com.example.flight_control_android_app.view_models.ViewModel


class MainActivity : AppCompatActivity() {

    private val vm = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BUTTONS, SEEKERS, JOYSTICK IDs
        val connectBtn: Button = this.findViewById(R.id.connectButton)
        val disconnectBtn: Button = this.findViewById(R.id.disconnect_button)
        val rudderResetBtn: Button = this.findViewById(R.id.rudderResetButton)
        val throttleResetBtn: Button = this.findViewById(R.id.throttleResetButton)
        val rudderBar: SeekBar = this.findViewById(R.id.rudderBar)
        val throttleBar: SeekBar = this.findViewById(R.id.throttleBar)
        val joystickView : Joystick = this.findViewById(R.id.draggableJoystick)

        // SET DEFAULT VALUES FOR SEEKERS
        this.resetClickButton(throttleBar, "Throttle")
        this.resetClickButton(rudderBar, "Rudder")

        // SET onChange function for joystick position change
        joystickView.onChange = { a:Float ,e:Float ->
            this.vm.updateValues(a,"Aileron")
            this.vm.updateValues(e,"Elevator")
        }

        // SET LISTENERS
        connectBtn.setOnClickListener { this.connectClickButton()}
        disconnectBtn.setOnClickListener { this.disconnectClickButton()}
        rudderResetBtn.setOnClickListener { this.resetClickButton(rudderBar,"Rudder")}
        throttleResetBtn.setOnClickListener { this.resetClickButton(throttleBar,"Throttle")}
        seekBarListenerGeneric(rudderBar, "Rudder")
        seekBarListenerGeneric(throttleBar, "Throttle")
    }

    private fun connectClickButton() {
        if (vm.isConnected()){
            displayMessage("Already connected! Please disconnect first.")
        }
        else {
            val ip = findViewById<TextView>(R.id.ip)
            val port: TextView = findViewById(R.id.port)
            try {
                val ipString: String = ip.text.toString()
                val portInt: Int = Integer.valueOf(port.text.toString())
                vm.connect(ipString, portInt)
            } catch (e: Exception) {}

            val thread = Thread {
                Thread.sleep(600)
                runOnUiThread {
                    if (vm.isConnected()) {
                        displayMessage("Connected!")
                    } else {
                        displayMessage("Could not connect! Please enter valid IP and Port")
                    }
                }
            }
            thread.start()
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

    //
    private fun resetClickButton(seekBar: SeekBar,barType: String) {
        seekBar.progress = 0
        vm.updateValues(0f, barType)
    }

    // add seekBars to listeners.
    private fun seekBarListenerGeneric(seekBar: SeekBar, barType: String) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                vm.updateValues(seekBar.progress / 1000000.0f, barType)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    // display given message as toast
    @SuppressLint("RtlHardcoded")
    private fun displayMessage(message: String) {
        val myToast =
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.LEFT, 200, 200)
        myToast.show()
    }
}