package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val email: String,
    val password: String,
    val displayName: String,
    val fcmToken: String
)
