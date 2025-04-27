package com.tungnk123.zark.repository.user

import com.tungnk123.zark.data.datasource.user.UserDataSource
import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.SignInRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun registerUser(
        signInRequest: SignInRequest
    ) = userDataSource.registerUser(signInRequest)

    override suspend fun loginUser(loginRequest: LoginRequest) =
        userDataSource.loginUser(loginRequest)

    override suspend fun getUserIdByEmail(email: String) = userDataSource.getUserIdByEmail(email)
}