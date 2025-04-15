package com.tungnk123.zark.ui.login.state

data class LoginUiState(
    val email: String = "test@gmail.com",
    val password: String = "123123",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessLogin: Boolean = false
)
