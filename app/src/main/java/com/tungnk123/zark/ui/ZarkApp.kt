package com.tungnk123.zark.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.tungnk123.zark.ui.navigation.AppNavHost
import com.tungnk123.zark.ui.navigation.BottomNavigationBar
import com.tungnk123.zark.ui.theme.ZarkTheme
import com.tungnk123.zark.utils.AppConstants

@Composable
fun ZarkApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

    ZarkTheme {
        Surface {
            Scaffold(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                topBar = {},
                bottomBar = {
                    BottomNavigationBar(
                        currentTab = currentTab,
                        onTabSelected = {
                            viewModel.updateCurrentTab(it)
                            navController.navigate(it.navigationRoute.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        tabItemsList = AppConstants.navigationTabs
                    )
                }) { contentPaddings ->
                AppNavHost(
                    navController = navController, modifier = Modifier.padding(contentPaddings)
                )
            }
        }
    }
}