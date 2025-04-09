package com.tungnk123.zark.utils.extensions

fun Exception.printException(tag: String = "Print-Exception") {
    this.printStackTrace()
    this.toString()
        .printLog(tag)
}