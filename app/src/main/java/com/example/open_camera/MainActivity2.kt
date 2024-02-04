package com.example.open_camera

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import java.util.concurrent.Executors


class MainActivity2 : AppCompatActivity() {
    lateinit var buttonOn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        buttonOn = findViewById(R.id.btnOn)

        // Handles required permissions
        if (hasPermission()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable{
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbinds use cases before rebinding
                cameraProvider.unbindAll()
                // Binds use cases to the lifecycle of the activity
                val camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
                //Handles flash light button functionality
                var flashFlag : Boolean = false
                buttonOn.setOnClickListener {
                    flashFlag = toggle(camera, flashFlag)
                }
                // R.id.viewFinder is the ID of the PreviewView
                val previewView: PreviewView = findViewById(R.id.previewView)
                val executor = Executors.newSingleThreadExecutor()

                preview.setSurfaceProvider(executor, previewView.surfaceProvider)
            } catch(exc: Exception) {
                // Handle any errors
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }
    //Toggles the flash light
    private fun toggle (camera: Camera, flashFlag: Boolean): Boolean{
        val newFlag = !flashFlag
        if (camera.cameraInfo.hasFlashUnit()) {
            camera.cameraControl.enableTorch(newFlag)
        }
        return newFlag
    }

    //Storing all needed permissions
    companion object {
        private val permissions = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }

    //Checks for required permissions
    private fun hasPermission(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

}

