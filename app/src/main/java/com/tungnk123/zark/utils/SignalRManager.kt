package com.tungnk123.zark.utils

import com.tungnk123.zark.BuildConfig
import com.tungnk123.zark.utils.extensions.printLog
import eu.lepicekmichal.signalrkore.AutomaticReconnect
import eu.lepicekmichal.signalrkore.HubConnection
import eu.lepicekmichal.signalrkore.HubConnectionBuilder
import eu.lepicekmichal.signalrkore.HubConnectionState
import eu.lepicekmichal.signalrkore.TransportEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRManager @Inject constructor(
    private val tokenManager: TokenManager
) {
    companion object {
        private const val TAG = "SignalRManager"
        private const val HUB_URL = BuildConfig.CHAT_BASE_URL + "chatHub"
        private const val RECEIVE_MESSAGE = "ReceiveMessage"
        private const val SEND_MESSAGE = "SendMessage"
        private const val DELAY_RECONNECT = 3_000L
    }

    private var hubConnection: HubConnection? = null
    private var connectionJob: Job? = null
    private var messageListenerJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var onReceiveMessage: ((conversationId: Int, senderId: Int, content: String, type: String, sendDate: String) -> Unit)? =
        null

    suspend fun connect(
        onMessageReceived: (Int, Int, String, String, String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (isConnected()) return
        val token = tokenManager.token.firstOrNull()
        if (token == null) {
            "Token is null, cannot connect".printLog(TAG)
            return
        }
        "Using token: $token".printLog(TAG)
        "HUB_URL: $HUB_URL".printLog(TAG)

        val hubUrlWithAccessToken = "$HUB_URL?access_token=$token"

        onReceiveMessage = onMessageReceived

        hubConnection = HubConnectionBuilder.create(hubUrlWithAccessToken) {
            automaticReconnect = AutomaticReconnect.Active
            transportEnum = TransportEnum.WebSockets
        }
        "Hub Connection created: $hubConnection".printLog(TAG)

        observeConnectionState()

        messageListenerJob?.cancel()
        messageListenerJob = null
        messageListenerJob = coroutineScope.launch {
            hubConnection?.on(
                RECEIVE_MESSAGE,
                paramType1 = Int::class,
                paramType2 = Int::class,
                paramType3 = String::class,
                paramType4 = String::class,
                paramType5 = String::class
            )
                ?.collect { (conversationId, senderId, content, type, sendDate) ->
                    "Message received - ConversationId: $conversationId, SenderId: $senderId, Type: $type, SendDate: $sendDate".printLog(
                        TAG
                    )
                    onReceiveMessage?.invoke(conversationId, senderId, content, type, sendDate)
                }
        }

        retryUntilConnected(onError)
    }


    private suspend fun retryUntilConnected(onError: (Throwable) -> Unit) {
        var attempt = 0
        while (hubConnection?.connectionState?.value != HubConnectionState.CONNECTED && attempt < 10) {
            try {
                "Attempt $attempt to connect to SignalR...".printLog(TAG)
                hubConnection?.start()
                delay(DELAY_RECONNECT)
            }
            catch (e: Exception) {
                "SignalR connection error on attempt $attempt: ${e.message}".printLog(TAG)
                onError(e)
                delay(DELAY_RECONNECT)
            }
            attempt++
        }

        if (hubConnection?.connectionState?.value != HubConnectionState.CONNECTED) {
            "Failed to connect after $attempt attempts".printLog(TAG)
        }
        else {
            "Successfully connected to SignalR".printLog(TAG)
        }
    }

    fun sendMessage(
        conversationId: Int,
        senderId: Int,
        content: String,
        type: String = "Text"
    ) {
        if (!isConnected()) {
            "Cannot send message: not connected to SignalR".printLog(TAG)
            return
        }

        coroutineScope.launch {
            try {
                "Sending message to $conversationId: $content".printLog(TAG)
                hubConnection?.send(SEND_MESSAGE, conversationId, senderId, content, type)
                "Message sent to $conversationId successfully".printLog(TAG)
            }
            catch (e: Exception) {
                "Failed to send message to $conversationId: ${e.message}".printLog(TAG)
            }
        }
    }

    suspend fun disconnect() {
        coroutineScope.cancel()
        hubConnection?.stop()
        connectionJob?.cancel()
        hubConnection = null
    }

    fun isConnected(): Boolean =
        hubConnection?.connectionState?.value == HubConnectionState.CONNECTED

    private fun observeConnectionState() {
        coroutineScope.launch {
            hubConnection?.connectionState?.collect { state ->
                when (state) {
                    HubConnectionState.CONNECTED -> "Connected to SignalR".printLog(TAG)
                    HubConnectionState.DISCONNECTED -> "Disconnected from SignalR".printLog(TAG)
                    HubConnectionState.CONNECTING -> "Attempting to connect to SignalR".printLog(TAG)
                    HubConnectionState.RECONNECTING -> "Reconnecting to SignalR".printLog(TAG)
                }
            }
        }
    }
}