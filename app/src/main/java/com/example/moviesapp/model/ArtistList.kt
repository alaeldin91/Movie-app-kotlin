package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

class ArtistList(
    @SerializedName("results")
    val artist: List<Artist>
)