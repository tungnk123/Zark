package com.tungnk123.zark.repository.schedule

import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.network.ScheduleService
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleService: ScheduleService,
) : ScheduleRepository {
    override suspend fun processText(request: ScheduleRequest) =
        scheduleService.processText(request)
}