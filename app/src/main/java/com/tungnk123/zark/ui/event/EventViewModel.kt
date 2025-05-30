package com.tungnk123.zark.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.repository.event.EventRepository
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "EventViewModel"
    }

    fun createEvent(event: CreateEventRequest) {
        "Event request: $event".printLog("test_event")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = eventRepository.createEvent(event)
                "Response: $response".printLog("test_event")
            }
            catch (e: Exception) {
                e.printException(TAG)
            }

        }
    }
}