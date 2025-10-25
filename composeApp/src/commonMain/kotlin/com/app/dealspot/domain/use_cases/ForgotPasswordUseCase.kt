package com.app.dealspot.domain.use_cases

import com.app.dealspot.data.AuthRepositoryImpl
import com.app.dealspot.presentation.ui.auth.forgot_password.ForgotPasswordState

class ForgotPasswordUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(email: String): ForgotPasswordState {
        return authRepositoryImpl.forgotPassword(email = email)
    }
}