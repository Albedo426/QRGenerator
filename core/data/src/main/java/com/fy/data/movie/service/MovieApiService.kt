package com.fy.data.movie.service


import com.fy.data.movie.endpoints.MovieEndPoint
import com.fy.data.movie.response.MovieApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET(MovieEndPoint.NOW_PLAYING)
    suspend fun getNowPlayingList(): Response<MovieApiModel>

    @GET(MovieEndPoint.UP_COMING)
    suspend fun getUpcomingMovieList(@Query("page") page: Int): Response<MovieApiModel>
}
