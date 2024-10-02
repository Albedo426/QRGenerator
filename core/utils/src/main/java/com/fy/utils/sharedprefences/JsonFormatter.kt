package com.fy.utils.sharedprefences

import java.lang.reflect.Type

interface JsonFormatter {

    fun <T> encodeToString(model : T) : String
    fun <T> decodeFromString(key : String, classOfT : Class<T>) : T?
    fun <T> decodeFromString(key : String, type : Type) : T?
 }