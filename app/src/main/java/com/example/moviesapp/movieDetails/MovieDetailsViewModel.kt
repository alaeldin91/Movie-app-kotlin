package com.example.moviesapp.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.Movie
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(tmdbRepository: TmdbRepository) : ViewModel() {
    private val tmdbRepository: TmdbRepository
    private var movieById: LiveData<Movie>
    private var id: Int = 0

    init {
        this.tmdbRepository = tmdbRepository
        this.movieById = tmdbRepository.getLocalMovieById(id);
    }

    fun getMovieByIdLiveData(id: Int): LiveData<Movie> {
        return tmdbRepository.getLocalMovieById(id)
    }

    fun getMovieById(id: Int) {
        this.movieById = tmdbRepository.getLocalMovieById(id)
    }
}