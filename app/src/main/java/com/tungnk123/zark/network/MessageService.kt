package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.message.ChatMessageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageService {
    @GET("api/Messages/{conversationId}")
    suspend fun getMessages(
        @Path("conversationId") conversationId: Int,
        @Query("Page") page: Int? = null,
        @Query("PageSize") pageSize: Int? = null,
    ): List<ChatMessageResponse>
    
}