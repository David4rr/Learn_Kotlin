package com.example.xgram.data.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xgram.data.api.ApiConfig
import com.example.xgram.data.db.FavoriteDao
import com.example.xgram.data.db.FavoriteEntity
import com.example.xgram.data.db.FavoriteRepository
import com.example.xgram.data.db.FavoriteRoomDatabase
import com.example.xgram.data.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val favoriteDao: FavoriteDao =
        FavoriteRoomDatabase.getDatabase(application).favoriteDao()

    private val _inResult = MutableLiveData<Boolean>()
    val inResult: LiveData<Boolean> = _inResult

    private val _userDetails = MutableLiveData<DetailUserResponse>()
    val userDetails: LiveData<DetailUserResponse> = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun insertFavorite(favoriteUser: FavoriteEntity){
        mFavoriteRepository.insert(favoriteUser)
        _inResult.value = true
    }

    fun deleteFavorite(favoriteUser: FavoriteEntity){
        mFavoriteRepository.delete(favoriteUser)
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity?> {
        return favoriteDao.getFavoriteByUsername(username)
    }

    fun getUserDetail(dataName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(dataName)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _userDetails.value = response.body()
                    Log.d(TAG, "Successfully")
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDetails"
    }
}