package com.tungnk123.zark.ui.home.state

import com.tungnk123.zark.data.dto.Contact

data class HomeUiState(
    val isLoading: Boolean = false,
    val contacts: List<Contact> = emptyList(),
    val error: String? = null
)
