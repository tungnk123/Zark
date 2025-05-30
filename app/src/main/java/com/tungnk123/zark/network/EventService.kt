package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.data.dto.calendar.CreateEventResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EventService {
    @POST("/api/Event")
    suspend fun createEvent(
        @Body event: CreateEventRequest,
    ): CreateEventResponse
}