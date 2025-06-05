package com.tungnk123.zark.ui.event.state

import com.tungnk123.zark.data.dto.calendar.EventDetail

data class EventDetailUiState(
    val isLoading: Boolean = false,
    val eventDetail: EventDetail? = null,
    val error: String? = null,
    val isDone: Boolean = false
)