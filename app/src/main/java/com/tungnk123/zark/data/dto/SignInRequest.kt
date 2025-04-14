package com.tungnk123.zark.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val email: String,
    val password: String,
    val displayName: String
)
