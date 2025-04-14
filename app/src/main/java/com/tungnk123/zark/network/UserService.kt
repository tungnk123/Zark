package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.Contact
import com.tungnk123.zark.data.dto.FindUserByEmailResponse
import com.tungnk123.zark.data.dto.LoginRequest
import com.tungnk123.zark.data.dto.LoginResponse
import com.tungnk123.zark.data.dto.SignInRequest
import com.tungnk123.zark.data.dto.SignInResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("api/User/register")
    suspend fun registerUser(
        @Body request: SignInRequest
    ): SignInResponse

    @POST("api/User/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("api/User/get-id-by-email")
    suspend fun getUserIdByEmail(
        @Query("email") email: String
    ): FindUserByEmailResponse
}