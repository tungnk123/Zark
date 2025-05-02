package com.tungnk123.zark.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.chat.composables.MessageItem
import com.tungnk123.zark.ui.chat.composables.TypingIndicator
import com.tungnk123.zark.utils.AppConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            TopAppBar(
                windowInsets = androidx.compose.foundation.layout.WindowInsets(0),
                title = {
                    Text(
                        text = "Xuân Anh",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Call action */ }) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Call"
                        )
                    }
                    IconButton(onClick = { /* Info action */ }) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Info"
                        )
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More"
                        )
                    }
                })
        },
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(chatList.reversed()) { chat ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        MessageItem(
                            chat,
                            isMe = chat.userSendId == currentUserId
                        )
                    }
                }
                items(incomingMessages) { chat ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        MessageItem(
                            chat,
                            isMe = chat.userSendId == currentUserId
                        )
                    }
                }
                if (isTyping) {
                    item { TypingIndicator() }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        isTyping = it.isNotBlank()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            Color(0xFFF0F0F0),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (isConnected && message.isNotBlank()) {
                            chatViewModel.sendMessage(
                                conversationId = conversationId,
                                content = message
                            )
                            message = ""
                            isTyping = false
                        }
                    }) {
                    Text(stringResource(R.string.msg_send_message))
                }
            }
        }
    }
}

