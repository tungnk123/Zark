package com.tungnk123.zark.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.EventDetail
import com.tungnk123.zark.repository.schedule.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventDetail>>(emptyList())
    val events: StateFlow<List<EventDetail>> = _events

    init {
        loadSampleEvents()
    }

    fun addEvent(event: EventDetail) {
        _events.update { it + event }
    }

    private fun loadSampleEvents() {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            val sampleEvents = listOf(
                EventDetail(
                    id = "1",
                    title = "Team Meeting",
                    startTime = now.withHour(14)
                        .withMinute(30),
                    endTime = now.withHour(15)
                        .withMinute(30),
                    creatorId = 23,
                    creatorDisplayName = "Tasha Hooper",
                    creatorAvatar = "liber",
                    description = "offendit",
                    participants = listOf(),
                ),
                EventDetail(
                    id = "2",
                    title = "Code Review",
                    startTime = now.withHour(9)
                        .withMinute(0),
                    endTime = now.withHour(10)
                        .withMinute(0),
                    creatorId = 23,
                    creatorDisplayName = "Pearl Rocha",
                    creatorAvatar = "hendrerit",
                    description = "pericula",
                    participants = listOf(),
                )
            )
            _events.value = sampleEvents
        }
    }
}
