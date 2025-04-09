package com.tungnk123.zark.repository.user

interface UserRepository {
    suspend fun registerUser(
        email: String,
        password: String
    ): String
}