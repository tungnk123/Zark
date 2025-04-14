package com.tungnk123.zark.repository.message

import com.tungnk123.zark.data.datasource.chat.MessageDataSource
import com.tungnk123.zark.data.dto.ChatEntity
import com.tungnk123.zark.network.MessageService
import com.tungnk123.zark.utils.SignalRManager
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDataSource: MessageDataSource,
    private val messageService: MessageService,
    private val signalRManager: SignalRManager
) : MessageRepository {

    override fun observeChatEntities(): Flow<List<ChatEntity>> =
        messageDataSource.observeChatEntities()

    override suspend fun getChatHistory(
        senderId: Int,
        receiverId: Int,
        page: Int,
        size: Int
    ): List<ChatEntity> {
        return messageService.getMessages(senderId, page, size)
            .map {
                ChatEntity(
                    senderId = it.userSendId,
                    receiverId = it.chatMessageId,
                    content = it.message,
                    timestamp = it.sendDate
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
