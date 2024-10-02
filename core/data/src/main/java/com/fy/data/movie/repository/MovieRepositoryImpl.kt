package com.fy.data.movie.repository

import com.fy.base.BaseApiRepository
import com.fy.data.movie.model.MovieUiModel
import com.fy.data.movie.model.MovieUiModelMapper
import com.fy.data.movie.service.MovieApiService
import com.fy.extension.network.mapOnSuccess
import com.fy.utils.model.ApiResult
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val mapper: MovieUiModelMapper,
) : BaseApiRepository(), MovieRepository {

    override suspend fun getNowPlayingList(): ApiResult<List<MovieUiModel>> = simpleRequest {
        movieApiService.getNowPlayingList()
    }.mapOnSuccess { movieApiModel ->
        //movieDao.insertMovieList(mapper.toEntity(movieApiModel))
        mapper.toUIModel(movieApiModel)
    }

}

