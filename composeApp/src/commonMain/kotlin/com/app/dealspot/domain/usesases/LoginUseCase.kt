package com.app.dealspot.domain.usesases

import com.app.dealspot.business.LoginState
import com.app.dealspot.data.AuthRepositoryImpl

class LoginUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(email: String, password: String): LoginState {
        return authRepositoryImpl.login(email = email, password = password)
    }
}