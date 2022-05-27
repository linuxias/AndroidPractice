package com.linuxias.qrcodereader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.linuxias.qrcodereader.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraProviderFuture:
            ListenableFuture<ProcessCameraProvider>

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.all { it.value }) {
            startCamera()
        } else {
            finish()
        }
    }

    private var isDetected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    override fun onResume() {
        super.onResume()
        isDetected = false
    }

    fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            val preview = getPreview()
            val imageAnalysis = getimageAnalysis()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.bindToLifecycle(this,
                cameraSelector, preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(this))
    }

    fun getPreview() : Preview {
        val preview : Preview = Preview.Builder().build()
        preview.setSurfaceProvider(binding.barcordPreview.getSurfaceProvider())

        return preview
    }

    fun getimageAnalysis() : ImageAnalysis {
        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val imageAnalysis = ImageAnalysis.Builder().build()

        imageAnalysis.setAnalyzer(cameraExecutor,
            QRCodeAnalyzer(object : OnDetectListener {
                override fun onDetect(message: String) {
                    if (isDetected == false) {
                        isDetected = true
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("message", message)
                        startActivity(intent)
                    }
                }
            }))
        return imageAnalysis
    }

    private companion object {
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}