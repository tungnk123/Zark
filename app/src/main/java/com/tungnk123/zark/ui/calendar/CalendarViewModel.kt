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

    fun processText(text: String) {
        "Process text: $text".printLog("test_detect")
        viewModelScope.launch {
            try {
                val currentTime = OffsetDateTime.now().withOffsetSameInstant(java.time.ZoneOffset.ofHours(7))
                val formattedNow = currentTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                val request = ScheduleRequest(
                    text = text,
                    currentDatetime = formattedNow
                )

                val response = scheduleRepository.processText(request)

                "Response: $response".printLog("test_detect")

                val startTime = OffsetDateTime.parse(response.time).toLocalDateTime()
                val endTime = startTime.plusHours(1)

//                val newEvent = CalendarDto(
//                    id = UUID.randomUUID().toString(),
//                    title = response.event,
//                    startTime = startTime,
//                    endTime = endTime
//                )
//
//                addEvent(newEvent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
