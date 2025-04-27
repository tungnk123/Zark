package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val message: String,
    val userId: Int
)
