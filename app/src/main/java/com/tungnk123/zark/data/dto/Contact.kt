package com.tungnk123.zark.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Int,
    val username: String
)
