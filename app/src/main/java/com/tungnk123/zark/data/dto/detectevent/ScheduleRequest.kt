package com.tungnk123.zark.data.dto.detectevent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRequest(
    val text: String,
    @SerialName("current_datetime")
    val currentDatetime: String,
)