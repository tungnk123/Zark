package com.tungnk123.zark.data.datasource.user

import com.tungnk123.zark.data.dto.user.FindUserByEmailResponse
import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.LoginResponse
import com.tungnk123.zark.data.dto.user.SignInRequest
import com.tungnk123.zark.data.dto.user.SignInResponse

interface UserDataSource {
    suspend fun registerUser(
        signInRequest: SignInRequest
    ): SignInResponse

    suspend fun loginUser(
        loginRequest: LoginRequest
    ): LoginResponse

    suspend fun getUserIdByEmail(email: String): FindUserByEmailResponse
}