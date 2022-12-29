package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.db.MediaDb
import com.example.moviesapp.model.Artist
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.repository.TmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideMediaDb(application: Application): MediaDb {
        return Room.databaseBuilder(application, MediaDb::class.java, "meal_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

    }

    @Singleton
    @Provides
    fun provideTmdbRepositry(apiService: ApiService, mediaDb: MediaDb): TmdbRepository {
        return TmdbRepository(apiService, mediaDb.movieDao(), mediaDb.artistDao(), mediaDb.tvDao())
    }



}