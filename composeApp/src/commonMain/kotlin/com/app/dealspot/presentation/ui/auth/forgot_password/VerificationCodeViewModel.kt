package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationCodeViewModel() : ViewModel() {
    var verificationCode: String = ""
    private set

    private var newPassword: String = ""

    private var confirmPassword: String = ""
    
    private val _verificationState = MutableStateFlow<VerificationCodeState>(VerificationCodeState.None)
    val verificationState: StateFlow<VerificationCodeState> = _verificationState.asStateFlow()

    fun setVerificationCode(code: String) {
        this.verificationCode = code
    }

    fun setNewPassword(password: String) {
        this.newPassword = password
    }

    fun setConfirmPassword(password: String) {
        this.confirmPassword = password
    }
    
    fun verifyCode() {
        viewModelScope.launch {
            _verificationState.value = VerificationCodeState.Loading
            
            if (verificationCode.length < 6) {
                _verificationState.value = VerificationCodeState.Error("Verification code must be at least 6 digits")
                return@launch
            }
            
//            val result = verifyForgotPasswordCodeUseCase.invoke(code = verificationCode)
            _verificationState.value = VerificationCodeState.Success
        }
    }
    
    fun resendCode() {
        viewModelScope.launch {
            _verificationState.value = VerificationCodeState.Loading
            // TODO: Implement resend code logic
            _verificationState.value = VerificationCodeState.Success
        }
    }
    
    fun clearState() {
        _verificationState.value = VerificationCodeState.None
    }
}

