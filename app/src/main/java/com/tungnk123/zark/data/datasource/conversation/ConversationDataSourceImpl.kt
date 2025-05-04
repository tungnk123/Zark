package com.tungnk123.zark.data.datasource.conversation

import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import com.tungnk123.zark.network.ConversationService
import javax.inject.Inject

class ConversationDataSourceImpl @Inject constructor(
    private val conversationService: ConversationService
) : ConversationDataSource {
    override suspend fun createConversation(createConversationRequest: CreateConversationRequest) =
        conversationService.createConversation(request = createConversationRequest)

    override suspend fun getConversations() = conversationService.getConversations()
    override suspend fun searchConversationByQuery(query: String) =
        conversationService.searchConversationByQuery(query)
}