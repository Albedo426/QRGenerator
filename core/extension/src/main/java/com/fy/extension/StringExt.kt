package com.fy.extension

import java.security.MessageDigest
import java.util.*

const val EMPTY_STRING = ""

fun String.md5(): String {
    return hashString(this, "MD5")
}

fun hashString(input: String, algorithm: String): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold(EMPTY_STRING) { str, result -> str + "%02x".format(result) }
}

fun String.toLowerCaseEn(): String { return this.lowercase(Locale.ENGLISH) }

fun String.toUpperCaseEn(): String { return this.uppercase(Locale.ENGLISH) }

//fun String.toLowerCaseLocal(): String { return this.lowercase(LocaleManager.getLocale()) }
//
//fun String.toUpperCaseLocal(): String { return this.uppercase(LocaleManager.getLocale()) }

fun String.toLowerCaseInvariant(): String {
    return this.clearTurkishChars().toLowerCaseEn()
}

fun String.toUpperCaseInvariant(): String {
    return this.clearTurkishChars().toUpperCaseEn()
}

fun String.clearTurkishChars(): String {
    return this
        .replace('İ', 'I')
        .replace('Ş', 'S')
        .replace('Ğ', 'G')
        .replace('Ç', 'C')
        .replace('Ö', 'O')
        .replace('Ü', 'U')
        .replace('ı', 'i')
        .replace('ş', 's')
        .replace('ğ', 'g')
        .replace('ç', 'c')
        .replace('ö', 'o')
        .replace('ü', 'u')
}

fun String.clearSpaces() = this.replace(" ", "")

fun String.onlyDigits(): String {
    return this.filter { it.isDigit() }
}

fun getSentencesLetter(fullName: String): String {
    if (fullName.length == 1) {
        return fullName
    }
    if (fullName.trim().isEmpty()) {
        return "?"
    }

    val list = fullName.trim().split(" ")
    return if (list.size == 1) {
        list[0][0].toString().uppercase()
        //fixme uppercase(LocaleManager.getLocale())
    } else {
        "${list[0][0]}${list[list.size - 1][0]}".uppercase()
        //fixme uppercase(LocaleManager.getLocale())
    }
}

inline fun String?.ifNotEmpty(crossinline block: (String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        block(this)
    }
}
