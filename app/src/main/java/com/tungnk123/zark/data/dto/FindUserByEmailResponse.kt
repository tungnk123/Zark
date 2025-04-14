package com.tungnk123.zark.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FindUserByEmailResponse(
    val id: Int,
    val firebaseUid: String,
    val username: String
)
