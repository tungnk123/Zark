package com.tungnk123.zark.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.ui.chat.ChatViewModel
import com.tungnk123.zark.ui.home.composables.HomeTopBar
import com.tungnk123.zark.ui.home.composables.SwipeChatItem
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem
import com.tungnk123.zark.utils.extensions.navigateToChat
import com.tungnk123.zark.utils.extensions.navigateToDestination
import com.tungnk123.zark.utils.extensions.printException

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homeViewModel.fetchConversations()
        chatViewModel.startConnection()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onAppLogoClick = {},
                onSearchClick = {
                    navController.navigateToDestination(NavigationBarMetadataItem.Search)
                },
                onMoreClick = {}
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color.White
    ) { contentPaddings ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPaddings),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .padding(contentPaddings)
                    .padding(horizontal = 16.dp)
            ) {
                items(uiState.contacts) { item ->
                    SwipeChatItem(
                        name = item.name,
                        lastMessage = item.lastMessage,
                        lastChatTime = item.lastMessageAt,
                        isSeen = false,
                        onChatItemClick = {
                            try {
                                navController.navigateToChat(item.conversationId)
                            }
                            catch (e: Exception) {
                                e.printException("HomeScreen")
                            }
                        },
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
}