package com.tungnk123.zark.ui.search.state

import com.tungnk123.zark.data.dto.conversation.ConversationSearchResponse
import com.tungnk123.zark.data.dto.user.UserDto

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchContacts: List<ConversationSearchResponse> = emptyList(),
    val searchUsers: List<UserDto> = emptyList(),
    val error: String? = null,
)
