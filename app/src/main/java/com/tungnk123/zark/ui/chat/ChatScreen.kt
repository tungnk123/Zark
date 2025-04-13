package com.tungnk123.zark.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.data.dto.ChatEntity

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
            incomingMessages.forEach { (senderId, content) ->
                Text("From $senderId: $content")
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
                    chatViewModel.sendMessage(senderId = 1, receiverId = 2, content = message)
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
private fun ChatItem(chat: ChatEntity) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text("Name: ${chat.senderId}")
        Text("Last message: ${chat.content}")
        Text("Seen: ${if (chat.isSeen) "Yes" else "No"}")
    }
}
