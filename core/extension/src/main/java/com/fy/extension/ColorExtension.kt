package com.fy.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Int.asColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun @receiver:ColorRes Int.asColorStateList(context: Context): ColorStateList? {
    return ContextCompat.getColorStateList(context, this)
}

val String?.asColor: Int
    get() {
        return try {
            Color.parseColor(this)
        } catch (e: Exception) {
            Color.WHITE
        }
    }

fun String?.asColorWithDefault(context: Context, @ColorRes defaultColor: Int): Int {
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        defaultColor.asColor(context)
    }
}

fun ImageView.setColorTint(@ColorRes colorId: Int) {
    this.setColorFilter(ContextCompat.getColor(this.context, colorId))
}
