package com.tungnk123.zark.repository.user

import com.tungnk123.zark.data.datasource.remote.user.UserDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun registerUser(
        email: String,
        password: String
    ) = userDataSource.registerUser(email, password)
}