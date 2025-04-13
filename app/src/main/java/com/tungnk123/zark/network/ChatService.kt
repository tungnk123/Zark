package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.ChatResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {
    @GET("api/Messages/{userId1}/{userId2}")
    suspend fun getMessages(
        @Path("userId1") senderId: Int,
        @Path("userId2") receiverId: Int,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): List<ChatResponse>

}