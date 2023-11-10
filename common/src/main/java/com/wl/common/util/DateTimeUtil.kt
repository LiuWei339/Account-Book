package com.wl.common.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object DateTimeUtil {
    private const val DAY_WITH_WEEK_PATTERN = "dd EEE"
    private const val MONTH_YEAR_PATTERN = "MMM YYYY"

}

fun Date.format(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Long.endOfTheDay(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}

fun Long.startOfTheDay(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun Long.sameDay(timeStamp: Long): Boolean {
    return this.startOfTheDay() == timeStamp.startOfTheDay()
}

fun Long.millisLater(milliseconds: Long) = this + milliseconds

fun Long.tomorrow() = this.millisLater(DateUtils.DAY_IN_MILLIS)

fun Date.endOfTheDay(): Date {
    return time.endOfTheDay().toDate()
}

fun Date.startOfTheDay(): Date {
    return time.startOfTheDay().toDate()
}

fun Date.millisLater(milliseconds: Long) = Date(time + milliseconds)

fun Date.tomorrow() = this.millisLater(DateUtils.DAY_IN_MILLIS)