package com.example.moviesapp.ui.tv

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.TvShow
import com.example.moviesapp.model.TvShowList
import com.example.moviesapp.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(repository: TmdbRepository) : ViewModel() {
    private var tvListItem: MutableLiveData<List<TvShow>> = MutableLiveData()
    private val repository: TmdbRepository
    private var tvListItemLocal: LiveData<List<TvShow>>

    init {
        this.repository = repository
        this.tvListItemLocal = repository.getLocalTvs()
    }

    fun getTvListItem(apiKey: String) {
        repository.getTvShow(apiKey).enqueue(object : Callback<TvShowList> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(call: Call<TvShowList>, response: Response<TvShowList>) {
                response.body().let { tvList ->
                    tvListItem.postValue(tvList!!.tvShows)
                }
            }

            @SuppressLint("LogNotTimber")
            override fun onFailure(call: Call<TvShowList>, t: Throwable) {
                Log.i("error", t.message.toString())
            }

        })
    }

    fun getTv(): MutableLiveData<List<TvShow>> {
        return tvListItem
    }

    suspend fun insertTvs(tvShow: TvShow) {
        return repository.insertTv(tvShow)
    }

    fun getTvLiveData(): LiveData<List<TvShow>> {
        return repository.getLocalTvs()
    }

    fun getLocalTvs() {
        this.tvListItemLocal = repository.getLocalTvs()
    }


}