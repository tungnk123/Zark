package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val fcmToken: String
)
