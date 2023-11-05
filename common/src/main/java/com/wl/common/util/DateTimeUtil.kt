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

fun Date.endOfTheDay(): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.time
}

fun Date.startOfTheDay(): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun Date.millisLater(milliseconds: Long) = Date(time + milliseconds)

fun Date.tomorrow() = this.millisLater(DateUtils.DAY_IN_MILLIS)