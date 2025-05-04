package com.tungnk123.zark.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.tungnk123.zark.ui.search.SearchViewModel

@Composable
fun TemplateScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackBarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {

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

            }
        }
    }
}