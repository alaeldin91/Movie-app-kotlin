package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MovieList(@SerializedName("results")
val movieList:List<Movie>
)
