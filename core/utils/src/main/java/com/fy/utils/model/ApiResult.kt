package com.fy.utils.model


import com.google.gson.annotations.SerializedName

const val LOADING_STATUS = -1

sealed class ApiResult<out T>(
    @SerializedName("code") open val statusCode: Int,
    @SerializedName("message") open val message: String? = null
) {
    class Success<out T>(
        val result: T,
        override val message: String? = null,
        override val statusCode: Int = HttpStatus.SUCCESS
    ) : ApiResult<T>(statusCode, message)

    class Empty(
        override val statusCode: Int = HttpStatus.SUCCESS
    ) : ApiResult<Nothing>(statusCode)

    class Failure(
        val exception: Throwable = Exception(),
        val payload: String? = null,
        override val message: String? = null,
        override val statusCode: Int
    ) : ApiResult<Nothing>(statusCode, message)

    class Error(
        val exception: Throwable = Exception(),
        val errorApiModelType: ErrorApiModelType = ErrorApiModelType.UNKNOWN,
        val errorApiModel: Any? = null,
        override val statusCode: Int = HttpStatus.HTTP_400
    ) : ApiResult<Nothing>(statusCode)

    object Loading : ApiResult<Nothing>(LOADING_STATUS)
}

enum class ErrorApiModelType {
    UNKNOWN, API,
    //fixme maybe more REGISTER,
}

interface OnSuccess<T> {
    fun invoke(data: T)
}

interface OnFailure {
    fun invoke(exception: Throwable)
}

interface OnError {
    fun invoke(exception: ApiResult.Error)
}

interface OnLoading {
    fun invoke()
}
