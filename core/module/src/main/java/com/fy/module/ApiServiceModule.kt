package com.fy.module

import com.fy.data.movie.service.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun provideAuthService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)
}
