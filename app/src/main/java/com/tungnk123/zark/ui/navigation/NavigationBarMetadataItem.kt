package com.tungnk123.zark.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.MusicNote
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
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        navigationRoute = NavigationRoute.Login
    ),
    Signin(
        labelResId = R.string.signin,
        selectedIcon = Icons.Filled.MusicNote,
        unselectedIcon = Icons.Outlined.MusicNote,
        navigationRoute = NavigationRoute.Signin
    ),
}