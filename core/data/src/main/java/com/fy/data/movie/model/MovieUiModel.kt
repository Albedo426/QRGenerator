package com.fy.data.movie.model

data class MovieUiModel(
    var id : Int,
    var title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String
)
