package com.tungnk123.zark.repository.conversation

import com.tungnk123.zark.data.dto.conversation.ConversationResponse
import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import com.tungnk123.zark.data.dto.conversation.CreateConversationResponse

interface ConversationRepository {
    suspend fun createConversation(
        createConversationRequest: CreateConversationRequest
    ): CreateConversationResponse

    suspend fun getConversations(): List<ConversationResponse>
}