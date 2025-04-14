package com.tungnk123.zark.data.dto.conversation

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ConversationResponse(
    val conversationId: Int,
    val type: String,
    val name: String,
    val lastMessage: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastMessageAt: LocalDateTime,
    val participants: List<Participant>
)

@Serializable
data class Participant(
    val userId: Int,
    val username: String
)
