package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.EmailPasswordDataValidationState
import com.app.dealspot.domain.use_cases.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    
    var email: String = ""
        private set
    
    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.None)
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState.asStateFlow()
    
    fun setEmail(email: String) {
        this.email = email
    }
    
    fun sendCodeToEmail() {
        viewModelScope.launch {
            _forgotPasswordState.value = ForgotPasswordState.Loading
            
            when (dataValidationState()) {
                EmailPasswordDataValidationState.OK -> {
                    val result = forgotPasswordUseCase.invoke(email = email)
                    _forgotPasswordState.value = result
                }
                EmailPasswordDataValidationState.EMAIL_INCORRECT -> {
                    _forgotPasswordState.value = ForgotPasswordState.Error("Email format is incorrect")
                }
                EmailPasswordDataValidationState.PASSWORD_LENGTH_INCORRECT -> {
                    // This shouldn't happen in forgot password flow, but keeping for consistency
                    _forgotPasswordState.value = ForgotPasswordState.Error("Invalid input")
                }
            }
        }
    }
    
    fun clearState() {
        _forgotPasswordState.value = ForgotPasswordState.None
    }
    
    private fun dataValidationState(): EmailPasswordDataValidationState {
        return if (!email.contains("@")) {
            EmailPasswordDataValidationState.EMAIL_INCORRECT
        } else {
            EmailPasswordDataValidationState.OK
        }
    }
}

sealed class ForgotPasswordState {
    object None : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object Success : ForgotPasswordState()
    data class Error(val message: String) : ForgotPasswordState()
}

