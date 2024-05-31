package com.davidarrozaqi.storyapp.ui.setting

import androidx.lifecycle.ViewModel
import com.davidarrozaqi.storyapp.data.repository.AuthRepository

class SettingViewModel(private val repository: AuthRepository) : ViewModel() {
    fun logOut(): Boolean {
        return repository.logout()
    }
}