package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.database.DatabaseBuilder
import com.dicoding.asclepius.data.database.HistoryRepository
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.viewmodel.ResultViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var currentImageUri: Uri
    private lateinit var prediction: String
    private var confidenceScore: Float = 0f

    private val viewModel: ResultViewModel by viewModels {
        ViewModelFactory(HistoryRepository(DatabaseBuilder.getDatabase(this).historyDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        currentImageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        prediction = intent.getStringExtra(EXTRA_PREDICTION) ?: ""
        confidenceScore = intent.getFloatExtra(EXTRA_CONFIDENCE, 0f)

        binding.resultImage.setImageURI(currentImageUri)

        val resultText = getString(R.string.result, prediction, confidenceScore)
        binding.resultText.text = resultText

        binding.btnSave.setOnClickListener {
            saveHistory()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveHistory() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.saveHistory(currentImageUri.toString(), prediction, confidenceScore)
            runOnUiThread {
                showToast("Saved")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_PREDICTION = "extra_prediction"
        const val EXTRA_CONFIDENCE = "extra_confidence"
    }

}