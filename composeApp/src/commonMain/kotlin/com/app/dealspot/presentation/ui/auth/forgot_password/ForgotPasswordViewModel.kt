package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.EmailPasswordDataValidationState
import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.domain.use_cases.ForgotPasswordUseCase
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.incorrect_email
import dealspot.composeapp.generated.resources.password_info
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    
    var email: String = ""
        private set
    
    private val _forgotPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.None)
    val forgotPasswordState: StateFlow<ResetPasswordState> = _forgotPasswordState.asStateFlow()
    
    fun setEmail(email: String) {
        this.email = email
    }
    
    fun sendCodeToEmail() {
        viewModelScope.launch {
            _forgotPasswordState.value = ResetPasswordState.Loading
            
            when (dataValidationState()) {
                EmailPasswordDataValidationState.OK -> {
                    val result = forgotPasswordUseCase.invoke(email = email)
                    _forgotPasswordState.value = result
                }
                EmailPasswordDataValidationState.EMAIL_INCORRECT -> {
                    _forgotPasswordState.value = ResetPasswordState.Error(message = Res.string.incorrect_email)
                }
                EmailPasswordDataValidationState.PASSWORD_LENGTH_INCORRECT -> {
                    // This shouldn't happen in forgot password flow, but keeping for consistency
                    _forgotPasswordState.value = ResetPasswordState.Error(message = Res.string.password_info)
                }
            }
        }
    }
    
    fun clearState() {
        _forgotPasswordState.value = ResetPasswordState.None
    }
    
    private fun dataValidationState(): EmailPasswordDataValidationState {
        return if (!email.contains("@")) {
            EmailPasswordDataValidationState.EMAIL_INCORRECT
        } else {
            EmailPasswordDataValidationState.OK
        }
    }
}