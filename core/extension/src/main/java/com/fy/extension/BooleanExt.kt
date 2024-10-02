package com.fy.extension

fun Boolean?.orFalse() = this ?: false

fun Any?.letOrFalse(): Boolean {
    return this != null
}

fun Any?.letOrTrue(): Boolean {
    return this == null
}