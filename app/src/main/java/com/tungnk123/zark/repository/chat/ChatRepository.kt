package com.tungnk123.zark.repository.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeChatEntities(): Flow<List<ChatEntity>>
}