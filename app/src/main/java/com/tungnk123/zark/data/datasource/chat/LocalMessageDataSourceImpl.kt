package com.tungnk123.zark.data.datasource.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

class LocalMessageDataSourceImpl @Inject constructor() : MessageDataSource {

    override fun observeChatEntities(): Flow<List<ChatEntity>> = flowOf(getMockChatEntities())

    private fun getMockChatEntities(): List<ChatEntity> = listOf(
        ChatEntity(
            senderId = 1,
            receiverId = 2,
            content = "Hello my friend",
            timestamp = LocalDateTime.now().minusMinutes(2),
            isSeen = true
        ),
        ChatEntity(
            senderId = 2,
            receiverId = 1,
            content = "I love you",
            timestamp = LocalDateTime.now().minusMinutes(5),
            isSeen = false
        ),
        ChatEntity(
            senderId = 1,
            receiverId = 3,
            content = "Hey! Are you coming to the party?",
            timestamp = LocalDateTime.now().minusHours(1),
            isSeen = true
        ),
        ChatEntity(
            senderId = 4,
            receiverId = 1,
            content = "Don’t forget to send me the report!",
            timestamp = LocalDateTime.now().minusDays(1),
            isSeen = false
        ),
        ChatEntity(
            senderId = 1,
            receiverId = 5,
            content = "Let’s meet at 6 PM.",
            timestamp = LocalDateTime.now().minusDays(2),
            isSeen = true
        ),
        ChatEntity(
            senderId = 6,
            receiverId = 1,
            content = "Can you call me when you're free?",
            timestamp = LocalDateTime.now().minusHours(10),
            isSeen = false
        ),
        ChatEntity(
            senderId = 1,
            receiverId = 7,
            content = "Thanks for your help today!",
            timestamp = LocalDateTime.now().minusMinutes(30),
            isSeen = true
        )
    )
}
