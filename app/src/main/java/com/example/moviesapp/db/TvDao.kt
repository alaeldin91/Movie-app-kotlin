package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.model.TvShow

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tvShow: TvShow)

    @Query("SELECT * FROM popular_tv")
    fun getTvList(): LiveData<List<TvShow>>
}