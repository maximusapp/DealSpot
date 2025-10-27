package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.business.ResetPasswordVerificationDataState
import com.app.dealspot.domain.use_cases.ConfirmForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationCodeViewModel(
    private val confirmForgotPasswordUseCase: ConfirmForgotPasswordUseCase
) : ViewModel() {
    private var email: String = ""
    private var newPassword: String = ""
    private var confirmPassword: String = ""
    var verificationCode: String = ""
    private set

    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.None)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()

    private val _verifiedDataState = MutableStateFlow<ResetPasswordVerificationDataState>(ResetPasswordVerificationDataState.None)
    val verifiedDataState: StateFlow<ResetPasswordVerificationDataState> = _verifiedDataState.asStateFlow()

    fun setEmail(email: String) {
        this.email = email
    }
    fun setVerificationCode(code: String) {
        this.verificationCode = code
    }

    fun setNewPassword(password: String) {
        this.newPassword = password
    }

    fun setConfirmPassword(password: String) {
        this.confirmPassword = password
    }
    
    fun resetPassword() {
        println("VerificationCodeViewModel. resetPassword()")

        viewModelScope.launch {
            _resetPasswordState.value = ResetPasswordState.Loading

            val result: ResetPasswordState = confirmForgotPasswordUseCase.invoke(
                email = email,
                confirmationCode = verificationCode,
                newPassword = newPassword
            )

            _resetPasswordState.value = result
        }
    }

    fun verifyEnteredData() {
        println("VerificationCodeViewModel. verifyEnteredData()")

        if (newPassword.length < 8) {
            _verifiedDataState.value = ResetPasswordVerificationDataState.InvalidPassword()
        } else if (newPassword != confirmPassword) {
            _verifiedDataState.value = ResetPasswordVerificationDataState.PasswordsMismatch()
        } else if (verificationCode.length < 6) {
            _verifiedDataState.value = ResetPasswordVerificationDataState.InvalidVerificationCode()
        } else {
            _verifiedDataState.value = ResetPasswordVerificationDataState.Ok
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _resetPasswordState.value = ResetPasswordState.None
        }
    }

    fun resetVerificationDataState() {
        viewModelScope.launch {
            _verifiedDataState.value = ResetPasswordVerificationDataState.None
        }
    }
}