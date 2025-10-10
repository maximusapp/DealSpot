package com.app.dealspot.business

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