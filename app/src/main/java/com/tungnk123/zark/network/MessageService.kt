package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageService {
    @GET("api/Messages/conversation/{conversationId}")
    suspend fun getMessages(
        @Query("conversationId") conversationId: Int,
        @Query("Page") page: Int,
        @Query("PageSize") pageSize: Int,
    ): List<ChatMessageResponse>

}