package com.tungnk123.zark.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.repository.message.MessageRepository
import com.tungnk123.zark.ui.chat.state.ChatUiState
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val tokenManager: TokenManager
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

    override fun onCleared() {
        super.onCleared()
        stopConnection()
    }
}