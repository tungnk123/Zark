package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val userId: Int
)
