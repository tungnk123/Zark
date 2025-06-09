package com.tungnk123.zark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.tungnk123.zark.ui.calendar.CalendarScreen
import com.tungnk123.zark.ui.calendar.CalendarViewModel
import com.tungnk123.zark.ui.chat.ChatScreen
import com.tungnk123.zark.ui.chat.ChatViewModel
import com.tungnk123.zark.ui.event.AddEventScreen
import com.tungnk123.zark.ui.event.detail.EventDetailScreen
import com.tungnk123.zark.ui.home.HomeScreen
import com.tungnk123.zark.ui.login.LoginScreen
import com.tungnk123.zark.ui.search.SearchScreen
import com.tungnk123.zark.ui.signin.SignInScreen
import com.tungnk123.zark.ui.workplace.WorkplaceScreen
import com.tungnk123.zark.utils.extensions.printLog

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationRoute.Login.route,
) {
    val calendarViewModel: CalendarViewModel = hiltViewModel()
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
                navController = navController,
            )
        }
        baseComposable(
            item = NavigationBarMetadataItem.Chat,
            arguments = listOf(
                navArgument("conversationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val conversationId =
                backStackEntry.arguments?.getInt("conversationId") ?: return@baseComposable
            ChatScreen(
                navController = navController,
                conversationId = conversationId,
            )
        }

        baseComposable(NavigationBarMetadataItem.Search) {
            SearchScreen(navController = navController)
        }

        baseComposable(NavigationBarMetadataItem.Calendar) {
            CalendarScreen(
                navController = navController,
                calendarViewModel = calendarViewModel
            )
        }
        baseComposable(
            NavigationBarMetadataItem.AddEvent,
            arguments = listOf(
                navArgument("eventRequestJson") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
        ) { backStackEntry ->
            val eventRequestJson = backStackEntry.arguments?.getString("eventRequestJson")
            "Event request json in AppNavHost: $eventRequestJson".printLog("test_nav")
            AddEventScreen(
                navController = navController,
                eventRequestJson = eventRequestJson
            )
        }
        baseComposable(
            item = NavigationBarMetadataItem.EventDetail,
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(
                navController = navController,
                eventId = eventId
            )
        }
        baseComposable(NavigationBarMetadataItem.Workplace) {
            WorkplaceScreen(
                navController = navController
            )
        }
    }
}
