package com.tungnk123.zark.data.datasource.local.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocalChatDataSourceImpl @Inject constructor() : LocalChatDataSource {
    override fun observeChatEntities(): Flow<List<ChatEntity>> = flowOf(
        listOf(
            ChatEntity(
                name = "Yii",
                lastMessage = "Hello my friend",
                isSeenLastMessage = true
            ),
            ChatEntity(
                name = "Mii",
                lastMessage = "I love you",
                isSeenLastMessage = false
            ),
            ChatEntity(
                name = "Alex",
                lastMessage = "Hey! Are you coming to the party?",
                isSeenLastMessage = true
            ),
            ChatEntity(
                name = "Sarah",
                lastMessage = "Don’t forget to send me the report!",
                isSeenLastMessage = false
            ),
            ChatEntity(
                name = "John",
                lastMessage = "Let’s meet at 6 PM.",
                isSeenLastMessage = true
            ),
            ChatEntity(
                name = "Emily",
                lastMessage = "Can you call me when you're free?",
                isSeenLastMessage = false
            ),
            ChatEntity(
                name = "David",
                lastMessage = "Thanks for your help today!",
                isSeenLastMessage = true
            )
        )
    )
}
