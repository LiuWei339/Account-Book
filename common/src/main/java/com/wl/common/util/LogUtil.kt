package com.wl.common.util

import android.util.Log

object LogUtil {

    private const val LOG_PREFIX = "Account_book"

    private fun addPrefix(tag: String) = "$LOG_PREFIX, $tag"

    fun d(tag: String, msg: String) {
        Log.d(addPrefix(tag), msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(addPrefix(tag), msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(addPrefix(tag), msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(addPrefix(tag), msg)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        Log.d(addPrefix(tag), msg, tr)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        Log.i(addPrefix(tag), msg, tr)
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        Log.w(addPrefix(tag), msg, tr)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        Log.e(addPrefix(tag), msg, tr)
    }
}