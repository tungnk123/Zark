package com.tungnk123.zark.data.dto.conversation

data class ConversationSearchResponse(
    val conversationId: Int,
    val type: String,
    val name: String,
    val avatar: String,
    val isNew: Boolean,
    val userId: Int
)
