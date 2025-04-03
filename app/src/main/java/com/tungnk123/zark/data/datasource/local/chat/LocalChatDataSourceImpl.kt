package com.tungnk123.zark.data.datasource.local.chat

import com.tungnk123.zark.data.dto.ChatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocalChatDataSourceImpl @Inject constructor(

) : LocalChatDataSource {
    override fun observeChatEntities(): Flow<List<ChatEntity>> = flowOf(
        listOf(
            ChatEntity(
                name = "Yii",
                lastMessage = "Hello my friend"
            ),
            ChatEntity(
                name = "Mii",
                lastMessage = "I love yiu"
            ),
        )
    )
}