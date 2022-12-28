package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.model.Artist
@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)
    @Query("SELECT  * FROM popular_artists")
     fun getPopularArtists(): LiveData<List<Artist>>
     @Query("SELECT * FROM popular_artists where id=:id")
     fun getPopularArtist(id:Int):LiveData<Artist>

}