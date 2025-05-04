package com.tungnk123.zark.repository.conversation

import com.tungnk123.zark.data.datasource.conversation.ConversationDataSource
import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val conversationDataSource: ConversationDataSource
) : ConversationRepository {
    override suspend fun createConversation(createConversationRequest: CreateConversationRequest) =
        conversationDataSource.createConversation(createConversationRequest)

    override suspend fun getConversations() = conversationDataSource.getConversations()
    override suspend fun searchConversationByQuery(query: String) =
        conversationDataSource.searchConversationByQuery(query)
}