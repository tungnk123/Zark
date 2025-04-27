package com.tungnk123.zark.data.datasource.user

import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.SignInRequest
import com.tungnk123.zark.network.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun registerUser(
        signInRequest: SignInRequest
    ) = userService.registerUser(signInRequest)

    override suspend fun loginUser(loginRequest: LoginRequest) =
        userService.loginUser(loginRequest)

    override suspend fun getUserIdByEmail(email: String) = userService.getUserIdByEmail(email)
}