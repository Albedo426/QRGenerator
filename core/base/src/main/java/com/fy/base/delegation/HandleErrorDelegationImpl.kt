package com.fy.base.delegation


import android.content.Context
import androidx.fragment.app.Fragment
import com.fy.base.dialog.LoadingDialog
import com.fy.utils.model.ApiResponse
import com.fy.utils.model.HttpStatus
import com.fy.utils.model.LoadingType
import com.fy.utils.model.MessageType
import com.fy.utils.model.NetworkState
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.SocketTimeoutException

class HandleErrorDelegationImpl : HandleErrorDelegation {

    private var loadingDialog: LoadingDialog? = null

    override fun errorDelegation(
        fragment: Fragment,
        errorState: NetworkState.Error
    ) {
        val error = errorState.error
        val errorApiModel = error.errorApiModel
        val statusCode = error.statusCode

        if (isExistException(error.exception, errorState.retryCall)) return

        if (isHttpStatusUnauthorized(statusCode)) return

        if (isUnsafeConnection(statusCode)) return

        var errorMessage: String? = null

        //fixme all error case
        when (errorApiModel) {
            is ApiResponse<*> -> {
                errorMessage = if (errorApiModel.message.isNullOrEmpty().not()) {
                    errorApiModel.message.orEmpty()
                } else {
                    "GLOBAL ERROR MESSAGE"
                }
            }

        }

        //fixme special status code
        if (statusCode in 500..599) {

        }

        showErrorDelegation(
            fragment = fragment,
            errorType = errorState.messageType,
            message = errorMessage
        )
    }

    override fun showErrorDelegation(
        fragment: Fragment,
        errorType: MessageType,
        message: String?) {

        when (errorType) {
            MessageType.NONE -> {
              //no*op
            }
            MessageType.SNACK_BAR -> Unit
            MessageType.DIALOG -> Unit
            MessageType.BOTTOM_SHEET -> {

            }
        }
    }


    private fun isUnsafeConnection(resultCode: Int): Boolean {
        if (resultCode == HttpStatus.UNSAFE) {

            return true
        }

        return false
    }

    override suspend fun handleLoadingDelegation(
        context: Context,
        loadingType: LoadingType,
        text: String,
        delay: Long
    ) {
        when (loadingType) {
            LoadingType.NONE -> {
                loadingDialog?.hideDialog()
            }
            LoadingType.DEFAULT -> {
                loadingDialog?.hideDialog()
                delay(delay)
                loadingDialog = LoadingDialog(context, text).apply {
                    showDialog()
                }
            }
        }
    }

    override fun handleFailureDelegation(
        fragment: Fragment,
        statusCode: Int,
        message: String?,
        messageType: MessageType,
        payload: String?
    ) {
        if (isHttpStatusUnauthorized(statusCode)) return

        if (isUnsafeConnection(statusCode)) return

        val failureMessage = "failerMessage"


        showErrorDelegation(fragment,messageType, failureMessage)
    }

    override fun handleSuccessDelegation(
        fragment: Fragment,
        message: String,
        messageType: MessageType
    ) {
        when (messageType) {
            MessageType.NONE -> Unit
            MessageType.DIALOG -> {

            }
            MessageType.SNACK_BAR -> {

            }
            else -> {
                //no*op
            }
        }
    }

    private fun isHttpStatusUnauthorized(statusCode: Int): Boolean {
        if (statusCode == HttpStatus.UNAUTHORIZED) {


            return true
        }

        return false
    }

    private fun isExistException(exception: Throwable, retryCall: (() -> Unit)?): Boolean {
        return when (exception) {
            is IOException -> {
                isExistIOException(exception, retryCall)
            }
            else -> false
        }
    }

    private fun isExistIOException(exception: IOException, retryCall: (() -> Unit)?): Boolean {
        return when (exception) {
            is SocketTimeoutException -> {
                showSocketTimeoutMessage(retryCall)
                true
            }
            else -> {
                showNoInternetConnectionMessage(retryCall)
                true
            }
        }
    }

    private fun showSocketTimeoutMessage(retryCall: (() -> Unit)?) {

    }

    private fun showNoInternetConnectionMessage(retryCall: (() -> Unit)?) {

    }
}
