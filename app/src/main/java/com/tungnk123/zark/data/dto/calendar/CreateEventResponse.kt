package com.tungnk123.zark.data.dto.calendar

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateEventResponse(
    val statusCode: Int,
    val message: EventDetail,
)

@Serializable
data class EventDetail(
    val id: String = "",
    val creatorId: Int,
    val creatorDisplayName: String,
    val creatorAvatar: String? = null,
    val title: String,
    val description: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val participants: List<Int>,
)
