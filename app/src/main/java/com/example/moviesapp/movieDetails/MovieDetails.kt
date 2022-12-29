package com.example.moviesapp.movieDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetails : AppCompatActivity() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var title: String
    private var movieProfile: String? = null
    private lateinit var body: String
    private lateinit var movieDetailsBinding: ActivityMovieDetailsBinding

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(movieDetailsBinding.root)
        val intent = intent
        var id = intent.getIntExtra("movieId", 0)
        Log.i("id", "$id")
        movieDetailsViewModel.getMovieById(id)
        getObserverMovieDetails(id)
    }

    private fun getObserverMovieDetails(id: Int) {
        movieDetailsViewModel.getMovieByIdLiveData(id).observe(this, Observer { movie ->
            title = movie.title
            body = movie.overview
            movieProfile = movie.posterPath
            val posterURL = "https://image.tmdb.org/t/p/w500$movieProfile"
            movieDetailsBinding.txtTitle.text = title
            movieDetailsBinding.body.text = body
            Glide.with(applicationContext).load(posterURL).into(movieDetailsBinding.imageMovie)

        })
    }
}