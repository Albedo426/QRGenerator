package com.fy.extension

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun String.toDate(): Date? {
    if (this.isEmpty()) return null
    val that = this.replace("'", "").replace(" ", "T")
    val fullFormat = when (that.length) {
        15, 16 -> DatePattern.yyyyMMddTHHmm
        12, 13 -> DatePattern.yyyyMMddTHH
        10 -> DatePattern.yyyyMMdd_ServerFormat
        8 -> DatePattern.yyyyMMdd_WithoutSeparator
        14 -> DatePattern.yyyyMMddHHmmss_WithoutSeparator
        else -> DatePattern.yyyyMMddTHHmmss_JsonFormat
    }
    return that.toDate(fullFormat, Locale.US)
}

private fun String.toDate(format: String): Date? {
    return toDate(format, Locale.getDefault()) //fixme
}

private fun String.toDate(format: String, locale: Locale): Date? {
    return try {
        SimpleDateFormat(format, locale).parse(this)
    } catch (ex: Exception) {
        null
    }
}

// Pattern: dd.MM.yyyy
fun String.toDateFromViewFormat(): Date? = toDate(DatePattern.ddMMyyyy_ViewFormat)

// Pattern: MM/yyyy
fun String.toDateFromSlashFormat(): Date? = toDate(DatePattern.MMyyyy_Slash, Locale.US)

// Pattern: yyyy-MM-dd HH:mm:ss
fun Date.formatToServerDateTime(): String =
    this.formatTo(DatePattern.yyyyMMddHHmmss_ServerFormat, Locale.US)

// Pattern: yyyy-MM-dd'T'HH:mm:ss
fun Date.formatToJson(): String = this.formatTo(DatePattern.yyyyMMddTHHmmss_JsonFormat, Locale.US)

// Pattern: yyyy-MM-dd
fun Date.formatToServerDate(): String = this.formatTo(DatePattern.yyyyMMdd_ServerFormat, Locale.US)

// Pattern: HH:mm
fun Date.formatToTime(): String = this.formatTo(DatePattern.HHmm, Locale.US)

// Pattern: EEEE
fun Date.formatToDayOfWeek(): String = this.formatTo(DatePattern.EEEE)

// Pattern: dd.MM.yyyy HH:mm
fun Date.formatToViewDateTime(): String = this.formatTo(DatePattern.ddMMyyyyHHmmss, Locale.US)

// Pattern: 1 August 2020
fun Date.formatToViewDateLong(): String = this.formatTo(DatePattern.ddMMMMyyyy)

// Pattern: 1 August 2020 20:20
fun Date.formatToViewDateTimeLong(): String = this.formatTo(DatePattern.ddMMMMyyyyHHmm)

// Pattern: 1 August 2020 Sunday 20:20
fun Date.formatToViewDateTimeLongWithDay(): String = this.formatTo(DatePattern.ddMMMMyyyyEEEEHHmm)

// Pattern: 1 Aug 2020
fun Date.formatToViewDateShort(): String = this.formatTo(DatePattern.ddMMMyyyy)

// Pattern: August 2020
fun Date.formatToViewDateShortWithoutDay(): String = this.formatTo(DatePattern.MMMMyyyy)

// Pattern: dd.MM.yyyy
fun Date.formatToViewDate(): String = this.formatTo(DatePattern.ddMMyyyy_ViewFormat, Locale.US)

// Pattern: yyyyMM
fun Date.formatToYearPeriod(): String =
    this.formatTo(DatePattern.yyyyMM_WithoutSeparator, Locale.US)

// Pattern: yyyy
fun Date.formatToYear(): String = this.formatTo(DatePattern.YYYY, Locale.US)

// Pattern: MM
fun Date.formatToMonth(): String = this.formatTo(DatePattern.MM, Locale.US)

// Pattern: MMMM
fun Date.formatToMonthName(): String = this.formatTo(DatePattern.MMMM)

// Pattern: given pattern
private fun Date.formatTo(pattern: String): String {
    return formatTo(pattern, Locale.getDefault())
}

private fun Date.formatTo(pattern: String, locale: Locale): String {
    return SimpleDateFormat(pattern, locale).format(this)
}

//Add field date to current date
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

// minus field date to current date
fun Date.minus(field: Int, amount: Int): Date = this@minus.add(field, (amount * -1))

fun Date.minusYears(years: Int): Date {
    return minus(Calendar.YEAR, years)
}

fun Date.minusMonths(months: Int): Date {
    return minus(Calendar.MONTH, months)
}

fun Date.minusDays(days: Int): Date {
    return minus(Calendar.DAY_OF_MONTH, days)
}

fun Date.minusHours(hours: Int): Date {
    return minus(Calendar.HOUR_OF_DAY, hours)
}

fun Date.minusMinutes(minutes: Int): Date {
    return minus(Calendar.MINUTE, minutes)
}

