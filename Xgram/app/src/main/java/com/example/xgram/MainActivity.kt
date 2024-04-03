package com.example.xgram

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.xgram.data.factory.ViewModelPrefsFactory
import com.example.xgram.data.model.MainViewModel
import com.example.xgram.data.model.SettingViewModel
import com.example.xgram.data.prefs.SettingPreference
import com.example.xgram.data.prefs.dataStore
import com.example.xgram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mainViewModel = obtainViewModel()
        setSavedTheme(mainViewModel)
    }

    private fun setSavedTheme(mainViewModel: MainViewModel) {
        mainViewModel.getDarkMode().observe(this){
            val selectedItem = when (it) {
                0 -> AppCompatDelegate.MODE_NIGHT_NO
                1 -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(selectedItem)
        }
    }

    private fun obtainViewModel(): MainViewModel {
        val pref = SettingPreference.getInstance(application.dataStore)
        return ViewModelProvider(
            this,
            ViewModelPrefsFactory(pref)
        )[MainViewModel::class.java]
    }
}

