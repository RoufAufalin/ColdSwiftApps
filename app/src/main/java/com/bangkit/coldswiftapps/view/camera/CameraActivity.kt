package com.bangkit.coldswiftapps.view.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bangkit.coldswiftapps.R
import com.bangkit.coldswiftapps.databinding.ActivityCameraBinding
import com.bangkit.coldswiftapps.utils.createCustomTempFile
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executor

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()

        binding.captureImage.setOnClickListener {
            takePicture()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    cropImageToSquare(photoFile)

                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, photoFile.absolutePath)
                    setResult(CAMERAX_RESULT, intent)
                    finish()

                    Toast.makeText(
                        this@CameraActivity,
                        "Berhasil mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private fun cropImageToSquare(photoFile: File) {
        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
        val dimension = minOf(bitmap.width, bitmap.height)
        val xOffset = (bitmap.width - dimension) / 2
        val yOffset = (bitmap.height - dimension) / 2
        val croppedBitmap = Bitmap.createBitmap(bitmap, xOffset, yOffset, dimension, dimension)
        val outStream = FileOutputStream(photoFile)
        croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}
