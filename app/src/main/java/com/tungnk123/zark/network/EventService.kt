package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.GenericResponse
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.data.dto.calendar.CreateEventResponse
import com.tungnk123.zark.data.dto.calendar.GetEventByIdResponse
import com.tungnk123.zark.data.dto.calendar.GetEventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {
    @POST("/api/Event")
    suspend fun createEvent(
        @Body event: CreateEventRequest,
    ): CreateEventResponse

    @GET("api/Event")
    suspend fun getEventsByUserId(
        @Query("userId") userId: Int,
    ): GetEventResponse

    @GET("api/Event/{eventId}")
    suspend fun getEventByEventId(
        @Path("eventId") eventId: String,
    ): GetEventByIdResponse

    @PUT("api/Event/{eventId}/markedDone")
    suspend fun checkDoneEventByEventId(
        @Path("eventId") eventId: String
    ): GenericResponse

}