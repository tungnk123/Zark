package com.tungnk123.zark.data.dto.message

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChatMessageResponse(
    val chatMessageId: Int,
    val conversationId: Int,
    val userSendId: Int,
    val senderUsername: String,
    val message: String,
    val mediaLink: String,
    val type: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val sendDate: LocalDateTime
)
