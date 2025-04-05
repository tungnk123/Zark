package com.tungnk123.zark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tungnk123.zark.ui.chat.ChatScreen
import com.tungnk123.zark.ui.login.LoginScreen
import com.tungnk123.zark.ui.signin.SignInScreen

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
        baseComposable(NavigationBarMetadataItem.Chat) {
            ChatScreen(
                navController = navController
            )
        }
    }
}
