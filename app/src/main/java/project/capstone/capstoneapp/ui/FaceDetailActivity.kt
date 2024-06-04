package project.capstone.capstoneapp.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import project.capstone.capstoneapp.R
import project.capstone.capstoneapp.classfier.face.FaceClassifier

class FaceDetailActivity : AppCompatActivity() {

    private lateinit var camera: Button
    private lateinit var gallery: Button
    private lateinit var imageView: ImageView
    private lateinit var result: TextView
    private val imageSize = 150
    private lateinit var imageClassifier: FaceClassifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detail)

        camera = findViewById(R.id.btn_camera)
        gallery = findViewById(R.id.btn_gallery)
        imageView = findViewById(R.id.imageView2)
        result = findViewById(R.id.tv_result_gesture)

        imageClassifier = FaceClassifier(applicationContext, imageSize)

        camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        gallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 3)
        } else {
            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                when (requestCode) {
                    3 -> {
                        val image = data?.extras?.get("data") as? Bitmap
                        if (image != null) {
                            imageView.setImageBitmap(image)
                            val scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                            val resultText = imageClassifier.classifyImage(scaledImage)
                            result.text = resultText
                        } else {
                            Log.e("FaceDetectActivity", "Bitmap data is null")
                        }
                    }
                    1 -> {
                        val uri = data?.data
                        if (uri != null) {
                            val image = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                            imageView.setImageBitmap(image)
                            val scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                            val resultText = imageClassifier.classifyImage(scaledImage)
                            result.text = resultText
                        } else {
                            Log.e("FaceDetectActivity", "Uri data is null")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("FaceDetectActivity", "Error processing image: ${e.message}")
            }
        }
    }
}