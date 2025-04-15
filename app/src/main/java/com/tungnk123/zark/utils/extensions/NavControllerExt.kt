package com.tungnk123.zark.utils.extensions

import androidx.navigation.NavController
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem

fun NavController.navigateToDestination(destination: NavigationBarMetadataItem) {
    this.navigate(destination.navigationRoute.route)
}