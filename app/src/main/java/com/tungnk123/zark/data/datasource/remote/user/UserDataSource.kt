package com.tungnk123.zark.data.datasource.remote.user

interface UserDataSource {
    suspend fun registerUser(
        email: String,
        password: String
    ): String
}