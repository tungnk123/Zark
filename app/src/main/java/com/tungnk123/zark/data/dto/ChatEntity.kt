package com.tungnk123.zark.data.dto

import com.tungnk123.zark.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ChatEntity(
    val senderId: Int,
    val receiverId: Int,
    val content: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime,
    val isSeen: Boolean = false
)

