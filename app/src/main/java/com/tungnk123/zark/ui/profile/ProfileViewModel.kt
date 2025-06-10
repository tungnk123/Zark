package com.tungnk123.zark.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.user.UserDto
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val currentUserId = tokenManager.userId.firstOrNull() ?: return@launch
                val response = userRepository.getUserById(currentUserId)
                if (response.statusCode == 200) {
                    _uiState.value = _uiState.value.copy(
                        user = response.message,
                        isLoading = false,
                        error = null
                    )
                }
                else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load user profile"
                    )
                }
            }
            catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearLoginResponse()
            _uiState.value = ProfileUiState()
        }
    }
}

data class ProfileUiState(
    val user: UserDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)