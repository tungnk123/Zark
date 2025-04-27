package com.tungnk123.zark.repository.message

import com.tungnk123.zark.data.datasource.chat.MessageDataSource
import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import com.tungnk123.zark.network.MessageService
import com.tungnk123.zark.utils.SignalRManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDataSource: MessageDataSource,
    private val messageService: MessageService,
    private val signalRManager: SignalRManager
) : MessageRepository {

    override suspend fun getChatHistory(
        senderId: Int,
        receiverId: Int,
        page: Int,
        size: Int
    ): List<ChatMessageResponse> {
        return messageService.getMessages(senderId, page, size)
    }

    override suspend fun startSignalRConnection(
        onReceiveMessage: (ChatMessageResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        signalRManager.connect(
            onMessageReceived = { conversationId, senderId, content, type, sendDateStr ->
                val formatter = DateTimeFormatter.ISO_DATE_TIME
                val sendDate = try {
                    LocalDateTime.parse(sendDateStr, formatter)
                }
                catch (e: Exception) {
                    LocalDateTime.now()
                }

                val message = ChatMessageResponse(
                    chatMessageId = 0,
                    conversationId = conversationId,
                    userSendId = senderId, senderDisplayName = "User $senderId",
                    message = content,
                    mediaLink = "null",
                    type = type,
                    sendDate = sendDate
                )
                onReceiveMessage(message)
            }, onError = onError
        )
    }

    override suspend fun sendMessage(
        conversationId: Int,
        senderId: Int,
        content: String,
        type: String
    ) {
        signalRManager.sendMessage(
            conversationId = conversationId, senderId = senderId, content = content, type = type
        )
    }

    override suspend fun disconnectSignalR() {
        signalRManager.disconnect()
    }
}