package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.VerificationCodeErrorType
import com.app.dealspot.business.VerificationCodeState
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.enter_six_numbers_of_code
import dealspot.composeapp.generated.resources.invalid_verification_code
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class VerificationCodeViewModel() : ViewModel() {
    var verificationCode: String = ""
    private set

    private val _verificationState = MutableStateFlow<VerificationCodeState>(VerificationCodeState.None)
    val verificationState: StateFlow<VerificationCodeState> = _verificationState.asStateFlow()

    fun setVerificationCode(code: String) {
        this.verificationCode = code
    }
    
    fun verifyCode() {
        viewModelScope.launch {
            _verificationState.value = VerificationCodeState.Loading
            
            if (verificationCode.length < 6) {
                _verificationState.value = VerificationCodeState.Error(type = VerificationCodeErrorType.ERROR_CODE_SHOULD_BE_6_DIGITS)
                return@launch
            }
            
            _verificationState.value = VerificationCodeState.Success
        }
    }

    fun getErrorTypeMessage(errorType: VerificationCodeErrorType): StringResource? {
        return when (errorType) {
            VerificationCodeErrorType.ERROR_CODE_SHOULD_BE_6_DIGITS -> {
                Res.string.enter_six_numbers_of_code
            }

            VerificationCodeErrorType.CONFIRMATION_CODE_INCORRECT -> {
                Res.string.invalid_verification_code
            }

            else -> {
                null
            }
        }
    }
}

