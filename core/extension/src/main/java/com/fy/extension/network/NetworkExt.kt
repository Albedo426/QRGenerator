package com.fy.extension.network

import com.fy.utils.model.ApiResult
import com.fy.utils.model.OnError
import com.fy.utils.model.OnFailure
import com.fy.utils.model.OnLoading
import com.fy.utils.model.OnSuccess

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(result)
    return this
}

inline fun <T> ApiResult<T>.onEmpty(action: () -> Unit): ApiResult<T> {
    if (this is ApiResult.Empty) action()
    return this
}

/**
 *  API call is success but return fail from business.
 */
inline fun <T> ApiResult<T>.onFailure(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) action(exception)
    return this
}

/**
 *  API call throws an error.
 */
inline fun <T> ApiResult<T>.onError(action: (ApiResult.Error) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(this)
    return this
}

inline fun <T> ApiResult<T>.onLoading(action: (T?) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(result)
    return this
}

inline fun <T> ApiResult<T>.orElse(action: () -> Unit): ApiResult<T> {
    if (this !is ApiResult.Success) action()
    return this
}

inline fun <T, R> ApiResult<T>.mapOnSuccess(map: (T?) -> R) = when (this) {
    is ApiResult.Success -> ApiResult.Success(
        map(result),
        message,
        this.statusCode
    )
    is ApiResult.Empty -> this
    is ApiResult.Failure -> this
    is ApiResult.Error -> this
    is ApiResult.Loading -> this
}

fun <T> ApiResult<T>.setReceiveMethods(
    onSuccess: OnSuccess<T?>,
    onFailure: OnFailure,
    onError: OnError,
    onLoading: OnLoading
) {
    onSuccess { response ->
        onSuccess.invoke(response)
    }.onFailure { exception ->
        onFailure.invoke(exception)
    }.onError { exception ->
        onError.invoke(exception)
    }.onLoading {
        onLoading.invoke()
    }
}

fun <T> ApiResult<T>.setReceiver(
    callback: (ApiResult<T>) -> Unit
) {
    callback.invoke(this)
}