package com.tungnk123.zark.data.dto

data class ChatEntity(
    val name: String,
    val lastMessage: String,
    val isSeenLastMessage: Boolean = false
)
