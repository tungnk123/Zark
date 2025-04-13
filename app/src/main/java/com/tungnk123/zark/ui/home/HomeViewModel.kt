package com.tungnk123.zark.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.repository.chat.ChatRepository
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.ui.home.state.HomeUiState
import com.tungnk123.zark.utils.SignalRManager
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val signalRManager: SignalRManager
) : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    val chatEntities = chatRepository.observeChatEntities()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _incomingMessages = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val incomingMessages: StateFlow<List<Pair<Int, String>>> get() = _incomingMessages

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    fun loadContacts(userId: Int) {
        "LoadContacts".printLog("test_contact")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val contacts = userRepository.getContacts(userId)
                "Contacts: $contacts".printLog("test_contact")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    contacts = contacts,
                    error = null
                )
            }
            catch (e: Exception) {
                e.printException(TAG)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun startConnection() {
        viewModelScope.launch {
            signalRManager.startConnection(
                onReceiveMessage = { senderId, content ->
                    _incomingMessages.value = _incomingMessages.value + Pair(senderId, content)
                },
                onError = { error ->
                    error.printStackTrace()
                }
            )
            _isConnected.value = signalRManager.isConnected()
        }
    }

    fun sendMessage(senderId: Int, receiverId: Int, content: String) {
        signalRManager.sendMessage(senderId, receiverId, content)
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