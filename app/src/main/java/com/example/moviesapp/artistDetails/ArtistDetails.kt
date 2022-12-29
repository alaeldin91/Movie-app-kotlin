package com.example.moviesapp.artistDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityArtistDetailsBinding
import com.example.moviesapp.di.GlideApp
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ArtistDetails : AppCompatActivity() {
    private lateinit var artistDetailsViewModel: ArtistDetailsViewModel
    private lateinit var name: String
    private var popularity by Delegates.notNull<Double>()
    private var profilePath:String? = null
    private lateinit var bindingArtistDetails: ActivityArtistDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_artist_details)
        bindingArtistDetails = ActivityArtistDetailsBinding.inflate(layoutInflater)
        setContentView(bindingArtistDetails.root)
        val intent = intent
        var id = intent.getIntExtra("id", 0);
        artistDetailsViewModel = ViewModelProvider(this)[ArtistDetailsViewModel::class.java]
        artistDetailsViewModel.getArtistById(id);
        getObserveArtistDetails(id)
    }

    private fun getObserveArtistDetails(id: Int) {
        artistDetailsViewModel.getArtistByIdLiveData(id).observe(this, Observer { artist ->
            name = artist.name;
            popularity = artist.popularity
            profilePath = artist.profile
            bindingArtistDetails.txtTitle.text = name
            val posterURL = "https://image.tmdb.org/t/p/w500$profilePath"
            Glide.with(applicationContext).load(posterURL).into(bindingArtistDetails.imageProfile)

        })
    }


}