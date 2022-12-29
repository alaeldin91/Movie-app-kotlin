package com.example.moviesapp.tvDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ActivityTvDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetails : AppCompatActivity() {
    private lateinit var tvDetailsViewModel: TvDetailsViewModel
    private lateinit var title: String
    private lateinit var body: String
    private var posterUrl: String? = null
    private lateinit var tvDetailsBinding: ActivityTvDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvDetailsBinding = ActivityTvDetailsBinding.inflate(layoutInflater)
        setContentView(tvDetailsBinding.root)
        val intent = intent
        var id = intent.getIntExtra("tvId", 0)
        tvDetailsViewModel = ViewModelProvider(this)[TvDetailsViewModel::class.java]
        tvDetailsViewModel.getTvById(id)
        getObserverTv(id)
    }

    private fun getObserverTv(id: Int) {
        tvDetailsViewModel.getTvByIdLiveData(id).observe(this) { tv ->
            title= tv.name.toString()
             body = tv.overview.toString()
            var posterImage = tv.posterPath
            val posterURL = "https://image.tmdb.org/t/p/w500$posterImage"
            Glide.with(applicationContext).load(posterURL).into(tvDetailsBinding.imageTv)
            tvDetailsBinding.title.text = title
            tvDetailsBinding.body.text = body
        }
    }
}