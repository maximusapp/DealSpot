package com.app.dealspot.business

import com.app.dealspot.data.model.LoginResponse
import org.jetbrains.compose.resources.StringResource

sealed class ResendVerificationCodeState {
    data object None: ResendVerificationCodeState()
    data object Success: ResendVerificationCodeState()
    data class Error(val message: String, val cause: String) : ResendVerificationCodeState()
}

sealed class VerificationEmailState {
    data object None: VerificationEmailState()
    data object Success: VerificationEmailState()
    data class Error(val message: String, val cause: String) : VerificationEmailState()
}

sealed class RegistrationState {
    data object None: RegistrationState()
    data class Success(val email: String) : RegistrationState()
    data class Error(val message: StringResource?) : RegistrationState()
}

sealed class LoginState {
    data object None : LoginState()
    data object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: StringResource) : LoginState()
}