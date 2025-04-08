package com.tungnk123.zark.ui.chat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.ui.chat.composables.ChatTopBar
import com.tungnk123.zark.ui.chat.composables.SwipeChatItem
import com.tungnk123.zark.ui.common.SearchBar
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    val chatEntities = chatViewModel.chatEntities.collectAsStateWithLifecycle()
    var query by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ChatTopBar(
                onAppLogoClick = {},
                onEditClick = { }
            )
        },
        containerColor = Color.White
    ) { contentPaddings ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPaddings)
                .padding(horizontal = 16.dp)
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChanged = { newQuery ->
                        query = newQuery
                    },
                    onClearQuery = {
                        query = ""
                    }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
            items(chatEntities.value) { item ->

                SwipeChatItem(
                    name = item.name,
                    lastMessage = item.lastMessage,
                    lastChatTime = LocalDateTime.now(),
                    isSeen = item.isSeenLastMessage,
                    onChatItemClick = {},
                    logoUrl = null,
                    onNotify = {},
                    onDelete = {},
                    onPin = {},
                    onArchive = {},
                    onMarkUnread = {},
                )
            }

        }
    }
}