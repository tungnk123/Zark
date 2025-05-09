package com.tungnk123.zark.data.dto.conversation

import kotlinx.serialization.Serializable

@Serializable
data class ConversationSearchResponse(
    val conversationId: Int? = null,
    val type: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val isNew: Boolean? = null,
    val userId: Int? = null
)
