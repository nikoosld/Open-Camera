package com.example.open_camera

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var buttonOn: Button
    lateinit var buttonOff: Button
    lateinit var button: Button
    private var cameraManager: CameraManager? = null
    private var getCameraID: String? = null
    private var flashFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOn = findViewById(R.id.btnOn)
        buttonOff = findViewById(R.id.btnOff)
        button = findViewById(R.id.btnOpenCamera)

        getCamera()

        buttonOn.setOnClickListener {
            turnOn()
        }
        buttonOff.setOnClickListener {
            turnOff()
        }
        button.setOnClickListener {
            creatActivity()
        }

    }

    //Turns the flash light on
    private fun turnOn() {
        if (!flashFlag) {
            toggle()
        }
    }

    //Turns the flash light off
    private fun turnOff() {
        if (flashFlag) {
            toggle()
        }
    }

    //Toggles the flash light
    private fun toggle() {
        try {
            flashFlag = !flashFlag
            cameraManager!!.setTorchMode(getCameraID!!, flashFlag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Getting the back camera as the default camera
    private fun getCamera() {
        cameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            getCameraID = cameraManager!!.cameraIdList[0]

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    //Creates new activity for displaying the camera preview
    fun creatActivity() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

}