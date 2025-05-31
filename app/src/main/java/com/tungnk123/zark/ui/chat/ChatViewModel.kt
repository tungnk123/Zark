package com.tungnk123.zark.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.calendar.CreateEventRequest
import com.tungnk123.zark.data.dto.detectevent.ScheduleRequest
import com.tungnk123.zark.repository.event.EventRepository
import com.tungnk123.zark.repository.message.MessageRepository
import com.tungnk123.zark.repository.schedule.ScheduleRepository
import com.tungnk123.zark.ui.chat.state.ChatUiState
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val scheduleRepository: ScheduleRepository,
    private val eventRepository: EventRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState

    companion object {
        private const val TAG = "ChatViewModel"
    }

    init {
        viewModelScope.launch {
            val userId = tokenManager.userId.firstOrNull()
            _uiState.value = _uiState.value.copy(currentUserId = userId)
        }
        startConnection()
    }

    private fun updateState(update: ChatUiState.() -> ChatUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun startConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                messageRepository.startSignalRConnection(
                    onReceiveMessage = { message ->
                        updateState {
                            copy(incomingMessages = incomingMessages + message)
                        }
                    },
                    onError = {
                        it.printException(TAG)
                        updateState { copy(error = it.message) }
                    }
                )
                updateState { copy(isConnected = true) }
            }
            catch (e: Exception) {
                e.printException(TAG)
                updateState { copy(error = e.message) }
            }
        }
    }

    fun getAllMessages(
        conversationId: Int,
        page: Int? = null,
        size: Int? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            updateState {
                copy(
                    isLoading = true,
                    error = null
                )
            }
            try {
                val messages = messageRepository.getAllMessages(
                    conversationId,
                    page,
                    size
                )
                updateState {
                    copy(
                        chatList = messages,
                        isLoading = false
                    )
                }
            }
            catch (e: Exception) {
                e.printException(TAG)
                updateState {
                    copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun sendMessage(
        conversationId: Int,
        content: String,
        type: String = "Text"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val senderId = _uiState.value.currentUserId ?: return@launch
                messageRepository.sendMessage(
                    conversationId,
                    senderId,
                    content,
                    type
                )
            }
            catch (e: Exception) {
                e.printException(TAG)
                updateState { copy(error = e.message) }
            }
        }
    }

    fun stopConnection() {
        viewModelScope.launch {
            messageRepository.disconnectSignalR()
            updateState { copy(isConnected = false) }
        }
    }

    fun processText(text: String) {
        "Process text: $text".printLog("test_detect")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentTime = OffsetDateTime.now()
                    .withOffsetSameInstant(java.time.ZoneOffset.ofHours(7))
                val formattedNow = currentTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                val request = ScheduleRequest(
                    text = text,
                    currentDatetime = formattedNow
                )

                val response = scheduleRepository.processText(request)

                val matchedMessage = _uiState.value.incomingMessages
                    .lastOrNull { it.message == text }

                updateState {
                    copy(messageWithIntent = if (response.intent) matchedMessage else null)
                }

                "Response: $response".printLog("test_detect")

                val startTime = OffsetDateTime.parse(response.time)
                    .toLocalDateTime()
                val endTime = startTime.plusHours(1)

                createEvent(
                    title = response.event,
                    description = response.event,
                    startTime = startTime,
                    endTime = endTime,
                    participants = listOfNotNull(_uiState.value.currentUserId)
                )
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

    override fun onCleared() {
        super.onCleared()
        stopConnection()
    }
}