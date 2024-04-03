package com.example.xgram.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgram.data.api.ApiConfig
import com.example.xgram.data.response.UserResponseItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _followers = MutableLiveData<List<UserResponseItems>?>()
    val followers: LiveData<List<UserResponseItems>?> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserResponseItems>> {
            override fun onResponse(
                call: Call<List<UserResponseItems>>,
                response: Response<List<UserResponseItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onResponse error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "FollowersViewModel"
    }
}

