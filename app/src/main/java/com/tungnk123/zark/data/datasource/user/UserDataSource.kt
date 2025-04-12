package com.tungnk123.zark.data.datasource.user

import com.tungnk123.zark.data.dto.Contact
import com.tungnk123.zark.data.dto.LoginRequest
import com.tungnk123.zark.data.dto.LoginResponse

interface UserDataSource {
    suspend fun registerUser(
        email: String,
        password: String
    ): String

    suspend fun loginUser(
        loginRequest: LoginRequest
    ): LoginResponse

    suspend fun getContacts(userId: Int): List<Contact>
}