package com.fy.extension

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.roundToInt

val displayMetrics: DisplayMetrics get() = Resources.getSystem().displayMetrics

val screenDensityDpi get() = displayMetrics.densityDpi

val screenWidthInPx: Int get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeightInPx: Int get() = Resources.getSystem().displayMetrics.heightPixels

val screenDensity: Float get() = Resources.getSystem().displayMetrics.density

val screenWidthInDp: Int get() = (screenWidthInPx / screenDensity).roundToInt()

val screenHeightInDp: Int get() = (screenHeightInPx / screenDensity).roundToInt()

val deviceScreenResolution: String get() = "($screenWidthInPx)x($screenHeightInPx)"

fun spToPx(sp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    ).toInt()
}
