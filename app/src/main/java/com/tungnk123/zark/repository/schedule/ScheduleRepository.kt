package com.tungnk123.zark.repository.schedule

import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.data.dto.detectevent.ScheduleResponse

interface ScheduleRepository {
    suspend fun processText(request: ScheduleRequest): ScheduleResponse
}