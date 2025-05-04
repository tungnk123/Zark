package com.tungnk123.zark.ui.home.state

import com.tungnk123.zark.data.dto.conversation.ConversationResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val contacts: List<ConversationResponse> = emptyList(),
    val error: String? = null,
)
