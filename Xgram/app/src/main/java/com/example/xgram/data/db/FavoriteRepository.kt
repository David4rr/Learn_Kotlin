package com.example.xgram.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllFavorite()

    fun insert(favoriteUser: FavoriteEntity){
        executorService.execute { mFavoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteEntity){
        executorService.execute { mFavoriteDao.delete(favoriteUser) }
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity?> {
        return mFavoriteDao.getFavoriteByUsername(username)
    }
}