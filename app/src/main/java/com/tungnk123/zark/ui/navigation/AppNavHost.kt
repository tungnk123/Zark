package com.tungnk123.zark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tungnk123.zark.ui.calendar.CalendarScreen
import com.tungnk123.zark.ui.chat.ChatScreen
import com.tungnk123.zark.ui.home.HomeScreen
import com.tungnk123.zark.ui.login.LoginScreen
import com.tungnk123.zark.ui.signin.SignInScreen
import com.tungnk123.zark.ui.workplace.WorkplaceScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationRoute.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        baseComposable(NavigationBarMetadataItem.Login) {
            LoginScreen(
                navController = navController
            )
        }
        baseComposable(NavigationBarMetadataItem.SignIn) {
            SignInScreen(
                navController = navController
            )
        }
        baseComposable(NavigationBarMetadataItem.Home) {
            HomeScreen(
                navController = navController
            )
        }
        baseComposable(NavigationBarMetadataItem.Chat) {
            ChatScreen(
                navController = navController
            )
        }
        baseComposable(NavigationBarMetadataItem.Calendar) {
            CalendarScreen(
                navController = navController
            )
        }
        baseComposable(NavigationBarMetadataItem.Workplace) {
            WorkplaceScreen(
                navController = navController
            )
        }
    }
}
