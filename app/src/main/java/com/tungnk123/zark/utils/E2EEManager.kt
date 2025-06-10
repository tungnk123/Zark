package com.tungnk123.zark.utils

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class E2EEManager @Inject constructor() {
    private val userKeys = ConcurrentHashMap<Int, UserKeyPair>()
    private val sessions = ConcurrentHashMap<String, SessionInfo>()
    private val chatSessions = ConcurrentHashMap<String, ChatSession>()
    private val userPublicKeys = ConcurrentHashMap<Int, String>()

    fun registerUser(userId: Int): UserKeyPair {
        if (userKeys.containsKey(userId)) {
            throw IllegalArgumentException("User $userId already registered")
        }
        val keyPair = generateKeyPair()
        val userKeyPair = UserKeyPair(
            userId,
            keyPair.public,
            keyPair.private
        )
        userKeys[userId] = userKeyPair
        val publicKeyBase64 = Base64.getEncoder()
            .encodeToString(keyPair.public.encoded)
        userPublicKeys[userId] = publicKeyBase64
        println("✅ User $userId registered successfully")
        return userKeyPair
    }

    fun getUserPublicKey(userId: Int): String? = userPublicKeys[userId]

    fun getAllRegisteredUsers(): Set<Int> = userKeys.keys.toSet()

    fun createChatSession(participants: Set<Int>): String {
        if (participants.size < 2) {
            throw IllegalArgumentException("Chat session requires at least 2 participants")
        }
        val unregisteredUsers = participants.filter { !userKeys.containsKey(it) }
        if (unregisteredUsers.isNotEmpty()) {
            throw IllegalArgumentException("Unregistered users: $unregisteredUsers")
        }
        val sessionId = generateSessionId()
        val sessionKey = generateSessionKey()
        val sessionInfo = SessionInfo(
            sessionId,
            sessionKey,
            participants
        )
        sessions[sessionId] = sessionInfo
        val chatSession = ChatSession(
            sessionId,
            participants
        )
        chatSessions[sessionId] = chatSession
        println("🔐 Chat session created: $sessionId with participants: $participants")
        return sessionId
    }

    fun getSessionParticipants(sessionId: String): Set<Int>? = sessions[sessionId]?.participants

    fun getAllSessions(): Map<String, Set<Int>> = sessions.mapValues { it.value.participants }

    fun sendMessage(
        sessionId: String,
        senderId: Int,
        recipientId: Int,
        message: String,
    ): String {
        val session =
            sessions[sessionId] ?: throw IllegalArgumentException("Session $sessionId not found")
        if (!session.participants.contains(senderId) || !session.participants.contains(recipientId)) {
            throw IllegalArgumentException("Sender or recipient not in session")
        }
        val iv = generateIV()
        val encryptedContent = encryptMessage(
            message,
            session.sessionKey,
            iv
        )
        val messageId = generateMessageId()
        val encryptedMessage = EncryptedMessage(
            messageId = messageId,
            senderId = senderId,
            recipientId = recipientId,
            sessionId = sessionId,
            encryptedContent = encryptedContent,
            iv = Base64.getEncoder()
                .encodeToString(iv)
        )
        chatSessions[sessionId]?.messages?.add(encryptedMessage)
        sessions[sessionId] = session.copy(lastUsed = LocalDateTime.now())
        println("📤 Message sent from User $senderId to User $recipientId in session $sessionId")
        return messageId
    }

    fun receiveMessage(
        sessionId: String,
        messageId: String,
        recipientId: Int,
    ): String? {
        val chatSession = chatSessions[sessionId]
            ?: throw IllegalArgumentException("Chat session $sessionId not found")
        val message = chatSession.messages.find { it.messageId == messageId }
            ?: throw IllegalArgumentException("Message $messageId not found")
        if (message.recipientId != recipientId) {
            throw IllegalArgumentException("Message not intended for user $recipientId")
        }
        val session = sessions[sessionId]!!
        val iv = Base64.getDecoder()
            .decode(message.iv)
        val decryptedMessage = decryptMessage(
            message.encryptedContent,
            session.sessionKey,
            iv
        )
        println("📥 Message received by User $recipientId from User ${message.senderId}")
        return decryptedMessage
    }

    fun getEncryptedSessionKeyForUser(
        sessionId: String,
        userId: Int,
    ): String {
        val session =
            sessions[sessionId] ?: throw IllegalArgumentException("Session $sessionId not found")
        if (!session.participants.contains(userId)) {
            throw IllegalArgumentException("User $userId not in session")
        }
        val userKeyPair =
            userKeys[userId] ?: throw IllegalArgumentException("User $userId not registered")
        return encryptSessionKeyWithPublicKey(
            session.sessionKey,
            userKeyPair.publicKey
        )
    }

    fun decryptSessionKeyForUser(
        encryptedSessionKey: String,
        userId: Int,
    ): SecretKey {
        val userKeyPair =
            userKeys[userId] ?: throw IllegalArgumentException("User $userId not registered")
        return decryptSessionKeyWithPrivateKey(
            encryptedSessionKey,
            userKeyPair.privateKey
        )
    }

    fun getChatHistory(
        sessionId: String,
        userId: Int,
    ): List<Pair<String, String>> {
        val chatSession = chatSessions[sessionId]
            ?: throw IllegalArgumentException("Chat session $sessionId not found")
        if (!chatSession.participants.contains(userId)) {
            throw IllegalArgumentException("User $userId not in session")
        }
        val session = sessions[sessionId]!!
        return chatSession.messages
            .filter { it.recipientId == userId || it.senderId == userId }
            .map { message ->
                val iv = Base64.getDecoder()
                    .decode(message.iv)
                val decryptedContent = decryptMessage(
                    message.encryptedContent,
                    session.sessionKey,
                    iv
                )
                val timestamp =
                    message.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val sender = if (message.senderId == userId) "You" else "User ${message.senderId}"
                "$timestamp - $sender" to decryptedContent
            }
    }

    fun closeSession(sessionId: String) {
        sessions.remove(sessionId)
        chatSessions.remove(sessionId)
        println("🔒 Session $sessionId closed and cleaned up")
    }

    fun cleanupOldSessions(hoursOld: Long = 24) {
        val cutoffTime = LocalDateTime.now()
            .minusHours(hoursOld)
        val oldSessions = sessions.filter { it.value.lastUsed.isBefore(cutoffTime) }
        oldSessions.forEach { (sessionId, _) ->
            sessions.remove(sessionId)
            chatSessions.remove(sessionId)
        }
        println("🧹 Cleaned up ${oldSessions.size} old sessions")
    }

    private fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        return keyGen.generateKeyPair()
    }

    private fun generateSessionKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }

    private fun generateIV(): ByteArray {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return iv
    }

    private fun generateSessionId(): String =
        "session_${System.currentTimeMillis()}_${(1000..9999).random()}"

    private fun generateMessageId(): String =
        "msg_${System.currentTimeMillis()}_${(1000..9999).random()}"

    private fun encryptSessionKeyWithPublicKey(
        sessionKey: SecretKey,
        publicKey: PublicKey,
    ): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            publicKey
        )
        val encrypted = cipher.doFinal(sessionKey.encoded)
        return Base64.getEncoder()
            .encodeToString(encrypted)
    }

    private fun decryptSessionKeyWithPrivateKey(
        encryptedSessionKeyBase64: String,
        privateKey: PrivateKey,
    ): SecretKey {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            privateKey
        )
        val encryptedBytes = Base64.getDecoder()
            .decode(encryptedSessionKeyBase64)
        val sessionKeyBytes = cipher.doFinal(encryptedBytes)
        return SecretKeySpec(
            sessionKeyBytes,
            "AES"
        )
    }

    private fun encryptMessage(
        message: String,
        sessionKey: SecretKey,
        iv: ByteArray,
    ): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            sessionKey,
            IvParameterSpec(iv)
        )
        val encryptedBytes = cipher.doFinal(message.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder()
            .encodeToString(encryptedBytes)
    }

    private fun decryptMessage(
        encryptedMessageBase64: String,
        sessionKey: SecretKey,
        iv: ByteArray,
    ): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            sessionKey,
            IvParameterSpec(iv)
        )
        val encryptedBytes = Base64.getDecoder()
            .decode(encryptedMessageBase64)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(
            decryptedBytes,
            Charsets.UTF_8
        )
    }
}

data class UserKeyPair(
    val userId: Int,
    val publicKey: PublicKey,
    val privateKey: PrivateKey,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class SessionInfo(
    val sessionId: String,
    val sessionKey: SecretKey,
    val participants: Set<Int>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastUsed: LocalDateTime = LocalDateTime.now(),
)

data class EncryptedMessage(
    val messageId: String,
    val senderId: Int,
    val recipientId: Int,
    val sessionId: String,
    val encryptedContent: String,
    val iv: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)

data class ChatSession(
    val sessionId: String,
    val participants: Set<Int>,
    val messages: MutableList<EncryptedMessage> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
