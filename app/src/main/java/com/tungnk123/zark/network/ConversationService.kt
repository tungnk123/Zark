package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.conversation.ConversationResponse
import com.tungnk123.zark.data.dto.conversation.CreateConversationRequest
import com.tungnk123.zark.data.dto.conversation.CreateConversationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConversationService {
    @POST("api/Conversation/create")
    suspend fun createConversation(
        @Body request: CreateConversationRequest
    ): CreateConversationResponse

    @GET("api/Conversation/conversations")
    suspend fun getConversations(): List<ConversationResponse>
}