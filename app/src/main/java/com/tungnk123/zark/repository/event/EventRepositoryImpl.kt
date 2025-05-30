package com.tungnk123.zark.repository.event

import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.network.EventService
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventService: EventService,
) : EventRepository {
    override suspend fun createEvent(event: CreateEventRequest) = eventService.createEvent(event)
}