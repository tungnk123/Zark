package com.tungnk123.zark.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val statusCode: Int,
    val message: T
)