package com.wl.data.util

import kotlin.math.pow

object MoneyUtils {
    private val multiplier = 10.0.pow(Constants.MAX_DECIMAL_LENGTH).toLong()

    fun transToCalcMoney(integer: String, decimal: String): Long {
        var calcDecimal = 0L
        if (decimal.isNotEmpty()) {
            calcDecimal = (decimal + "0".repeat(Constants.MAX_DECIMAL_LENGTH - decimal.length)).toLong()
        }
        return (integer.ifEmpty { "0" }).toLong() * multiplier + calcDecimal
    }

    fun transToShowMoney(number: Long): Pair<String, String> {
        val intVal = number / multiplier
        val decimalVal = number - intVal * multiplier

        val integer = intVal.toString().take(Constants.MAX_NUMBER_LENGTH)
        val decimal = if (decimalVal > 0) {
            val str = decimalVal.toString()
            ("0".repeat(Constants.MAX_DECIMAL_LENGTH - str.length) + str).dropLastWhile { it == '0' }
        } else ""
        return Pair(integer, decimal)
    }
}