package com.tungnk123.zark.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tungnk123.zark.data.dto.user.SignInRequest
import com.tungnk123.zark.repository.user.UserRepository
import com.tungnk123.zark.utils.extensions.printException
import com.tungnk123.zark.utils.extensions.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun registerUser(
        email: String,
        password: String,
        displayName: String = "",
        fcmToken: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.registerUser(
                    SignInRequest(email, password, displayName, fcmToken)
                )
                "Response: $response".printLog("test_res")

                _eventFlow.emit("Đăng ký tài khoản thành công")
            } catch (e: Exception) {
                e.printException(tag = "test_res")
                _eventFlow.emit("Đăng ký thất bại: ${e.message}")
            }
        }
    }
}