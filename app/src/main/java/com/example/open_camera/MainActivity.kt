package com.example.open_camera

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttonOn: Button
    private lateinit var buttonOff: Button
    private lateinit var button: Button
    var cameraManager: CameraManager? = null
    var getCameraID: String? = null

    // Flag to track flashlight status
    var flashFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        getCamera()
        setupListeners()
    }

    // Initialize views by finding them in the layout
    private fun initializeViews() {
        buttonOn = findViewById(R.id.btnOn)
        buttonOff = findViewById(R.id.btnOff)
        button = findViewById(R.id.btnOpenCamera)
    }

    //Get the back camera as the default camera
    private fun getCamera() {
        cameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            getCameraID = cameraManager!!.cameraIdList[0]

        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private fun setupListeners() {
        buttonOn.setOnClickListener { turnOn() }
        buttonOff.setOnClickListener { turnOff() }
        button.setOnClickListener { createCameraPreviewActivity() }
    }

    //Turn the flash light on
    private fun turnOn() {
        if (!flashFlag) {
            toggle()
        }
    }

    //Turn the flash light off
    private fun turnOff() {
        if (flashFlag) {
            toggle()
        }
    }

    //Toggle the flash light
    fun toggle() {
        try {
            flashFlag = !flashFlag
            cameraManager!!.setTorchMode(getCameraID!!, flashFlag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Create new activity for displaying the camera preview
    private fun createCameraPreviewActivity() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

}