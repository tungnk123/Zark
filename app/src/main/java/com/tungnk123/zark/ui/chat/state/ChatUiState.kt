package com.tungnk123.zark.ui.chat.state

import com.tungnk123.zark.data.dto.message.ChatMessageResponse

data class ChatUiState(
    val currentUserId: Int? = null,
    val chatList: List<ChatMessageResponse> = emptyList(),
    val incomingMessages: List<ChatMessageResponse> = emptyList(),
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val messageWithIntent: ChatMessageResponse? = null
)