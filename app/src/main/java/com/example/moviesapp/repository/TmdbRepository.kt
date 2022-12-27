package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.example.moviesapp.db.ArtistDao
import com.example.moviesapp.db.MovieDao
import com.example.moviesapp.db.TvDao
import com.example.moviesapp.model.*
import com.example.moviesapp.network.ApiService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Call
import javax.inject.Inject

/**
 * Module Dagger Hilt && InstallIn
 * Inject Constructor
 */
@Module
@InstallIn(ViewModelComponent::class)
class TmdbRepository @Inject constructor(
    apiService: ApiService,
    movieDao: MovieDao,
    artistDao: ArtistDao,
    tvDao: TvDao
) {
    private var apiService: ApiService
    private val movieDao: MovieDao
    private val artistDao: ArtistDao
    private val tvDao: TvDao

    /**
     * Constructor class Api Service,Data Access Object
     */

    init {
        this.apiService = apiService
        this.movieDao = movieDao
        this.artistDao = artistDao
        this.tvDao = tvDao
    }

    /**
     * function insertMovies in Sqlite DataBase (Room DataBase)
     */
    suspend fun insertMovies(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    /**
     * function insertArtist in Sqlite DataBase(Room DataBase)
     */
    suspend fun insertArtist(artist: Artist) {
        artistDao.insertArtist(artist)
    }

    suspend fun insertTv(tvShow: TvShow) {
        tvDao.insertTv(tvShow)
    }

    /**
     * function getLocalArtist getData Store in Local DataBase
     */
    fun getLocalArtist(): LiveData<List<Artist>> {
        return artistDao.getPopularArtists()
    }

    /**
     * function getLocalMovies getData Store in Local DataBase
     */
    fun getLocalMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }

    fun getLocalTvs(): LiveData<List<TvShow>> {
        return tvDao.getTvList()
    }
     fun getLocalArtistById(id: Int):LiveData<Artist>{
         return artistDao.getPopularArtist(id)
     }
    /**
     * getData in Api Using Api Service
     */
    fun getArtist(apiKey: String): Call<ArtistList> {
        return apiService.getPopularArtist(apiKey)
    }

    /**
     * getData in Api Using Api Service
     */
    fun getMovie(apiKey: String): Call<MovieList> {
        return apiService.getPopularMovies(apiKey)
    }

    /**
     * function getLocalTv get Data Store in Local DataBase
     */
    // fun getLocalTv():LiveData<List<TvShow>>{
    //   return
    //}
    fun getTvShow(apiKey: String): Call<TvShowList> {
        return apiService.getPopularTv(apiKey)
    }

}