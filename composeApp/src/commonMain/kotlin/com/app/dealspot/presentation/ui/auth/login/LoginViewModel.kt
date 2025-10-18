package com.app.dealspot.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.constants.DataStoreKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val dataStore: AppDataStore
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _emailConfirmationState: MutableStateFlow<String> = MutableStateFlow("")
    val emailConfirmationState = _emailConfirmationState.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _loginError.value = null

            try {

            } catch (e: Exception) {
                _loginError.value = e.message ?: "An unexpected error occurred"
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _loginError.value = null
    }

    fun isValid(): Boolean {
        return _email.value.contains("@") && _password.value.length >= 6
    }

    fun checkEmailConfirmationState() {
        println("checkEmailConfirmationState")
        viewModelScope.launch {
            val emailThatNeedToVerify = dataStore.getString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY) ?: ""

            println("emailThatNeedToVerify: $emailThatNeedToVerify")
            if (emailThatNeedToVerify.isNotEmpty()) {
                _emailConfirmationState.value = emailThatNeedToVerify
            }
        }
    }
}


