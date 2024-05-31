package com.davidarrozaqi.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidarrozaqi.storyapp.data.dto.auth.LoginRequest
import com.davidarrozaqi.storyapp.data.dto.auth.LoginResponse
import com.davidarrozaqi.storyapp.data.dto.network.ApiResponse
import com.davidarrozaqi.storyapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(val repo: AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResult: LiveData<ApiResponse<LoginResponse>> = _loginResult

    fun login(user: LoginRequest){
        viewModelScope.launch {
            repo.login(user).collect{
                _loginResult.value = it
            }
        }
    }
}