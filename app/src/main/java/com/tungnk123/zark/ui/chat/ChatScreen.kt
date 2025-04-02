package com.tungnk123.zark.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.Uri
import com.tungnk123.zark.ui.chat.composables.ChatItem
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    Scaffold(
        modifier = modifier
    ) { contentPaddings ->
        Column(
            modifier = Modifier.padding(contentPaddings)
        ) {
            ChatItem(
                name = "John Doe",
                lastMessage = "Hello, how are you?",
                lastChatTime = LocalDateTime.now(),
                isSeen = false
            )

            ChatItem(
                name = "John Doe",
                lastMessage = "Hello, how are you?",
                lastChatTime = LocalDateTime.now(),
                isSeen = true
            )
        }
    }
}