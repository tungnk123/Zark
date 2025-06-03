package com.tungnk123.zark.repository.schedule

import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.network.DetectScheduleService
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val detectScheduleService: DetectScheduleService,
) : ScheduleRepository {
    override suspend fun processText(request: ScheduleRequest) =
        detectScheduleService.processText(request)
}