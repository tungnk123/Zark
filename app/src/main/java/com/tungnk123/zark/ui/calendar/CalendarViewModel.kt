package com.tungnk123.zark.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.CalendarDto
import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.repository.schedule.ScheduleRepository
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<CalendarDto>>(emptyList())
    val events: StateFlow<List<CalendarDto>> = _events

    init {
        loadSampleEvents()
    }

    fun addEvent(event: CalendarDto) {
        _events.update { it + event }
    }

    private fun loadSampleEvents() {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            val sampleEvents = listOf(
                CalendarDto(
                    id = "1",
                    title = "Team Meeting",
                    startTime = now.withHour(14).withMinute(30),
                    endTime = now.withHour(15).withMinute(30),
                ),
                CalendarDto(
                    id = "2",
                    title = "Code Review",
                    startTime = now.withHour(9).withMinute(0),
                    endTime = now.withHour(10).withMinute(0),
                )
            )
            _events.value = sampleEvents
        }
    }
}
