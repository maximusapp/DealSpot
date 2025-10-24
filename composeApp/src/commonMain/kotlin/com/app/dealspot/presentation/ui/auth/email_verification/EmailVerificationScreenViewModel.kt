package com.app.dealspot.presentation.ui.auth.email_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.ResendVerificationCodeState
import com.app.dealspot.business.VerificationEmailState
import com.app.dealspot.domain.use_cases.EmailVerificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailVerificationScreenViewModel(
    private val emailVerificationUseCase: EmailVerificationUseCase
) : ViewModel() {

    private val _verificationEmailState: MutableStateFlow<VerificationEmailState> = MutableStateFlow(VerificationEmailState.None)
    val verificationEmailState = _verificationEmailState.asStateFlow()

    private val _resendConfirmationCodeState: MutableStateFlow<ResendVerificationCodeState> = MutableStateFlow(ResendVerificationCodeState.None)
    val resendConfirmationCodeState = _resendConfirmationCodeState.asStateFlow()


    fun verifyEmail(code: String) {
        println("EmailVerificationScreenViewModel. verifyEmail. Code: $code")
        viewModelScope.launch {
            _verificationEmailState.value = emailVerificationUseCase.verifyEmail(code = code)
        }
    }

    fun setEmailVerified() {
        println("EmailVerificationScreenViewModel. setEmailVerified")
        viewModelScope.launch {
            emailVerificationUseCase.setEmailVerified()
        }
    }

    fun processEmailVerificationState() {
        _verificationEmailState.value = VerificationEmailState.None
    }

    fun processResendCodeState() {
        _resendConfirmationCodeState.value = ResendVerificationCodeState.None
    }

    fun resendCode() {
        println("EmailVerificationScreenViewModel. resendCode")

        viewModelScope.launch {
            _resendConfirmationCodeState.value = emailVerificationUseCase.reSendVerificationCode()
        }
    }

}