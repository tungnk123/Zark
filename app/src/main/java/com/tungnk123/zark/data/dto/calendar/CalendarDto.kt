package com.tungnk123.zark.data.dto.calendar

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CalendarDto(
    val id: String = "",
    val title: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val description: String? = null,
)
