package com.app.dealspot.domain

import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.ResendVerificationCodeState
import com.app.dealspot.business.VerificationEmailState
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.data.AuthRepositoryImpl


class EmailVerificationUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val appDataStoreManager: AppDataStore
) {
    suspend fun setEmailVerified() {
        appDataStoreManager.putBoolean(key = DataStoreKeys.IS_EMAIL_VERIFIED, value = true)
        resetEmailForVerification()
    }

    suspend fun emailThatNeedVerify(email: String) {
        appDataStoreManager.putString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY, value = email)
    }

    suspend fun isEmailVerified(email: String): Boolean {
        return appDataStoreManager.getBoolean(key = DataStoreKeys.IS_EMAIL_VERIFIED) ?: false
    }

    suspend fun resetEmailForVerification() {
        appDataStoreManager.putString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY, value = "")
    }

    suspend fun verifyEmail(code: String): VerificationEmailState {
        println("EmailVerificationUseCase. verifyEmail")
        val email = appDataStoreManager.getString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY) ?: ""
        println("verifyEmail. Email: $email, code: $code")

        val response = authRepositoryImpl.verifyEmail(email = email, code = code)
        return response
    }

    suspend fun reSendVerificationCode(): ResendVerificationCodeState {
        println("EmailVerificationUseCase. reSendVerificationCode")
        val email = appDataStoreManager.getString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY) ?: ""
        println("reSendVerificationCode. Email: $email")

        val response = authRepositoryImpl.resendVerificationCode(email = email)
        return response
    }
}