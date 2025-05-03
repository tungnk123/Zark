package com.tungnk123.zark.data.dto.message

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChatMessageResponse(
    val chatMessageId: Int,
    val conversationId: Int,
    val userSendId: Int,
    val senderDisplayName: String,
    val message: String,
    val type: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val sendDate: LocalDateTime,
    val isSeen: Boolean = false,
    val isPinned: Boolean = false,
    val mediaLink: String? = null,
)
