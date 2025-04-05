package com.tungnk123.zark.ui.navigation

sealed class NavigationRoute(val route: String, val name: String = "") {
    data object Login : NavigationRoute("log_in", "Log in")
    data object SignIn : NavigationRoute("sign_in", "Sign in")
    data object Chat : NavigationRoute("chat", "Chat")
}
