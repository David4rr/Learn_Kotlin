package com.example.xgram.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.xgram.data.prefs.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreference) : ViewModel() {
    fun getDarkMode(): LiveData<Int> {
        return pref.getDarkMode().asLiveData()
    }

    fun saveDarkMode(currentTheme: Int){
        viewModelScope.launch {
            pref.saveDarkMode(currentTheme)
        }
    }
}