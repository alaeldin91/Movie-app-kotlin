package com.example.moviesapp.network

import com.example.moviesapp.model.ArtistList
import com.example.moviesapp.model.MovieList
import com.example.moviesapp.model.TvShowList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api Service Interface Using Retrofit
 */
interface ApiService {

    /**
     * function get Data MoviePopular from Server and pass api Key Type String
     */
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MovieList>

    /**
     * function get Data ArtistPopular from Server and pass api Key Type String
     */
    @GET("person/popular")
    fun getPopularArtist(@Query("api_key") apiKey: String): Call<ArtistList>

    /**
     * function get Data TvPopular from Server and pass api Key Type String
     */
    @GET("tv/popular")
    fun getPopularTv(@Query("api_key") apiKey: String): Call<TvShowList>
}
