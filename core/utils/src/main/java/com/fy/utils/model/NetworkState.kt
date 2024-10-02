package com.fy.utils.model

sealed class NetworkState {
    object Initial : NetworkState()
    data class Loading(
        val loadingType: LoadingType,
        val loadingText: String? = null
    ) : NetworkState()

    data class Error(
        val error: ApiResult.Error,
        val messageType: MessageType,
        val retryCall: (() -> Unit)?
    ) : NetworkState()

    data class Failure(
        val error: ApiResult.Failure,
        val messageType: MessageType
    ) : NetworkState()

    data class Success(
        val response: ApiResult<Any>,
        val messageType: MessageType
    ) : NetworkState()
}