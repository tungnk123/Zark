package com.tungnk123.zark.ui.search

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tungnk123.zark.R
import com.tungnk123.zark.ui.search.composables.EmptySearchItem
import com.tungnk123.zark.ui.search.composables.SearchConversationItem
import com.tungnk123.zark.ui.search.composables.SearchTopBar
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
                .printLog("test_error")
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChanged = {
                    searchViewModel.onQueryChanged(it)
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
        else if (uiState.searchContacts.isEmpty()) {
            EmptySearchItem(
                content = stringResource(R.string.msg_no_search_item),
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPaddings)
                    .padding(horizontal = 16.dp),
            ) {
                if (uiState.searchContacts.isNotEmpty()) {
                    items(uiState.searchContacts) { item ->
                        SearchConversationItem(
                            name = item.name.orEmpty(),
                            onChatItemClick = {
                                try {
                                    item.conversationId?.let {
                                        navController.navigateToChat(it)
                                    }
                                }
                                catch (e: Exception) {
                                    e.printException("HomeScreen")
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}