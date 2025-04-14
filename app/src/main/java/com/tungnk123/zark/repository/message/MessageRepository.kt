package com.tungnk123.zark.repository.message

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun observeChatEntities(): Flow<List<ChatEntity>>

    suspend fun getChatHistory(senderId: Int, receiverId: Int, page: Int, size: Int): List<ChatEntity>

    suspend fun startSignalRConnection(
        onReceiveMessage: (ChatEntity) -> Unit,
        onError: (Throwable) -> Unit
    )

    suspend fun sendMessage(senderId: Int, receiverId: Int, content: String)

    fun disconnectSignalR()
}
