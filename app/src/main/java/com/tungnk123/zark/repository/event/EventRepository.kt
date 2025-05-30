package com.tungnk123.zark.repository.event

import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.data.dto.calendar.CreateEventResponse

interface EventRepository {
    suspend fun createEvent(event: CreateEventRequest): CreateEventResponse
}