package com.tungnk123.zark.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.data.dto.message.ChatMessageResponse

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val incomingMessages by chatViewModel.incomingMessages.collectAsStateWithLifecycle()
    val isConnected by chatViewModel.isConnected.collectAsStateWithLifecycle()
    val chatList by chatViewModel.chatList.collectAsStateWithLifecycle()

    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Chat", modifier = Modifier.padding(bottom = 8.dp))

        Text("Chats", modifier = Modifier.padding(bottom = 8.dp))
        Column(modifier = Modifier.weight(1f)) {
            chatList.forEach { chat ->
                ChatItem(chat)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Incoming Messages", modifier = Modifier.padding(bottom = 8.dp))
        Column(modifier = Modifier.weight(1f)) {
            incomingMessages.forEach { chat ->
                Text("From ${chat.userSendId}: ${chat.message}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Message input field
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Send button
        Button(
            onClick = {
                if (isConnected) {
                    chatViewModel.sendMessage(
                        conversationId = 1,
                        senderId = 1,
                        content = message
                    )
                    message = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Message")
        }

        Button(
            onClick = {
                if (isConnected) {
                    chatViewModel.stopConnection()
                } else {
                    chatViewModel.startConnection()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(if (isConnected) "Disconnect" else "Connect")
        }
    }
}

@Composable
private fun ChatItem(chat: ChatMessageResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("From: ${chat.senderDisplayName} (ID: ${chat.userSendId})")
        Text("Message: ${chat.message}")
        Text("Type: ${chat.type}")
        Text("Sent at: ${chat.sendDate}")
    }
}
