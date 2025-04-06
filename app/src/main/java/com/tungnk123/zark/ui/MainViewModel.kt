package com.tungnk123.zark.ui

import androidx.lifecycle.ViewModel
import com.tungnk123.zark.ui.navigation.NavigationBarMetadataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _currentTab: MutableStateFlow<NavigationBarMetadataItem> =
        MutableStateFlow(NavigationBarMetadataItem.Chat)

    val currentTab = _currentTab.asStateFlow()

    fun updateCurrentTab(tab: NavigationBarMetadataItem) {
        _currentTab.update { tab }
    }
}