package com.tungnk123.zark.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.repository.conversation.ConversationRepository
import com.tungnk123.zark.ui.home.state.HomeUiState
import com.tungnk123.zark.utils.AppConstants
import com.tungnk123.zark.utils.extensions.printException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true)

            while (isActive) {
                try {
                    val contacts = conversationRepository.getConversations()
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, contacts = contacts, error = null
                    )
                }
                catch (e: Exception) {9
                    e.printException(TAG)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, error = e.message
                    )
                }
                delay(AppConstants.DELAY_POLLING_CONTACT)
            }
        }
    }
}