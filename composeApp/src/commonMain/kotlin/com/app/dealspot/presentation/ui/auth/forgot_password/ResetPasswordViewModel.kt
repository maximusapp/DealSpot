package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.domain.use_cases.ConfirmForgotPasswordUseCase
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.password_info
import dealspot.composeapp.generated.resources.passwords_do_not_match
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
            
            if (newPassword.length < 8) {
                _resetPasswordState.value = ResetPasswordState.Error(Res.string.password_info)
            } else if (newPassword != confirmPassword) {
                _resetPasswordState.value = ResetPasswordState.Error(Res.string.passwords_do_not_match)
            } else {
                val result = confirmForgotPasswordUseCase.invoke(
                    email = email,
                    confirmationCode = verificationCode,
                    newPassword = newPassword
                )

                _resetPasswordState.value = result
            }
        }
    }
    
    fun clearState() {
        _resetPasswordState.value = ResetPasswordState.None
    }
}