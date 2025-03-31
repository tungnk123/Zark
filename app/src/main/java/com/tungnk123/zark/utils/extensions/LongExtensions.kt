package com.tungnk123.zark.utils.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Long.toLocalDate(): LocalDate? {
    return if (this == 0L) null
    else Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}