package com.app.dealspot.domain.use_cases

import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.data.AuthRepositoryImpl

class ConfirmForgotPasswordUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(email: String, confirmationCode: String, newPassword: String): ResetPasswordState {
        return authRepositoryImpl.resetPassword(
            email = email,
            confirmationCode = confirmationCode,
            newPassword = newPassword
        )
    }
}

