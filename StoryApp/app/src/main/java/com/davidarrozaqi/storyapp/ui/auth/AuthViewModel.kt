package com.davidarrozaqi.storyapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidarrozaqi.storyapp.utils.PreferenceManager

class AuthViewModel(private val pref: PreferenceManager) : ViewModel() {
    val _isLogin = MutableLiveData<String>()
    val isLogin: LiveData<String> = _isLogin

    fun getToken(){
        _isLogin.value = pref.getToken
    }

    init {
        getToken()
    }
}