package com.dicoding.asclepius.view

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HistoryAdapter
import com.dicoding.asclepius.data.database.DatabaseBuilder
import com.dicoding.asclepius.data.database.HistoryRepository
import com.dicoding.asclepius.data.model.HistoryResponse
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.topBar.apply {
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            title = "History"
            setTitleTextColor(Color.WHITE)
        }

        loadHistory()
    }

    private fun loadHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val historyRepo =
                HistoryRepository(DatabaseBuilder.getDatabase(application).historyDao())
            val historyList = historyRepo.getAllHistory()

            withContext(Dispatchers.Main) {
                setUpRecyclerView(historyList)
            }
        }
    }

    private fun setUpRecyclerView(historyList: List<HistoryResponse>) {
        historyAdapter = HistoryAdapter(historyList)
        binding.rvFavorite.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }
}