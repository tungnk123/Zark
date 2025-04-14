package com.tungnk123.zark.data.datasource.user

import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.network.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun registerUser(
        email: String,
        password: String
    ) = userService.registerUser(email, password)

    override suspend fun loginUser(loginRequest: LoginRequest) =
        userService.loginUser(loginRequest)

    override suspend fun getContacts(userId: Int) = userService.getContacts(userId)
    override suspend fun getUserIdByEmail(email: String) = userService.getUserIdByEmail(email)
}