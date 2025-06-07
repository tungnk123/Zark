package com.tungnk123.zark.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tungnk123.zark.MainActivity
import com.tungnk123.zark.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(
                "FCM",
                "Message data payload: ${remoteMessage.data}"
            )
            handleDataMessage(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(
                "FCM",
                "Message Notification Body: ${it.body}"
            )
            sendNotification(
                it.title,
                it.body
            )
        }
    }

    override fun onNewToken(token: String) {
        Log.d(
            "FCM",
            "Refreshed token: $token"
        )
        sendRegistrationToServer(token)
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val title = data["title"]
        val body = data["body"]
        val action = data["action"]

        when (action) {
            "open_screen" -> {
                val screenName = data["screen"]
                Log.d(
                    "FCM",
                    "Navigate to screen: $screenName"
                )
            }

            "update_data" -> {
                Log.d(
                    "FCM",
                    "Update data triggered"
                )
            }
        }

        if (title != null && body != null) {
            sendNotification(
                title,
                body
            )
        }
    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
    ) {
        val intent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "zark_notifications"
        val notificationBuilder = NotificationCompat.Builder(
            this,
            channelId
        )
            .setSmallIcon(R.drawable.ic_chat)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Zark Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications for Zark app"
        }
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(
            System.currentTimeMillis()
                .toInt(),
            notificationBuilder.build()
        )
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(
            "FCM",
            "Sending token to server: $token"
        )
    }
}