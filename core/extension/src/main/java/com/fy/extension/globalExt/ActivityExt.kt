package com.fy.extension.globalExt

import android.app.Activity
import androidx.window.layout.WindowMetricsCalculator

val Activity.screenHeight : Int
get() {
    return WindowMetricsCalculator
        .getOrCreate()
        .computeCurrentWindowMetrics(this)
        .bounds.height()
}

val Activity.screenWidth : Int
get() {
    return WindowMetricsCalculator
        .getOrCreate()
        .computeCurrentWindowMetrics(this)
        .bounds.width()
}