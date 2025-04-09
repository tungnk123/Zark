package com.tungnk123.zark.data.datasource.remote.user

import com.tungnk123.zark.network.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun registerUser(
        email: String,
        password: String
    ) = userService.registerUser(email, password)
}