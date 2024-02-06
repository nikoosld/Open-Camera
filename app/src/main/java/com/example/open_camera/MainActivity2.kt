package com.example.open_camera

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class MainActivity2 : AppCompatActivity() {
    private lateinit var previewView: PreviewView
    private lateinit var buttonOn: Button

    // Flag to track flashlight status
    private var flashFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initializeViews()
        getPermission()
        startCamera { camera ->
            setupFlashlightButton(camera)
        }
    }

    // Initialize views by finding them in the layout
    private fun initializeViews() {
        previewView = findViewById(R.id.previewView)
        buttonOn = findViewById(R.id.btnOn)
    }

    // Request necessary permissions
    private fun getPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }

    //Store all needed permissions
    companion object {
        private val permissions = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }

    // Check if all required permissions are granted
    private fun hasPermission(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                applicationContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Start the camera and execute the provided callback when the camera is ready
    private fun startCamera(callback: (Camera) -> Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbind all previous use cases
                cameraProvider.unbindAll()
                // Bind camera to the LifecycleOwner and surface provider
                val camera =
                    cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
                preview.setSurfaceProvider(previewView.surfaceProvider)
                // Execute the callback with the camera object
                callback(camera)
            } catch (exc: Exception) {
                // Handle any errors
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupFlashlightButton(camera: Camera) {
        buttonOn.setOnClickListener {
            toggle(camera)
        }
    }

    //Toggle the flash light
    private fun toggle(camera: Camera) {
        flashFlag = !flashFlag
        if (camera.cameraInfo.hasFlashUnit()) {
            camera.cameraControl.enableTorch(flashFlag)
        }
    }
}