package com.tungnk123.zark.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import com.tungnk123.zark.data.dto.conversation.EncryptedSessionKey
import com.tungnk123.zark.data.dto.user.UserDto
import com.tungnk123.zark.repository.conversation.ConversationRepository
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.ui.search.state.SearchUiState
import com.tungnk123.zark.utils.AppConstants
import com.tungnk123.zark.utils.TokenManager
import com.tungnk123.zark.utils.extensions.printException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _navigateToConversation = MutableSharedFlow<Int>()
    val navigateToConversation = _navigateToConversation.asSharedFlow()


    init {
        viewModelScope.launch {
            _query.debounce(AppConstants.DEBOUNCE_QUERY)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchAll(query)
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery

        if (newQuery.isBlank()) {
            _uiState.update {
                it.copy(
                    searchContacts = emptyList(),
                    searchUsers = emptyList(),
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun searchAll(query: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val conversationDeferred = async { conversationRepository.searchConversationByQuery(query) }
                val userDeferred = async { searchUsers(query) }

                val searchResults = conversationDeferred.await()
                val userResults = userDeferred.await()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        searchContacts = searchResults,
                        searchUsers = userResults
                    )
                }
            }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                e.printException(TAG)
            }
        }
    }

    fun searchConversationByQuery(query: String) {
        _uiState.update {
            _uiState.value.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val searchResults = conversationRepository.searchConversationByQuery(query)
                _uiState.update {
                    _uiState.value.copy(
                        isLoading = false,
                        searchContacts = searchResults
                    )
                }
            }
            catch (e: Exception) {
                _uiState.update {
                    _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                e.printException(TAG)
            }
        }
    }

    private suspend fun searchUsers(query: String) = try {
        val response = userRepository.searchUsers(
            name = query,
            email = query,
            page = 1,
            pageSize = 20
        )
        response.data
    } catch (e: Exception) {
        e.printException("$TAG - searchUsers")
        emptyList()
    }

    fun searchUsersByName(name: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.searchUsers(
                    name = name,
                    page = 1,
                    pageSize = 20
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        searchUsers = response.data
                    )
                }
            }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                e.printException(TAG)
            }
        }
    }

    fun searchUsersByEmail(email: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.searchUsers(
                    email = email,
                    page = 1,
                    pageSize = 20
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        searchUsers = response.data
                    )
                }
            }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                e.printException(TAG)
            }
        }
    }

    suspend fun createConversation(request: CreateConversationRequest): Int? {
        return try {
            conversationRepository.createConversation(request).conversationId
        }
        catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = e.message
                )
            }
            e.printException(TAG)
            null
        }
    }

    fun startNewConversationWithUser(user: UserDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUserId = tokenManager.userId.firstOrNull() ?: return@launch
            val request = CreateConversationRequest(
                creatorId = currentUserId,
                participantIds = listOf(user.id),
                type = "Private",
                name = user.displayName,
                encryptedSessionKeys = listOf(
                    EncryptedSessionKey(
                        userId = user.id,
                        encryptedSessionKey = "dummy-key" // Replace with real key later
                    ),
                    EncryptedSessionKey(
                        userId = currentUserId,
                        encryptedSessionKey = "dummy-key" // Replace with real key later
                    )
                )
            )
            val conversationId = createConversation(request)
            if (conversationId != null) {
                _navigateToConversation.emit(conversationId)
            }
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}