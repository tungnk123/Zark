package com.tungnk123.zark.repository.message

import com.tungnk123.zark.data.dto.message.ChatMessageResponse

interface MessageRepository {

    suspend fun getAllMessages(
        conversationId: Int,
        page: Int?,
        size: Int?
    ): List<ChatMessageResponse>

    suspend fun startSignalRConnection(
        onReceiveMessage: (ChatMessageResponse) -> Unit,
        onError: (Throwable) -> Unit
    )

    suspend fun sendMessage(
        conversationId: Int,
        senderId: Int,
        content: String,
        type: String
    )

    suspend fun disconnectSignalR()
}

