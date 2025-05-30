package com.tungnk123.zark.data.dto.calendar

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateEventRequest(
    val creatorId: Int,
    val title: String,
    val description: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val participants: List<Int> = emptyList(),
)
