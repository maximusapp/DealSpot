package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.domain.use_cases.ConfirmForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val confirmForgotPasswordUseCase: ConfirmForgotPasswordUseCase
) : ViewModel() {

    private var email: String = ""
    private var newPassword: String = ""
    private var confirmPassword: String = ""
    private var verificationCode: String = ""
    
    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.None)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()

    fun setEmail(email: String) {
        this.email = email
    }

    fun setNewPassword(password: String) {
        this.newPassword = password
    }
    
    fun setConfirmPassword(password: String) {
        this.confirmPassword = password
    }
    
    fun setVerificationCode(code: String) {
        this.verificationCode = code
    }
    
    fun resetPassword() {
        viewModelScope.launch {
            _resetPasswordState.value = ResetPasswordState.Loading
            
            // Validate passwords
            if (newPassword.length < 8) {
                _resetPasswordState.value = ResetPasswordState.Error("Password must be at least 8 characters")
                return@launch
            }
            
            if (newPassword != confirmPassword) {
                _resetPasswordState.value = ResetPasswordState.Error("Passwords do not match")
                return@launch
            }
            
            val result = confirmForgotPasswordUseCase.invoke(
                email = email,
                confirmationCode = verificationCode,
                newPassword = newPassword
            )

            _resetPasswordState.value = result
        }
    }
    
    fun clearState() {
        _resetPasswordState.value = ResetPasswordState.None
    }
}

