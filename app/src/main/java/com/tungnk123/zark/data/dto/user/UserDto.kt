package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val displayName: String,
    val email: String,
    val firebaseUid: String?,
    val avatarUrl: String?,
    val connections: List<String>,
    val devices: List<String>,
    val isValidAccount: Boolean,
    val publicKey: String?
)

@Serializable
data class Pagination(
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)

@Serializable
data class PaginatedUserResponse(
    val statusCode: Int,
    val data: List<UserDto>,
    val pagination: Pagination
)
