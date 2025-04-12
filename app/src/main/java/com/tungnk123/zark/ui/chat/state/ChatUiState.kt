package com.tungnk123.zark.ui.chat.state

import com.tungnk123.zark.data.dto.Contact

data class ChatUiState(
    val isLoading: Boolean = false,
    val contacts: List<Contact> = emptyList(),
    val error: String? = null
)
