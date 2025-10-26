package com.app.dealspot.domain.use_cases

import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.data.AuthRepositoryImpl

class ForgotPasswordUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(email: String): ResetPasswordState {
        return authRepositoryImpl.forgotPassword(email = email)
    }
}