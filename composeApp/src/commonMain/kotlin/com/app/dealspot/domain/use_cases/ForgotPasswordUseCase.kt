package com.app.dealspot.domain.use_cases

import com.app.dealspot.data.AuthRepositoryImpl

class ForgotPasswordUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(email: String) {
        return authRepositoryImpl.forgotPassword(email = email)
    }
}