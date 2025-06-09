package com.tungnk123.zark.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.R
import com.tungnk123.zark.data.dto.user.UserDto
import com.tungnk123.zark.ui.search.composables.EmptySearchItem
import com.tungnk123.zark.ui.search.composables.SearchConversationItem
import com.tungnk123.zark.ui.search.composables.SearchTopBar
import com.tungnk123.zark.ui.search.composables.SearchUserItem
import com.tungnk123.zark.utils.extensions.navigateToChat
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val query by searchViewModel.query.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
            it.toString()
                .printLog("search_error")
        }
    }

    LaunchedEffect(Unit) {
        searchViewModel.navigateToConversation.collect { conversationId ->
            navController.navigateToChat(conversationId)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChanged = { newQuery ->
                    searchViewModel.onQueryChanged(newQuery)
                },
                onClearQuery = {
                    searchViewModel.onQueryChanged("")
                },
                onCancelClick = {
                    navController.navigateUp()
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color.White
    ) { contentPaddings ->

        when {
            // Loading state
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPaddings),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Empty query state
            query.isBlank() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPaddings),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.msg_start_typing_to_search),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }

            // No results state
            uiState.searchContacts.isEmpty() && uiState.searchUsers.isEmpty() -> {
                EmptySearchItem(
                    content = stringResource(
                        R.string.msg_no_search_results_found,
                        query
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPaddings)
                )
            }

            // Results state
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPaddings)
                        .padding(horizontal = 16.dp),
                ) {
                    // Search results summary
                    item {
                        val totalResults = uiState.searchContacts.size + uiState.searchUsers.size
                        Text(
                            text = stringResource(
                                R.string.search_results_summary,
                                totalResults,
                                query
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Conversations section
                    if (uiState.searchContacts.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.conversations),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }

                        items(
                            items = uiState.searchContacts,
                        ) { item ->
                            SearchConversationItem(
                                conversation = item,
                                onChatItemClick = {
                                    try {
                                        item.conversationId?.let { conversationId ->
                                            navController.navigateToChat(conversationId)
                                        }
                                    }
                                    catch (e: Exception) {
                                        e.printException("SearchScreen_ConversationClick")
                                    }
                                },
                            )
                        }
                    }

                    // Divider between sections
                    if (uiState.searchContacts.isNotEmpty() && uiState.searchUsers.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    // Users section
                    if (uiState.searchUsers.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.users),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }

                        items(
                            items = uiState.searchUsers,
                            key = { user -> user.id }) { user ->
                            SearchUserItem(
                                user = user,
                                onUserClick = {
                                    try {
                                        // Handle user click - you can implement these methods
                                        handleUserClick(
                                            user,
                                            navController,
                                            searchViewModel
                                        )
                                    }
                                    catch (e: Exception) {
                                        e.printException("SearchScreen_UserClick")
                                    }
                                },
                                onStartChatClick = {
                                    searchViewModel.startNewConversationWithUser(user)
                                })
                        }
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

private fun handleUserClick(
    user: UserDto,
    navController: NavController,
    searchViewModel: SearchViewModel,
) {
    // TODO
    // Navigate to user profile or show user details
    // Example: navController.navigate("user_profile/${user.id}")
}