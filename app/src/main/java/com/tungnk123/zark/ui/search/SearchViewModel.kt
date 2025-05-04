package com.tungnk123.zark.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.repository.conversation.ConversationRepository
import com.tungnk123.zark.ui.search.state.SearchUiState
import com.tungnk123.zark.utils.AppConstants
import com.tungnk123.zark.utils.extensions.printException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        viewModelScope.launch {
            _query.debounce(AppConstants.DEBOUNCE_QUERY)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchConversationByQuery(query)
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
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

    companion object {
        private const val TAG = "SearchViewModel"
    }
}