package com.example.xgram.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteEntity)

    @Update
    fun update(favoriteUser: FavoriteEntity)

    @Delete
    fun delete(favoriteUser: FavoriteEntity)

    @Query("SELECT * from FavoriteEntity ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<FavoriteEntity?>

}