fun Date.minusSeconds(seconds: Int): Date {
    return minus(Calendar.SECOND, seconds)
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}


fun Date.truncateTime() = formatToServerDate().toDate()!!
fun Date.isToday() = DateUtils.isToday(truncateTime().time)
fun Date.isYesterday() = DateUtils.isToday(truncateTime().addDays(1).time)
fun Date.isTomorrow() = DateUtils.isToday(truncateTime().minusDays(1).time)

fun Date.dateIntervalFromYearAgo(): Int {
    val givenDate = Calendar.getInstance()
    givenDate.time = this
    val year = givenDate.get(Calendar.YEAR)

    val currentCalendar = Calendar.getInstance()
    val currentYear = currentCalendar.get(Calendar.YEAR)

    return currentYear - year
}

fun Date.dateIntervalFromMonthAgo(): Int {
    val givenDate = Calendar.getInstance()
    givenDate.time = this
    val month = givenDate.get(Calendar.MONTH)

    val currentCalendar = Calendar.getInstance()
    val currentMonth = currentCalendar.get(Calendar.MONTH)

    return currentMonth - month
}

fun Date.dateIntervalFromDayOfMonthAgo(): Long {
    val endOfDate = Date().formatTo("yyyy-MM-dd 23:59:59").toDate()
    val diff = (endOfDate ?: Date()).time - this.time
    return TimeUnit.MILLISECONDS.toDays(diff)
}

fun Date.dateIntervalFromHourOfDayAgo(): Long {
    val diff = Date().time - this.time
    return diff / (1000 * 60 * 60)
}

fun Date.dateIntervalFromMinuteAgo(): Long {
    val diff = Date().time - this.time
    return diff / (1000 * 60)
}

fun Date.dateIntervalFromSecondAgo(): Long {
    val diff = Date().time - this.time
    return diff / 1000
}

fun Date.dateIntervalFromMilisAgo(): Long {
    return Date().time - this.time
}
//
//fun Long.milistoFormattedString(): String {
//    val date = Date(this)
//    return date.minutesAgo()
//}
//
//fun Long.milisFormattedForChat(): String {
//    val date = Date(this)
//    return date.timeAgoForChat()
//}

fun Long.hourFormat(): String {
    val date = Date(this)
    return date.formatToTime()
}

fun Date.areDatesWithinSameDateGroup(): Boolean {
    val givenDate = Calendar.getInstance()
    givenDate.time = this
    val year = givenDate.get(Calendar.YEAR)
    val month = givenDate.get(Calendar.MONTH)
    val dayOfMonth = givenDate.get(Calendar.DAY_OF_MONTH)

    val currentCalendar = Calendar.getInstance()
    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH)

    return currentYear == year && currentMonth == month && currentDayOfMonth == dayOfMonth
}

//fun Date.dateAgoForChat(): String {
//    return when (dateIntervalFromDayOfMonthAgo()) {
//        0L -> "Today".toLocalize()
//        1L-> "Yesterday".toLocalize()
//        in 2..7 -> formatToDayOfWeek()
//        else -> formatToViewDate()
//    }
//}

//fun Date.timeAgoForChat(): String {
//    return when (dateIntervalFromDayOfMonthAgo()) {
//        0L -> formatToTime()
//        1L-> "Yesterday".toLocalize()
//        in 2..7 -> formatToDayOfWeek()
//        else -> formatToViewDate()
//    }
//}

//fun Date.yearAgo(): String {
//    return when (val yearAgo = this.dateIntervalFromYearAgo()) {
//        0 -> this.formatToViewDate()
//        1 -> "YearAgo".toLocalize()
//        else -> "YearsAgo".toLocalize(yearAgo.toString())
//    }
//}

