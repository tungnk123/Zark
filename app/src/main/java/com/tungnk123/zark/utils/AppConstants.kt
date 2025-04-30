package com.tungnk123.zark.utils

import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem

object AppConstants {
    const val DELAY_POLLING_CONTACT = 2_000L
    const val STOP_TIMEOUT = 5_000L
    const val DELAY_AUTO_SCROLL = 100L
    val navigationTabs = listOf(
        NavigationBarMetadataItem.Chat,
        NavigationBarMetadataItem.Calendar,
        NavigationBarMetadataItem.Workplace,
        NavigationBarMetadataItem.Document,
        NavigationBarMetadataItem.More,
    )
}