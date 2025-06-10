package com.tungnk123.zark.repository.user

import com.tungnk123.zark.data.dto.user.BaseResponse
import com.tungnk123.zark.data.dto.user.FindUserByEmailResponse
import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.LoginResponse
import com.tungnk123.zark.data.dto.user.PaginatedUserResponse
import com.tungnk123.zark.data.dto.user.SignInRequest
import com.tungnk123.zark.data.dto.user.SignInResponse
import com.tungnk123.zark.data.dto.user.UserDto

interface UserRepository {
    suspend fun registerUser(signInRequest: SignInRequest): SignInResponse
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse
    suspend fun getUserIdByEmail(email: String): FindUserByEmailResponse
    suspend fun searchUsers(
        name: String? = null,
        email: String? = null,
        page: Int = 1,
        pageSize: Int = 10,
    ): PaginatedUserResponse
    suspend fun getUserById(userId: Int): BaseResponse<UserDto>
}