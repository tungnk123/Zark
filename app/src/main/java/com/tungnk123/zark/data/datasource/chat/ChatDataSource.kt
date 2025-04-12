package com.tungnk123.zark.data.datasource.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow

interface ChatDataSource {
    fun observeChatEntities(): Flow<List<ChatEntity>>
}