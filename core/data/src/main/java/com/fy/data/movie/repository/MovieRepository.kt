package com.fy.data.movie.repository

import com.fy.data.movie.model.MovieUiModel
import com.fy.utils.model.ApiResult

interface MovieRepository {
    suspend fun getNowPlayingList(): ApiResult<List<MovieUiModel>>
}

