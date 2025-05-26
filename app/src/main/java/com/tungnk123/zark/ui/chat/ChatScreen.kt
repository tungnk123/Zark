package com.tungnk123.zark.ui.chat

import MessageItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import java.time.LocalDateTime

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun ChatScreen(
    conversationId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val chatList = uiState.chatList
    val incomingMessages = uiState.incomingMessages
    val isConnected = uiState.isConnected
    val currentUserId = uiState.currentUserId

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

    LaunchedEffect(Unit) {
        chatViewModel.getAllMessages(conversationId)
    }

    LaunchedEffect(allMessages.size) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect {
                listState.animateScrollToItem(allMessages.size)
            }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                navController = navController,
                chatTitle = "$conversationId",
                onlineStatus = if (isConnected) "Online" else "Offline",
                onPhoneCallClick = {},
                onVideoCallClick = {},
                onInfoClick = {}
            )
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
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
}
