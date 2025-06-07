package com.tungnk123.zark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.tungnk123.zark.ui.ZarkApp
import com.tungnk123.zark.ui.theme.ZarkTheme
import com.tungnk123.zark.utils.FcmManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var fcmManager: FcmManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("FCM", "Notification permission granted")
        } else {
            Log.d("FCM", "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupFCM()

        setContent {
            ZarkTheme {
                ZarkApp()
            }
        }
    }

    private fun setupFCM() {
        lifecycleScope.launch {
            fcmManager.requestNotificationPermissionIfNeeded(
                this@MainActivity,
                requestPermissionLauncher
            )
            fcmManager.getFcmToken()
            fcmManager.subscribeToDefaultTopics()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZarkTheme {
        ZarkApp()
    }
}