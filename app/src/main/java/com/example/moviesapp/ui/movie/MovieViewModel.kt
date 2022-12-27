package com.example.moviesapp.ui.movie

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.Movie
import com.example.moviesapp.model.MovieList
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(repositoryMovie: TmdbRepository) : ViewModel() {
    private var movieListItem: MutableLiveData<List<Movie>> = MutableLiveData()
    private val repositoryMovie: TmdbRepository
    private var movieListItemLocal: LiveData<List<Movie>>

    init {
        this.repositoryMovie = repositoryMovie
        this.movieListItemLocal = repositoryMovie.getLocalMovies()

    }

    fun getMovieListItem(apiKey: String) {
        repositoryMovie.getMovie(apiKey).enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                response.body().let { movieList ->
                    movieListItem.postValue(movieList!!.movieList)
                }
            }

            @SuppressLint("LogNotTimber")
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.i("error", t.message.toString())
            }

        })
    }

    suspend fun insertMovies(movie: Movie) {
        repositoryMovie.insertMovies(movie)
    }

    fun getLiveDataLocalMovies(): LiveData<List<Movie>> {
        return movieListItemLocal
    }

    fun getLocalMovies() {
        this.movieListItemLocal = repositoryMovie.getLocalMovies()
    }


    fun getMovie(): MutableLiveData<List<Movie>> {
        return movieListItem

    }


}