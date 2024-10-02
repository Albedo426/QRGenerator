package com.fy.domain.movie

import com.fy.data.movie.repository.MovieRepository
import com.fy.data.movie.model.MovieUiModel
import com.fy.extension.buildDefaultFlow
import com.fy.qualifier.IoDispatcher
import com.fy.utils.model.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MovieUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val publicRepository: MovieRepository,
) {
    operator fun invoke(): Flow<ApiResult<List<MovieUiModel>>> = flow {
        val response = publicRepository.getNowPlayingList()
        emit(response)
    }.buildDefaultFlow(dispatcher)
}
