package com.tungnk123.zark.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.EventDetail
import com.tungnk123.zark.repository.event.EventRepository
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    companion object {
        private const val TAG = "CalendarViewModel"
    }

    private val _events = MutableStateFlow<List<EventDetail>>(emptyList())
    val events: StateFlow<List<EventDetail>> = _events

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

    fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = tokenManager.userId.firstOrNull() ?: return@launch
                val response = eventRepository.getEventsByUserId(currentUserId)
                "Get events: $response".printLog("test_event")
                _events.value = response.message
            }
            catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }
}
