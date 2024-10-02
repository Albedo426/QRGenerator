package com.fy.data.movie.model

import com.fy.data.movie.response.MovieApiModel
import javax.inject.Inject

class MovieUiModelMapper @Inject constructor() {

    fun toUIModel(response: MovieApiModel?) = response?.movieOverviews?.map { movie ->
        MovieUiModel(
            id = movie.id,
            title = movie.originalTitle,
            overview = movie.overview,
            posterPath = addPosterBaseUrl(movie.posterPath),
            releaseDate = movie.releaseDate
        )
    }.orEmpty()

    private fun addPosterBaseUrl(posterPath: String) =
        "${MovieConstants.POSTER_PATH_URL}$posterPath"
}
