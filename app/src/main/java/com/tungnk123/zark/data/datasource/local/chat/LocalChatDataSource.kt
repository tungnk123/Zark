package com.tungnk123.zark.data.datasource.local.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow

interface LocalChatDataSource {
    fun observeChatEntities(): Flow<List<ChatEntity>>
}