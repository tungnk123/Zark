package com.tungnk123.zark.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import com.tungnk123.zark.repository.message.MessageRepository
import com.tungnk123.zark.utils.extensions.printException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

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

    fun sendMessage(
        conversationId: Int,
        senderId: Int,
        content: String,
        type: String = "Text"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
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

