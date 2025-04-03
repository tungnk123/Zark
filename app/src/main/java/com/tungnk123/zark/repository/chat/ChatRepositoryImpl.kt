package com.tungnk123.zark.repository.chat

import com.tungnk123.zark.data.datasource.local.chat.LocalChatDataSource
import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val localChatDataSource: LocalChatDataSource
) : ChatRepository {
    override fun observeChatEntities(): Flow<List<ChatEntity>> =
        localChatDataSource.observeChatEntities()
}