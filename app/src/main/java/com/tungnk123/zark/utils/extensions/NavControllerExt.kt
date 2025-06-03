package com.tungnk123.zark.utils.extensions

import android.net.Uri
import androidx.navigation.NavController
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem

fun NavController.navigateToDestination(destination: NavigationBarMetadataItem) {
    this.navigate(destination.navigationRoute.route)
}

fun NavController.navigateToDestinationWithParameter(
    destination: NavigationBarMetadataItem,
    eventRequestJson: String? = null,
) {
    "navigateToDestinationWithParameter: $eventRequestJson".printLog("test_nav")

    val route = if (!eventRequestJson.isNullOrBlank()) {
        destination.navigationRoute.route.replace(
            "{eventRequestJson}",
            Uri.encode(eventRequestJson)
        )
    } else {
        destination.navigationRoute.route.substringBefore("?")
    }

    "Route: $route".printLog("test_nav")
    this.navigate(route)
}

fun NavController.navigateToChat(conversationId: Int) {
    val route = "chat/$conversationId"
    this.navigate(route)
}

fun NavController.navigateToEventDetail(eventId: String) {
    this.navigate("event_detail/$eventId")
}
