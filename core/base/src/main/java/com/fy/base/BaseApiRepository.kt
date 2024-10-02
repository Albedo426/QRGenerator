package com.fy.base

import com.fy.extension.globalExt.asSimpleApiResult
import com.fy.extension.globalExt.asApiResult
import com.fy.utils.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseApiRepository {


    suspend fun <T> request(
        call: suspend () -> Response<ApiResponse<T>>
    ): com.fy.utils.model.ApiResult<T> = withContext(Dispatchers.IO) {
        runCatching {
            call.invoke().asApiResult
        }.getOrElse { exception ->
            com.fy.utils.model.ApiResult.Error(exception)
        }
    }

    suspend fun <T> simpleRequest(
        call: suspend () -> Response<T>
    ): com.fy.utils.model.ApiResult<T> = withContext(Dispatchers.IO) {
        runCatching {
            call.invoke().asSimpleApiResult
        }.getOrElse { exception ->
            com.fy.utils.model.ApiResult.Error(exception)
        }
    }
}
