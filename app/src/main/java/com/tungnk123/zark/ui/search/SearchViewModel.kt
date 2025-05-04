package com.tungnk123.zark.ui.search

import androidx.lifecycle.ViewModel
import com.tungnk123.zark.repository.conversation.ConversationRepository
import com.tungnk123.zark.ui.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()


}