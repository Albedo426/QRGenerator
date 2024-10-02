package com.fy.utils.model

import com.google.gson.annotations.SerializedName

class ApiResponse<out T>(
    @SerializedName("success")
    val isSuccess: Boolean? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("status_message")
    val message: String? = null,
    @SerializedName("status_code")
    val code: Long? = null,
    @SerializedName("payload")
    val payload: String? = null
)
