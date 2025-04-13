package com.tungnk123.zark.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.ChatEntity
import com.tungnk123.zark.repository.chat.ChatRepository
import com.tungnk123.zark.utils.SignalRManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val signalRManager: SignalRManager,
    private val chatRepository: ChatRepository
) : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    private val _incomingMessages = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val incomingMessages: StateFlow<List<Pair<Int, String>>> get() = _incomingMessages

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _chatList = MutableStateFlow<List<ChatEntity>>(emptyList())
    val chatList: StateFlow<List<ChatEntity>> get() = _chatList

    init {
        observeChats()
    }

    private fun observeChats() {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.observeChatEntities()
                .catch { e -> e.printException(TAG) }
                .collectLatest { chats ->
                    _chatList.value = chats
                }
        }
    }

    fun startConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signalRManager.startConnection(
                    onReceiveMessage = { senderId, content ->
                        _incomingMessages.value = _incomingMessages.value + Pair(senderId, content)
                    },
                    onError = { error ->
                        "$error".printLog(TAG)
                        error.printStackTrace()
                    }
                )
                _isConnected.value = signalRManager.isConnected()
            } catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }

    fun sendMessage(
        senderId: Int,
        receiverId: Int,
        content: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signalRManager.sendMessage(senderId, receiverId, content)
            } catch (e: Exception) {
                e.printException(TAG)
            }
        }
    }

    fun stopConnection() {
        signalRManager.disconnect()
        _isConnected.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopConnection()
    }
}
