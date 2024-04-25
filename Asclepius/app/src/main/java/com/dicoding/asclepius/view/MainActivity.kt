package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("Image Uri", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        currentImageUri?.let { uri ->
            val imageClassifier = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        showToast(error)
                    }

                    override fun onResults(result: List<Classifications>?) {
                        result?.let { classifications ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                                val sortedClassification =
                                    classifications[0].categories.sortedByDescending { it.score }
                                val prediction = sortedClassification[0].label
                                val confidenceScore = sortedClassification[0].score * 100

                                moveToResult(uri, prediction, confidenceScore)
                            } else {
                                showToast(getString(R.string.image_classifier_failed))
                            }
                        }
                    }
                }
            )
            imageClassifier.classifyStaticImage(uri)
        } ?: showToast(getString(R.string.empty_image))
    }

    private fun moveToResult(currentImageUri: Uri?, prediction: String, confidenceScore: Float) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.apply {
            putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
            putExtra(ResultActivity.EXTRA_PREDICTION, prediction)
            putExtra(ResultActivity.EXTRA_CONFIDENCE, confidenceScore)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}