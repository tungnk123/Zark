package com.tungnk123.zark.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.ui.graphics.vector.ImageVector
import com.tungnk123.zark.R

enum class NavigationBarMetadataItem(
    val labelResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigationRoute: NavigationRoute
) {
    Login(
        labelResId = R.string.login,
        selectedIcon = Icons.AutoMirrored.Filled.Login,
        unselectedIcon = Icons.AutoMirrored.Filled.Login,
        navigationRoute = NavigationRoute.Login
    ),
    SignIn(
        labelResId = R.string.signin,
        selectedIcon = Icons.AutoMirrored.Filled.Login,
        unselectedIcon = Icons.AutoMirrored.Filled.Login,
        navigationRoute = NavigationRoute.SignIn
    ),
    Chat(
        labelResId = R.string.msg_chat,
        selectedIcon = Icons.AutoMirrored.Filled.Chat,
        unselectedIcon = Icons.AutoMirrored.Filled.Chat,
        navigationRoute = NavigationRoute.Chat
    ),
}