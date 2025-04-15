package com.tungnk123.zark.utils

import android.annotation.SuppressLint
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.tungnk123.zark.BuildConfig
import com.tungnk123.zark.utils.extensions.printLog
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRManager @Inject constructor(
    private val tokenManager: TokenManager
) {
    private var hubConnection: HubConnection? = null
    private var connectionDisposable: Disposable? = null

    companion object {
        private const val TAG = "SignalRManager"
        private const val HUB_URL = BuildConfig.CHAT_BASE_URL + "chatHub"
        private const val RECEIVE_MESSAGE = "ReceiveMessage"
        private const val SEND_MESSAGE = "SendMessage"
    }

    private var onReceiveMessage: ((conversationId: Int, senderId: Int, content: String, type: String, sendDate: String) -> Unit)? =
        null

    suspend fun connect(
        onMessageReceived: (Int, Int, String, String, String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = tokenManager.token.first() ?: return
        "Connecting with token: $token".printLog(TAG)

        hubConnection = HubConnectionBuilder.create(HUB_URL)
            .withAccessTokenProvider(Single.defer { Single.just(token) })
            .build()

        onReceiveMessage = onMessageReceived

        hubConnection?.on(
            RECEIVE_MESSAGE,
            { conversationId: Int, userSendId: Int, content: String, type: String, sendDate: String ->
                onReceiveMessage?.invoke(conversationId, userSendId, content, type, sendDate)
            },
            Int::class.java,
            Int::class.java,
            String::class.java,
            String::class.java,
            String::class.java
        )

        connectionDisposable = hubConnection?.start()
            ?.subscribe({
                "Connected to SignalR".printLog(TAG)
            }, { error ->
                "SignalR connection error: ${error.message}".printLog(TAG)
                onError(error)
            })
    }

    @SuppressLint("CheckResult")
    fun sendMessage(
        conversationId: Int,
        senderId: Int,
        content: String,
        type: String = "Text"
    ) {
        if (!isConnected()) {
            "Cannot send message: not connected".printLog(TAG)
            return
        }

        hubConnection?.invoke(SEND_MESSAGE, conversationId, senderId, content, type)
            ?.doOnError { it.printStackTrace() }
            ?.subscribe({
                "Message sent to $conversationId".printLog(TAG)
            }, {
                "Failed to send message: ${it.message}".printLog(TAG)
            })
    }

    fun disconnect() {
        hubConnection?.stop()
        connectionDisposable?.dispose()
        hubConnection = null
        "Disconnected from SignalR".printLog(TAG)
    }

    fun isConnected(): Boolean = hubConnection?.connectionState == HubConnectionState.CONNECTED

    fun getConnection(): HubConnection? = hubConnection
}
