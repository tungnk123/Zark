package com.tungnk123.zark.network

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("User/register")
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
    ): String
}