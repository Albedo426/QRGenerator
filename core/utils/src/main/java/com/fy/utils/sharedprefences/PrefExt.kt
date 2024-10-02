package com.fy.utils.sharedprefences

import android.content.SharedPreferences

inline fun <reified T> SharedPreferences.set(key: String, value: T?, formatter: JsonFormatter) {
    val editor = this.edit()
    if (value == null) {
        editor.remove(key)
    } else {
        when (T::class) {
            String::class -> editor.putString(key, value as String)
            Int::class -> editor.putInt(key, value as Int)
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Float::class -> editor.putFloat(key, value as Float)
            Long::dec -> editor.putLong(key, value as Long)
            else -> {
                val encoded = formatter.encodeToString<T>(value)
                editor.putString(key, encoded)
            }
        }
    }
    editor.apply()
}

inline fun <reified T : Any> SharedPreferences.get(
    key: String, defaultValue: T? = null, jsonFormatter: JsonFormatter
): T? {
    if (this.contains(key).not()){
        return null
    }

    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> {
            val json = getString(key, "").orEmpty()
            jsonFormatter.decodeFromString<T>(json,T::class.java)
        }
    }
}