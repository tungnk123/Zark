package com.tungnk123.zark.data.dto.conversation

import kotlinx.serialization.Serializable

@Serializable
data class CreateConversationRequest(
    val creatorId: Int,
    val participantIds: List<Int>,
    val type: String,
    val name: String
)