package com.example.moviesapp.artistDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.Artist
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsViewModel @Inject constructor(repository: TmdbRepository):ViewModel() {
private val repository:TmdbRepository
    private var artistById:LiveData<Artist>
    private var id : Int =0
    init {
        this.repository = repository
        this.artistById = repository.getLocalArtistById(id)
    }
    fun getArtistByIdLiveData(id: Int):LiveData<Artist>{
        return repository.getLocalArtistById(id)
    }
    fun getArtistById(id: Int){
        this.artistById = repository.getLocalArtistById(id)
    }

}