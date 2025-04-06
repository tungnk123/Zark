package com.tungnk123.zark.ui.navigation

import androidx.annotation.DrawableRes
import com.tungnk123.zark.R

enum class NavigationBarMetadataItem(
    val labelResId: String,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    val navigationRoute: NavigationRoute,
) {
    Login(
        labelResId = NavigationRoute.Login.name,
        selectedIconRes = R.drawable.ic_chat_selected,
        unselectedIconRes = R.drawable.ic_chat,
        navigationRoute = NavigationRoute.Login
    ),
    SignIn(
        labelResId = NavigationRoute.SignIn.name,
        selectedIconRes = R.drawable.ic_chat_selected,
        unselectedIconRes = R.drawable.ic_chat,
        navigationRoute = NavigationRoute.SignIn
    ),
    Chat(
        labelResId = NavigationRoute.Chat.name,
        selectedIconRes = R.drawable.ic_chat_selected,
        unselectedIconRes = R.drawable.ic_chat,
        navigationRoute = NavigationRoute.Chat
    ),
    Calendar(
        labelResId = NavigationRoute.Calendar.name,
        selectedIconRes = R.drawable.ic_calendar_selected,
        unselectedIconRes = R.drawable.ic_calendar,
        navigationRoute = NavigationRoute.Calendar
    ),
    Workplace(
        labelResId = NavigationRoute.Workplace.name,
        selectedIconRes = R.drawable.ic_workplace_selected,
        unselectedIconRes = R.drawable.ic_workplace,
        navigationRoute = NavigationRoute.Workplace
    ),
    Document(
        labelResId = NavigationRoute.Document.name,
        selectedIconRes = R.drawable.ic_document_selected,
        unselectedIconRes = R.drawable.ic_document,
        navigationRoute = NavigationRoute.Document
    ),
    More(
        labelResId = NavigationRoute.More.name,
        selectedIconRes = R.drawable.ic_more_selected,
        unselectedIconRes = R.drawable.ic_more,
        navigationRoute = NavigationRoute.More
    )
}
