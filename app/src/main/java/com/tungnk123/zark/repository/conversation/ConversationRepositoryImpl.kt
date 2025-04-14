package com.tungnk123.zark.repository.conversation

import com.tungnk123.zark.data.dto.conversation.ConversationResponse
import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import com.tungnk123.zark.data.dto.conversation.CreateConversationResponse
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(

) : ConversationRepository {
    override suspend fun createConversation(createConversationRequest: CreateConversationRequest): CreateConversationResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getConversations(): List<ConversationResponse> {
        TODO("Not yet implemented")
    }
}