package com.app.dealspot.business

import com.app.dealspot.domain.model.DealRequestResponse
import com.app.dealspot.domain.model.LoginResponse
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.password_info
import dealspot.composeapp.generated.resources.passwords_do_not_match
import dealspot.composeapp.generated.resources.verification_code_info
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

sealed class ResetPasswordState {
    object None : ResetPasswordState()
    object Loading : ResetPasswordState()
    object Success : ResetPasswordState()
    data class Error(val message: StringResource) : ResetPasswordState()
}

sealed class ResetPasswordVerificationDataState() {
    data class InvalidVerificationCode(val message: StringResource = Res.string.verification_code_info): ResetPasswordVerificationDataState()
    data class InvalidPassword(val message: StringResource = Res.string.password_info): ResetPasswordVerificationDataState()
    data class PasswordsMismatch(val message: StringResource = Res.string.passwords_do_not_match): ResetPasswordVerificationDataState()
    object Ok: ResetPasswordVerificationDataState()
    object None: ResetPasswordVerificationDataState()
}

sealed class DealRequestState() {
    object None : DealRequestState()
    object Loading : DealRequestState()
    data class Result(val result: DealRequestResponse): DealRequestState()
}