package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.model.Movie

@Dao
 interface MovieDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)
    @Query("SELECT * FROM mediaDb")
     fun getAllMovies():LiveData<List<Movie>>

}