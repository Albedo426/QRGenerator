package com.fy.main

import com.fy.data.movie.model.MovieUiModel

sealed class MovieUiState {
    object Init : MovieUiState()
    data class SuccessMovieList(val list: MutableList<MovieUiModel>) : MovieUiState()
    data class RefreshMovieList(val newList : MutableList<MovieUiModel>) : MovieUiState()
}
