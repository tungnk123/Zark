package com.tungnk123.zark.ui.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = modifier,
        topBar = {},
        containerColor = Color.White
    ) { contentPaddings ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPaddings)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = "Profile screen",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}