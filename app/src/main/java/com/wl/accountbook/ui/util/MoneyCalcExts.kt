package com.wl.accountbook.ui.util

/**
 * store money in Long format, actualMoney * 100
 */
fun Long.toActualMoney(): Double {
    return this.toDouble() / 100.0
}

