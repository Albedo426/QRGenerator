package com.fy.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadFromUrl(
    url: String? = "",
    defaultHolder: Int,
    shouldCircleCrop: Boolean = false
) {
    val requestBuilder = Glide.with(this.context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(defaultHolder)
            .error(defaultHolder)
    ).load(url)

    if (shouldCircleCrop) {
        requestBuilder.circleCrop()
    }
    requestBuilder.into(this@loadFromUrl)
}
