package com.tungnk123.zark.data.dto.conversation

import kotlinx.serialization.Serializable

@Serializable
data class CreateConversationRequest(
    val creatorId: Int,
    val participantIds: List<Int>,
    val type: String,
    val name: String,
    val encryptedSessionKeys: List<EncryptedSessionKey>
)

@Serializable
data class EncryptedSessionKey(
    val userId: Int,
    val encryptedSessionKey: String
)
