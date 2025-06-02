package com.tungnk123.zark.ui.navigation

sealed class NavigationRoute(val route: String, val name: String = "") {
    data object Login : NavigationRoute("log_in", "Đăng nhập")
    data object SignIn : NavigationRoute("sign_in", "Đăng ký")
    data object Chat : NavigationRoute("chat/{conversationId}", "Chat")
    data object Home : NavigationRoute("home", "Chat")
    data object Search : NavigationRoute("search", "Search")
    data object Calendar : NavigationRoute("calendar", "Lịch")
    data object AddEvent : NavigationRoute("add_event?eventRequestJson={eventRequestJson}", "Thêm sự kiện")
    data object Workplace : NavigationRoute("workplace", "Nơi làm việc")
    data object Document : NavigationRoute("document", "Tài liệu")
    data object More : NavigationRoute("more", "Xem thêm")
}
