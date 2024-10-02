package com.fy.extension

import android.content.Context
import androidx.core.content.res.ResourcesCompat

const val INVALID_INDEX = -1

fun Int.asDimensionFloat(context: Context): Float {
    return ResourcesCompat.getFloat(context.resources, this)
}

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0
