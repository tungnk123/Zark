package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.Contact
import com.tungnk123.zark.data.dto.LoginRequest
import com.tungnk123.zark.data.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("User/register")
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
    ): String

    @POST("User/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("User/contacts")
    suspend fun getContacts(
        @Query("userId") userId: Int
    ): List<Contact>

    @GET("User/get-id-by-email")
    suspend fun getUserIdByEmail(email: String): Int
}