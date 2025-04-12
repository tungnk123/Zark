package com.tungnk123.zark.repository.user

import com.tungnk123.zark.data.datasource.user.UserDataSource
import com.tungnk123.zark.data.dto.LoginRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun registerUser(
        email: String,
        password: String
    ) = userDataSource.registerUser(email, password)

    override suspend fun loginUser(loginRequest: LoginRequest) =
        userDataSource.loginUser(loginRequest)

    override suspend fun getContacts(userId: Int) = userDataSource.getContacts(userId)
}