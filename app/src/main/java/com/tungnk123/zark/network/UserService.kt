package com.tungnk123.zark.network

import com.tungnk123.zark.data.dto.user.BaseResponse
import com.tungnk123.zark.data.dto.user.FindUserByEmailResponse
import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.LoginResponse
import com.tungnk123.zark.data.dto.user.PaginatedUserResponse
import com.tungnk123.zark.data.dto.user.SignInRequest
import com.tungnk123.zark.data.dto.user.SignInResponse
import com.tungnk123.zark.data.dto.user.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("api/User")
    suspend fun searchUsers(
        @Query("name") name: String? = null,
        @Query("email") email: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): PaginatedUserResponse

    @GET("api/User/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Int
    ): BaseResponse<UserDto>

}