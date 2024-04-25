package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.NewsAdapter
import com.dicoding.asclepius.data.model.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityHomeBinding
import com.dicoding.asclepius.utils.Event
import com.dicoding.asclepius.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val adapter by lazy {
        NewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnClassification.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvDiscover.layoutManager = layoutManager
        binding.rvDiscover.adapter = adapter

        homeViewModel.resultNewsItem.observe(this) {
            when (it) {
                is Event.Success<*> -> {
                    adapter.submitList(it.data as MutableList<ArticlesItem>)
                }

                is Event.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Event.Loading -> {
                    binding.lpiNews.isVisible = it.isLoading
                }
            }
        }
        homeViewModel.getListNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }
}