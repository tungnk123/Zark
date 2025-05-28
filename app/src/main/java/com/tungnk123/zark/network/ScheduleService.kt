package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.data.dto.detectevent.ScheduleResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleService {
    @POST("schedule")
    suspend fun processText(@Body request: ScheduleRequest): ScheduleResponse
}
