package com.tungnk123.zark.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.datasource.user.UserDataSource
import com.tungnk123.zark.data.dto.LoginRequest
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.ui.login.state.LoginUiState
import com.tungnk123.zark.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassWord(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val response = userRepository.loginUser(
                    LoginRequest(_uiState.value.email, _uiState.value.password)
                )
                tokenManager.saveToken(response.token)
                _uiState.update { it.copy(isSuccessLogin = true) }
            }
            catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
            finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun consumeSuccess() {
        _uiState.update { it.copy(isSuccessLogin = false) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}