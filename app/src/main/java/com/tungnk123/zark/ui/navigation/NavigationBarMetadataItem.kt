package com.tungnk123.zark.ui.navigation

import androidx.annotation.DrawableRes
import com.tungnk123.zark.R

enum class NavigationBarMetadataItem(
    val labelResId: String,
    val navigationRoute: NavigationRoute,
    @DrawableRes val selectedIconRes: Int? = null,
    @DrawableRes val unselectedIconRes: Int? = null,
) {
    Login(
        labelResId = NavigationRoute.Login.name,
        navigationRoute = NavigationRoute.Login
    ),
    SignIn(
        labelResId = NavigationRoute.SignIn.name,
        navigationRoute = NavigationRoute.SignIn
    ),
    Home(
        labelResId = NavigationRoute.Home.name,
        selectedIconRes = R.drawable.ic_chat_selected,
        unselectedIconRes = R.drawable.ic_chat,
        navigationRoute = NavigationRoute.Home
    ),
    Search(
        labelResId = NavigationRoute.Search.name,
        navigationRoute = NavigationRoute.Search
    ),
    Chat(
        labelResId = NavigationRoute.Chat.name,
        navigationRoute = NavigationRoute.Chat
    ),
    Calendar(
        labelResId = NavigationRoute.Calendar.name,
        selectedIconRes = R.drawable.ic_calendar_selected,
        unselectedIconRes = R.drawable.ic_calendar,
        navigationRoute = NavigationRoute.Calendar
    ),
    AddEvent(
        labelResId = NavigationRoute.AddEvent.name,
        navigationRoute = NavigationRoute.AddEvent
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
