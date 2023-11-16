package com.wl.common.util

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateTimeUtil {
    private const val DAY_WITH_WEEK_PATTERN = "dd EEE"
    private const val MONTH_YEAR_PATTERN = "MMM YYYY"

    fun format(date: Date, pattern: String): String {
        return SimpleDateFormat(pattern).format(date)
    }

    fun dayWithWeekFormat(date: Date) = format(date, DAY_WITH_WEEK_PATTERN)
    fun monthYearFormat(date: Date) = format(date, MONTH_YEAR_PATTERN)
}

fun Date.format(pattern: String): String {
    return DateTimeUtil.format(this, pattern)
}

fun Date.dayWithWeekFormat() = DateTimeUtil.dayWithWeekFormat(this)
fun Date.monthYearFormat() = DateTimeUtil.monthYearFormat(this)
fun Date.toLocalDayString(timeStyle: Int = DateFormat.SHORT): String {
    return DateFormat.getDateInstance(timeStyle).format(this)
}

fun Date.toLocalDate(): LocalDate {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    return LocalDate.of(
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH] + 1,
        calendar[Calendar.DAY_OF_MONTH]
    )
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

fun Long.endOfTheMonth(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this.endOfTheDay()
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    return calendar.timeInMillis
}

fun Long.startOfTheMonth(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this.startOfTheDay()
    calendar.set(Calendar.DAY_OF_MONTH, 0)
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

fun LocalDate.toTimeMillis(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, monthValue - 1)
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    return calendar.timeInMillis.startOfTheDay()
}