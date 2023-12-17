package com.example.open_camera

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    private var cameraManager : CameraManager? = null
    private var getCameraID : String? = null
    private var torchStatus : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.btnOpenCamera)
        cameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE)as CameraManager
        try {
            getCameraID = cameraManager!!.cameraIdList[0]

        }catch (e:Exception){
            e.printStackTrace()
        }
        button.setOnClickListener {
            if(!torchStatus){
                try {
                    cameraManager!!.setTorchMode(getCameraID!!,true)
                    torchStatus = true
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }else{
                try {
                    cameraManager!!.setTorchMode(getCameraID!!,false)
                    torchStatus = false
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}