package com.tungnk123.zark.data.dto.detectevent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val intent: Boolean,
    @SerialName("TIME")
    val time: String,
    @SerialName("EVENT")
    val event: String,
    @SerialName("DURATION")
    val duration: String,
)