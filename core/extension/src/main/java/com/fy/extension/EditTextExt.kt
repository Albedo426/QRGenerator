package com.fy.extension

import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.requestFocusToInput(): Boolean {
    this.text?.toString().orEmpty().ifNotEmpty {  it ->
        this.setSelection(it.length)
    }
    val result = requestFocus()
    if (result) showSoftInput()
    return result
}

fun EditText.showSoftInput() = context.inputMethodManager.showSoftInput(this,
    InputMethodManager.SHOW_IMPLICIT)

fun EditText.clearFocusFromInput() {
    this.clearFocus()
    this.hideSoftInput()
}



