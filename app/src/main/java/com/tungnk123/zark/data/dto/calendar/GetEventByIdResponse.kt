package com.tungnk123.zark.data.dto.calendar

import kotlinx.serialization.Serializable

@Serializable
data class GetEventByIdResponse(
    val statusCode: Int,
    val message: EventDetail,
)
