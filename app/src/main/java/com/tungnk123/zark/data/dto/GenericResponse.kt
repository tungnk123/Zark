package com.tungnk123.zark.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse(
    val statusCode: Int,
    val message: String
)
