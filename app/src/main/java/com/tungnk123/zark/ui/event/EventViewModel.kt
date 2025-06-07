package com.tungnk123.zark.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.repository.event.EventRepository
import com.tungnk123.zark.ui.event.state.EventDetailUiState
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    companion object {
        private const val TAG = "EventViewModel"
    }

    private val _eventDetailUiState = MutableStateFlow(EventDetailUiState())
    val eventDetailUiState: StateFlow<EventDetailUiState> = _eventDetailUiState.asStateFlow()

    fun createEvent(
        title: String,
        description: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        participants: List<Int>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = tokenManager.userId.firstOrNull() ?: return@launch
                val createEventRequest = CreateEventRequest(
                    creatorId = currentUserId,
                    title = title,
                    description = description,
                    startTime = startTime,
                    endTime = endTime,
                    participants = participants
                )
                "Event request: $createEventRequest".printLog("test_event")
                val response = eventRepository.createEvent(createEventRequest)
                "Response: $response".printLog("test_event")
            }
            catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }

    fun getEventDetail(eventId: String) {
        viewModelScope.launch {
            _eventDetailUiState.value = EventDetailUiState(isLoading = true)

            try {
                val response = eventRepository.getEventByEventId(eventId)
                if (response.statusCode == 200) {
                    _eventDetailUiState.value = EventDetailUiState(
                        eventDetail = response.message
                    )
                }
                else {
                    _eventDetailUiState.value = EventDetailUiState(
                        error = "Failed to load event: ${response.message}"
                    )
                }
            }
            catch (e: Exception) {
                _eventDetailUiState.value = EventDetailUiState(
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun checkDoneEventByEventId(eventId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _eventDetailUiState.update { currentState ->
                    currentState.copy(
                        eventDetail = currentState.eventDetail?.copy(status = true),
                        isDone = true
                    )
                }

                val result = eventRepository.checkDoneEventByEventId(eventId)

                if (result.statusCode != 200) {
                    _eventDetailUiState.update { currentState ->
                        currentState.copy(
                            eventDetail = currentState.eventDetail?.copy(status = false),
                            isDone = false,
                            error = "Failed to mark event as done"
                        )
                    }
                }
            } catch (e: Exception) {
                _eventDetailUiState.update { currentState ->
                    currentState.copy(
                        eventDetail = currentState.eventDetail?.copy(status = false),
                        isDone = false,
                        error = e.message ?: "Failed to mark event as done"
                    )
                }
                e.printException(TAG)
            }
        }
    }
}