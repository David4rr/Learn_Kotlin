package com.example.xgram.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.xgram.data.prefs.SettingPreference

class MainViewModel(private val pref: SettingPreference) : ViewModel() {
    fun getDarkMode(): LiveData<Int> {
        return pref.getDarkMode().asLiveData()
    }
}