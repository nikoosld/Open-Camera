package com.example.open_camera

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Directory
import android.widget.Button
import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Handles required permissions
        if (hasPermission()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }

    //It was intended to start the camera :(
    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
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