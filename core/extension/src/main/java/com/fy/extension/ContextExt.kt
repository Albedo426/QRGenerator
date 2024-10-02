package com.fy.extension

import android.app.KeyguardManager
import android.content.Context
import android.media.AudioManager
import android.net.ConnectivityManager
import android.nfc.NfcManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

val Context.keyguardManager get() = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

val Context.inputMethodManager get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

val Context.connectivityManager get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.telephonyManager get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

val Context.audioManager get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

val Context.nfcManager get() = getSystemService(Context.NFC_SERVICE) as NfcManager


val Context.windowManager get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

/**
 * Extension method to provide quicker access to the [LayoutInflater] from [Context].
 */
val Context.layoutInflater get() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.color(id: Int) : Int = ContextCompat.getColor(this, id)
