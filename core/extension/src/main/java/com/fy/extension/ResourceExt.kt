package com.fy.extension

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

fun Typeface?.getFont(context : Context, @FontRes resId: Int) {
     ResourcesCompat.getFont(context, resId)
}

fun Int?.getFont(context: Context,@FontRes resId: Int): Typeface? {
     return ResourcesCompat.getFont(context, resId)
}

