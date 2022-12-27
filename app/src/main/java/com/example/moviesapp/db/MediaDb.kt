package com.example.moviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.model.Artist
import com.example.moviesapp.model.Movie
import com.example.moviesapp.model.TvShow


@Database(entities = [Movie::class,Artist::class,TvShow::class], version = 3, exportSchema = false)
abstract class MediaDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun artistDao(): ArtistDao
    abstract fun tvDao(): TvDao
}