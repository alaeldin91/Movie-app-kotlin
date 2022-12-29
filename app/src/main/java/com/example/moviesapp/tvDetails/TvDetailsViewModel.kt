package com.example.moviesapp.tvDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.TvShow
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class TvDetailsViewModel @Inject constructor(repository: TmdbRepository) : ViewModel() {
    private val repository: TmdbRepository
    private var tvById: LiveData<TvShow>
    private var id: Int = 0

    init {
        this.repository = repository
        this.tvById = repository.getLocalTvById(id)
    }

    fun getTvByIdLiveData(id: Int): LiveData<TvShow> {
        return repository.getLocalTvById(id)
    }

    fun getTvById(id: Int) {
        this.tvById = repository.getLocalTvById(id)
    }
}