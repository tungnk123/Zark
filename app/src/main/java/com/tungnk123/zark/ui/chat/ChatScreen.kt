package com.tungnk123.zark.ui.chat

import MessageItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.ui.chat.composables.ChatInputBar
import com.tungnk123.zark.ui.chat.composables.ChatTopBar
import com.tungnk123.zark.ui.chat.composables.TypingIndicator
import com.tungnk123.zark.ui.common.DateHeader
import com.tungnk123.zark.utils.AppConstants
import com.tungnk123.zark.utils.extensions.printLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    conversationId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val incomingMessages by chatViewModel.incomingMessages.collectAsStateWithLifecycle()
    val isConnected by chatViewModel.isConnected.collectAsStateWithLifecycle()
    val chatList by chatViewModel.chatList.collectAsStateWithLifecycle()
    val currentUserId by chatViewModel.currentUserId.collectAsStateWithLifecycle()

    val allMessages = remember(
        chatList,
        incomingMessages
    ) {
        (chatList + incomingMessages).sortedBy { it.sendDate }
    }

    var lastMessageDateTime: LocalDateTime? = null

    var message by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        chatViewModel.getAllMessages(conversationId = conversationId)
    }

    LaunchedEffect(chatList.size + incomingMessages.size) {
        val listSize = chatList.size + incomingMessages.size
        coroutineScope.launch {
            delay(AppConstants.DELAY_AUTO_SCROLL)
            listState.animateScrollToItem(listSize)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                navController = navController,
                chatTitle = "$conversationId",
                onlineStatus = "Online",
                onPhoneCallClick = {},
                onVideoCallClick = {},
                onInfoClick = {})
        },
        bottomBar = {
            ChatInputBar(
                message = message,
                onMessageChange = {
                    message = it
                    isTyping = it.isNotBlank()
                },
                placeholder = "Chat with $conversationId",
                onSendClick = {
                    if (isConnected && message.isNotBlank()) {
                        chatViewModel.sendMessage(
                            conversationId = conversationId,
                            content = message
                        )
                        message = ""
                        isTyping = false
                    }
                },
            )
        },
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
            ) {
                items(allMessages) { chat ->
                    val currentMessageDate = chat.sendDate
                    if (lastMessageDateTime?.dayOfYear != currentMessageDate.dayOfYear) {
                        lastMessageDateTime = currentMessageDate
                        DateHeader(chat.sendDate)
                    }
                    MessageItem(
                        chat = chat,
                        isMe = chat.userSendId == currentUserId
                    )
                }
                if (isTyping) {
                    item { TypingIndicator() }
                }
            }
        }
    }
}

