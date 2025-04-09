package com.tungnk123.zark.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun registerUser(
        email: String,
        password: String
    ) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = userRepository.registerUser(email, password)
                "Response: $response".printLog("test_res")
            }
        }
        catch (e: Exception) {
            e.printException(tag = "test_res")
        }
    }
}