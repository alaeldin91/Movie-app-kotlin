package com.example.moviesapp.ui.artist

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.Artist
import com.example.moviesapp.model.ArtistList
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(repository: TmdbRepository) : ViewModel() {
    private val repository: TmdbRepository
    private var artistListItem: MutableLiveData<List<Artist>> = MutableLiveData()
    private var artistListItemLocal: LiveData<List<Artist>>

    init {
        this.repository = repository
        this.artistListItemLocal = repository.getLocalArtist()

    }

     fun getArtist(apikey: String) {

        repository.getArtist(apikey).enqueue(object : Callback<ArtistList> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(call: Call<ArtistList>, response: Response<ArtistList>) {
                response.body().let { artistList ->
                    artistListItem.postValue(artistList!!.artist)

                }
            }

            @SuppressLint("LogNotTimber")
            override fun onFailure(call: Call<ArtistList>, t: Throwable) {
                Log.i("error", t.message.toString())
            }

        })
    }

    suspend fun insertArtist(artist: Artist) {
        return repository.insertArtist(artist)
    }

    fun getArtistLiveData(): LiveData<List<Artist>> {
        return repository.getLocalArtist()
    }

    fun getLocalArtist() {
        this.artistListItemLocal = repository.getLocalArtist()
    }

    fun getArtistMutableLiveData(): MutableLiveData<List<Artist>> {
        return artistListItem
    }

}




