package com.tungnk123.zark.ui.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.ui.chat.composables.ChatItem
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    val chatEntities = chatViewModel.chatEntities.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
    ) { contentPaddings ->
        LazyColumn(
            modifier = Modifier.padding(contentPaddings)
        ) {
            items(chatEntities.value) { item ->
                ChatItem(
                    name = item.name,
                    lastMessage = item.lastMessage,
                    lastChatTime = LocalDateTime.now(),
                    isSeen = item.isSeenLastMessage
                )
            }

        }
    }
}