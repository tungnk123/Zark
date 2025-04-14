package com.tungnk123.zark.data.datasource.user

import com.tungnk123.zark.data.dto.Contact
import com.tungnk123.zark.data.dto.user.LoginRequest
import com.tungnk123.zark.data.dto.user.LoginResponse

interface UserDataSource {
    suspend fun registerUser(
        email: String,
        password: String
    ): String

    suspend fun loginUser(
        loginRequest: LoginRequest
    ): LoginResponse

    suspend fun getContacts(userId: Int): List<Contact>
    suspend fun getUserIdByEmail(email: String): Int
}