package com.fy.extension

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView




fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
val View.layoutInflater get() = context.layoutInflater


fun View.hideSoftInput() {
    context.inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

infix fun RecyclerView.smoothScrollTo(index : Int?){
    index?.let {
        this.smoothScrollToPosition(index)
        return
    }
    this.smoothScrollToPosition(0)
}
