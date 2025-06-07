package com.tungnk123.zark.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmManager @Inject constructor(
    private val tokenManager: TokenManager
) {

    fun requestNotificationPermissionIfNeeded(
        activity: Activity,
        requestPermissionLauncher: ActivityResultLauncher<String>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    suspend fun getFcmToken() {
        try {
            val token = FirebaseMessaging.getInstance().token.await()
            tokenManager.saveFcmToken(token)
            Log.d("FCM", "FCM Registration Token: $token")
        } catch (e: Exception) {
            Log.w("FCM", "Fetching FCM registration token failed", e)
        }
    }

    fun subscribeToDefaultTopics() {
        FirebaseMessaging.getInstance().subscribeToTopic("general")
            .addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) {
                    "Subscribed to general topic"
                } else {
                    "Failed to subscribe to general topic"
                }
                Log.d("FCM", msg)
            }
    }
}
