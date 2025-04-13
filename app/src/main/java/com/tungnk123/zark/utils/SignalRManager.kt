package com.tungnk123.zark.utils

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
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
    private var connection: HubConnection? = null
    private var connectionDisposable: Disposable? = null

    companion object {
        private const val TAG = "SignalRManager"
        private const val HUB_URL = BuildConfig.CHAT_BASE_URL + "chatHub"
        private const val RECEIVE_MESSAGE = "ReceiveMessage"
        private const val SEND_PRIVATE_MESSAGE = "SendPrivateMessage"
    }

    suspend fun connect(
        onReceiveMessage: (Int, String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = tokenManager.token.first() ?: return

        connection = HubConnectionBuilder.create(HUB_URL)
            .withAccessTokenProvider(Single.defer { Single.just(token) })
            .build()

        connection?.on(RECEIVE_MESSAGE, { senderId: Int, content: String ->
            onReceiveMessage(senderId, content)
        }, Int::class.java, String::class.java)

        connectionDisposable = connection?.start()
            ?.subscribe({
                "Connected".printLog(TAG)
            }, { error ->
                onError(error)
            })
    }

    fun sendMessage(
        senderId: Int,
        receiverId: Int,
        content: String
    ) {
        connection?.invoke(SEND_PRIVATE_MESSAGE, senderId, receiverId, content)
    }

    fun disconnect() {
        connection?.stop()
        connectionDisposable?.dispose()
    }
}
