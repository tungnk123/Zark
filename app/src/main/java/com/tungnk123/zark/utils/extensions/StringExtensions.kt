package com.tungnk123.zark.utils.extensions

import android.util.Log

fun String.withCase(sensitive: Boolean) = if (!sensitive) lowercase() else this

enum class LogLevel {
    VERBOSE, DEBUG, INFO, WARN, ERROR
}

fun String.printLog(tag: String = "Zark_Log", logLevel: LogLevel = LogLevel.DEBUG) {
    when (logLevel) {
        LogLevel.VERBOSE -> Log.v(tag, this)
        LogLevel.DEBUG -> Log.d(tag, this)
        LogLevel.INFO -> Log.i(tag, this)
        LogLevel.WARN -> Log.w(tag, this)
        LogLevel.ERROR -> Log.e(tag, this)
    }
}

inline fun <reified T : Enum<T>> String?.toEnum(defaultValue: T): T =
    if (this == null) {
        defaultValue
    } else {
        try {
            enumValueOf(this)
        } catch (e: IllegalArgumentException) {
            defaultValue
        }
    }
