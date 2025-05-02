package com.tungnk123.zark.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import com.tungnk123.zark.repository.message.MessageRepository
import com.tungnk123.zark.utils.AppConstants
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    val currentUserId = tokenManager.userId.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(AppConstants.STOP_TIMEOUT),
        initialValue = null
    )

    private val _incomingMessages = MutableStateFlow<List<ChatMessageResponse>>(emptyList())
    val incomingMessages: StateFlow<List<ChatMessageResponse>> get() = _incomingMessages

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _chatList = MutableStateFlow<List<ChatMessageResponse>>(emptyList())
    val chatList: StateFlow<List<ChatMessageResponse>> get() = _chatList

    companion object {
        private const val TAG = "ChatViewModel"
    }

    init {
        startConnection()
    }

    fun startConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                messageRepository.startSignalRConnection(onReceiveMessage = { chatMessage ->
                    _incomingMessages.value = _incomingMessages.value + chatMessage
                    }, onError = {
                    it.printException(TAG)
                })
                _isConnected.value = true
            }
            catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }

    fun getAllMessages(
        conversationId: Int,
        page: Int? = null,
        size: Int? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val messages = messageRepository.getAllMessages(conversationId, page, size)
                _chatList.value = messages
            }
            catch (e: Exception) {
                e.printException(TAG)
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
                val senderId = tokenManager.userId.firstOrNull() ?: return@launch
                messageRepository.sendMessage(
                    conversationId = conversationId,
                    senderId = senderId,
                    content = content,
                    type = type
                )
            }
            catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }

    fun stopConnection() {
        viewModelScope.launch {
            messageRepository.disconnectSignalR()
        }
        _isConnected.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopConnection()
    }
}

