package com.tungnk123.zark.data.dto.calendar

import kotlinx.serialization.Serializable

@Serializable
data class GetEventResponse(
    val statusCode: Int,
    val message: List<EventDetail>,
)
