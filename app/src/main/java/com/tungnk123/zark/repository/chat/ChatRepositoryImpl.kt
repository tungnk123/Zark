package com.tungnk123.zark.repository.chat

import com.tungnk123.zark.data.datasource.chat.ChatDataSource
import com.tungnk123.zark.data.dto.ChatEntity
import com.tungnk123.zark.network.MessageService
import com.tungnk123.zark.utils.SignalRManager
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource,
    private val messageService: MessageService,
    private val signalRManager: SignalRManager
) : ChatRepository {

    override fun observeChatEntities(): Flow<List<ChatEntity>> =
        chatDataSource.observeChatEntities()

    override suspend fun getChatHistory(
        senderId: Int,
        receiverId: Int,
        page: Int,
        size: Int
    ): List<ChatEntity> {
        return messageService.getMessages(senderId, receiverId, page, size)
            .map {
                ChatEntity(
                    senderId = it.senderId,
                    receiverId = it.receiverId,
                    content = it.content,
                    timestamp = it.timeStamp
                )
            }
    }

    override suspend fun startSignalRConnection(
        onReceiveMessage: (ChatEntity) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        signalRManager.startConnection(
            onReceiveMessage = { senderId, content ->
                val entity = ChatEntity(
                    senderId = senderId,
                    receiverId = -1,
                    content = content,
                    timestamp = LocalDateTime.now()
                )
                onReceiveMessage(entity)
            }, onError = onError
        )
    }

    override suspend fun sendMessage(
        senderId: Int,
        receiverId: Int,
        content: String
    ) {
        signalRManager.sendMessage(senderId, receiverId, content)
    }

    override fun disconnectSignalR() {
        signalRManager.disconnect()
    }
}
