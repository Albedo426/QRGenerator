package com.fy.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fy.utils.model.ApiResult
import com.fy.utils.model.LoadingType
import com.fy.utils.model.MessageType
import com.fy.utils.model.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class BaseViewModel : ViewModel() {



    private val stateFlow: MutableSharedFlow<NetworkState> = MutableSharedFlow()
    val networkSharedFlow: MutableSharedFlow<NetworkState> = MutableSharedFlow()

    private var loadingCount = 0

    suspend fun <T : Any> apiCall(
        loadingType: LoadingType = LoadingType.DEFAULT,
        errorMessageType: MessageType = MessageType.NONE,
        successMessageType: MessageType = MessageType.NONE,
        retryCall: (() -> Unit)? = null,
        call: suspend () -> Flow<ApiResult<T>>
    ): ApiResult<T> = suspendCoroutine { continuation ->
        viewModelScope.launch {
            if (loadingType != LoadingType.NONE) {
                networkSharedFlow.emit(NetworkState.Loading(loadingType))
            }
            call.invoke().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> {

                    }

                    is ApiResult.Success -> {
                        networkSharedFlow.emit(NetworkState.Loading(LoadingType.NONE))
                        networkSharedFlow.emit(NetworkState.Success(apiResult, successMessageType))
                        continuation.resume(apiResult)
                    }

                    is ApiResult.Error -> {
                        handleApiError(apiResult, errorMessageType, retryCall)
                        continuation.resume(apiResult)
                    }

                    is ApiResult.Failure -> {
                        ApiResult.Error(exception = apiResult.exception)
                        continuation.resume(apiResult)
                    }

                    else -> {
                        //no*op
                    }
                }

            }
        }
    }

    suspend fun <T : Any> flowApiCall(
        loadingType: LoadingType = LoadingType.DEFAULT,
        errorMessageType: MessageType = MessageType.NONE,
        successMessageType: MessageType = MessageType.NONE,
        retryCall: (() -> Unit)? = null,
        call: suspend () -> Flow<ApiResult<T>>
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            val scope = this
            suspendCoroutine<ApiResult<T>> { continuation ->
                scope.launch {
                    call.invoke().collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                                if (loadingType != LoadingType.NONE) {
                                    stateFlow.emit(NetworkState.Loading(loadingType))
                                }
                            }

                            is ApiResult.Success -> {
                                stateFlow.emit(NetworkState.Loading(LoadingType.NONE))
                                stateFlow.emit(NetworkState.Success(result, successMessageType))
                                continuation.resume(result)
                            }

                            else -> {
                                handleApiError(result, errorMessageType, retryCall)
                                continuation.resume(result)
                            }
                        }
                    }
                }
            }
        } catch (exception: Exception) {
            val error = ApiResult.Error(exception)
            handleApiError(error, errorMessageType, retryCall)
            return@withContext error
        }
    }

    private suspend fun <T : Any> handleApiError(
        response: ApiResult<T>, errorType: MessageType, retryCall: (() -> Unit)?
    ) {
        networkSharedFlow.emit(NetworkState.Loading(LoadingType.NONE))

        when (response) {
            is ApiResult.Error -> {
                networkSharedFlow.emit(NetworkState.Error(response, errorType, retryCall))
            }

            is ApiResult.Failure -> {
                networkSharedFlow.emit(NetworkState.Failure(response, errorType))
            }

            else -> {
                //no-op
            }
        }
    }
}
