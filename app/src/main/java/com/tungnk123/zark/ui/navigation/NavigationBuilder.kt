package com.tungnk123.zark.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer

fun NavGraphBuilder.baseComposable(
    item: NavigationBarMetadataItem,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit),
) {
    composable(
        route = item.navigationRoute.route,
        arguments = arguments
//        popEnterTransition = {
//            when {
//                isInitialRoute<NavigationRoute.SearchView>() -> ScaleTransition.scaleUp.enterTransition()
//                isInitialRoute<NavigationRoute.NowPlayingView>() -> ScaleTransition.scaleUp.enterTransition()
//                isInitialRoute<NavigationRoute.QueueView>() -> ScaleTransition.scaleUp.enterTransition()
//                isInitialRoute<NavigationRoute.LyricsView>() -> ScaleTransition.scaleUp.enterTransition()
//                else -> SlideTransition.slideRight.enterTransition()
//            }
//        },
//        popExitTransition = {
//            when {
//                isInitialRoute<NavigationRoute.SearchView>() -> SlideTransition.slideUp.exitTransition()
//                isInitialRoute<NavigationRoute.NowPlayingView>() -> SlideTransition.slideDown.exitTransition()
//                isInitialRoute<NavigationRoute.QueueView>() -> SlideTransition.slideDown.exitTransition()
//                isInitialRoute<NavigationRoute.LyricsView>() -> SlideTransition.slideDown.exitTransition()
//                else -> SlideTransition.slideRight.exitTransition()
//            }
//        },
//        enterTransition = {
//            when {
//                isTargetRoute<NavigationRoute.SearchView>() -> SlideTransition.slideDown.enterTransition()
//                isTargetRoute<NavigationRoute.NowPlayingView>() -> SlideTransition.slideUp.enterTransition()
//                isTargetRoute<NavigationRoute.QueueView>() -> SlideTransition.slideUp.enterTransition()
//                isTargetRoute<NavigationRoute.LyricsView>() -> SlideTransition.slideUp.enterTransition()
//                else -> SlideTransition.slideLeft.enterTransition()
//            }
//        },
//        exitTransition = {
//            when {
//                isTargetRoute<NavigationRoute.SearchView>() -> ScaleTransition.scaleDown.exitTransition()
//                isTargetRoute<NavigationRoute.NowPlayingView>() -> ScaleTransition.scaleDown.exitTransition()
//                isTargetRoute<NavigationRoute.QueueView>() -> ScaleTransition.scaleDown.exitTransition()
//                isTargetRoute<NavigationRoute.LyricsView>() -> ScaleTransition.scaleDown.exitTransition()
//                else -> SlideTransition.slideLeft.exitTransition()
//            }
//        }
    ) { backStackEntry ->
        content(backStackEntry)
    }
}

@OptIn(ExperimentalSerializationApi::class)
private inline fun <reified T> NavDestination.isRoute() =
    route?.contains(serializer<T>().descriptor.serialName) == true

@OptIn(ExperimentalSerializationApi::class)
private inline fun <reified T> AnimatedContentTransitionScope<NavBackStackEntry>.isInitialRoute() =
    initialState.destination.isRoute<T>()

@OptIn(ExperimentalSerializationApi::class)
private inline fun <reified T> AnimatedContentTransitionScope<NavBackStackEntry>.isTargetRoute() =
    targetState.destination.isRoute<T>()