package com.tungnk123.zark.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.repository.event.EventRepository
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
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
}