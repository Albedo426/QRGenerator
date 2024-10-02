package com.fy.utils.sharedprefences

import android.content.SharedPreferences

class SharedPrefManager constructor(private val preferences: SharedPreferences) {
    private val editor: SharedPreferences.Editor = preferences.edit()


    var isTestValue: Boolean
        get() = preferences.getBoolean(IS_OPEN_DASHBOARD, false)
        set(value) {
            editor.apply {
                putBoolean(IS_OPEN_DASHBOARD, value)
                commit()
            }
        }
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    companion object {
        const val IS_OPEN_DASHBOARD = "OPEN_DASHBOARD"
    }

}
