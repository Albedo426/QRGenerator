package com.fy.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fy.interceptors.ApiKeyInterceptor
import com.fy.qualifier.ApiOkHttpClient
import com.fy.utils.AppConstants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(GsonProvider.get())
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor =
        ApiKeyInterceptor()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @ApiOkHttpClient
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor

    ): OkHttpClient = OkHttpClient().newBuilder().apply {
        connectTimeout(AppConstants.APP_TIME_OUT_SEC, TimeUnit.SECONDS)
        writeTimeout(AppConstants.APP_TIME_OUT_SEC, TimeUnit.SECONDS)
        readTimeout(AppConstants.APP_TIME_OUT_SEC, TimeUnit.SECONDS)
        addInterceptor(
            ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context))
                .maxContentLength(AppConstants.CHUCKER_MAX_LENGTH).redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        addInterceptor(httpLoggingInterceptor)
        addInterceptor(apiKeyInterceptor)
        followSslRedirects(false)
        followRedirects(false)
    }.build()
}