//fun Date.monthAgo(includeYearAgo: Boolean = true): String {
//    return if (includeYearAgo) {
//        when (val monthAgo = this.dateIntervalFromMonthAgo()) {
//            0 -> this.formatToViewDate()
//            1 -> "MonthAgo".toLocalize()
//            in 2..11-> "MonthsAgo".toLocalize(monthAgo.toString())
//            else -> this.yearAgo()
//        }
//    } else {
//        this.formatToViewDate()
//    }
//}
//
//fun Date.dayOfMonthAgo(includeYearAgo: Boolean = true): String {
//    return when {
//        isToday() -> "todayGroupTitle".toLocalize()
//        isYesterday() -> "yesterdayGroupTitle".toLocalize()
//        else -> this.monthAgo(includeYearAgo)
//    }
//}
//
//fun Date.todayOrYesterdayDayAgoWithDateTime(): String {
//    return when {
//        isToday() -> "Today".toLocalize() + ", " + formatToTime()
//        isYesterday() -> "Yesterday".toLocalize() + ", " + formatToTime()
//        else -> this.formatToViewDateTime()
//    }
//}
//
//fun Date.todayOrYesterdayDayAgoWithLongDate(): String {
//    return when {
//        isToday() -> "todayGroupTitle".toLocalize()
//        isYesterday() -> "yesterdayGroupTitle".toLocalize()
//        else -> formatToViewDateLong()
//    }
//}
//
//fun Date.hourOfDayAgo(): String {
//    return when (val hourOfDayAgo = this.dateIntervalFromHourOfDayAgo()) {
//        1L -> "xHourAgo".toLocalize("1")
//        in 2..23 -> "xHourAgo".toLocalize(hourOfDayAgo.toString())
//        else -> this.formatToTime()
//    }
//}
//
//fun Date.hourOfDayAgoWithDateTime(): String {
//    return when (val hourOfDayAgo = this.dateIntervalFromHourOfDayAgo()) {
//        1L -> "xHourAgo".toLocalize("1")
//        in 2..23 -> "xHourAgo".toLocalize(hourOfDayAgo.toString())
//        else -> this.formatToViewDateTimeLong()
//    }
//}
//
//fun Date.minutesAgo(): String {
//    return when (val minutesAgo = this.dateIntervalFromMinuteAgo()) {
//        1L -> "MinuteAgo".toLocalize("1")
//        in 2..59 -> "MinutesAgo".toLocalize(minutesAgo.toString())
//        else -> this.hourOfDayAgo()
//    }
//}
//
//fun Date.minutesAgoWithDateTime(): String {
//    return when (val minutesAgo = this.dateIntervalFromMinuteAgo()) {
//        1L -> "MinuteAgo".toLocalize("1")
//        in 2..59 -> "MinutesAgo".toLocalize(minutesAgo.toString())
//        else -> this.hourOfDayAgoWithDateTime()
//    }
//}
//
//fun Date.secondAgo(): String {
//    return when (val secondAgo = this.dateIntervalFromSecondAgo()) {
//        1L -> "ASecondAgo".toLocalize()
//        in 2..59 -> "SecondsAgo".toLocalize(secondAgo.toString())
//        else -> this.minutesAgo()
//    }
//}
//
//fun Date.secondAgoWithDateTime(): String {
//    return when (val secondAgo = this.dateIntervalFromSecondAgo()) {
//        1L -> "ASecondAgo".toLocalize()
//        in 2..59 -> "SecondsAgo".toLocalize(secondAgo.toString())
//        else -> this.minutesAgoWithDateTime()
//    }
//}

//fun Date.timeAgo(): String {
//    return secondAgo()
//}
//
//fun Date.dateTimeAgo(): String {
//    return secondAgoWithDateTime()
//}
//
//fun Date.gmt0(): Long {
//    val dateFormat = "yyyy-MM-dd HH:mm:ss.sss";
//    val format1 = SimpleDateFormat(dateFormat, LocaleManager.getLocale())
//    val d1 = format1.format(this)
//    format1.timeZone = TimeZone.getTimeZone("GMT+0")
//    val d2 = format1.parse(d1)
//    return format1.parse(format1.format(this))?.time ?: 0L
//}


private object DatePattern {
    const val DB_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val yyyyMMddTHHmmss_JsonFormat = "yyyy-MM-dd'T'HH:mm:ss"
    const val yyyyMMddTHHmm = "yyyy-MM-dd'T'HH:mm"
    const val yyyyMMddTHH = "yyyy-MM-dd'T'HH"
    const val ddMMyyyyHHmmss = "dd.MM.yyyy HH:mm:ss"
    const val ddMMMMyyyy = "d MMMM yyyy"
    const val ddMMMMyyyyHHmm = "d MMMM yyyy HH:mm"
    const val ddMMMMyyyyEEEEHHmm = "d MMMM yyyy EEEE HH:mm"
    const val ddMMMyyyy = "dd MMM yyyy"
    const val yyyyMMddHHmmss_ServerFormat = "yyyy-MM-dd HH:mm:ss"
    const val ddMMyyyy = "dd-MM-yyyy"
    const val ddMMyyyy_ViewFormat = "dd.MM.yyyy"
    const val yyyyMMdd_ServerFormat = "yyyy-MM-dd"
    const val yyyyMMddHHmmss_WithoutSeparator = "yyyyMMddHHmmss"
    const val HHmmss = "HH:mm:ss"
    const val HHmm = "HH:mm"
    const val MMyyyy_Slash = "MM/yyyy"
    const val yyyyMM_WithoutSeparator = "yyyyMM"
    const val yyyyMMdd_WithoutSeparator = "yyyyMMdd"
    const val MMMyyyy = "MMM yyyy"
    const val MMMMyyyy = "MMMM yyyy"
    const val MMMM = "MMMM"
    const val YYYY = "yyyy"
    const val MM = "MM"
    const val EEEE = "EEEE"
}