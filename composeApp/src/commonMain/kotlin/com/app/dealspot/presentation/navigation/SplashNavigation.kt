package com.app.dealspot.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SplashNavigation {

    @Serializable
    data object Welcome : SplashNavigation

    @Serializable
    data object Onboarding : SplashNavigation

    @Serializable
    data object Login : SplashNavigation

    @Serializable
    data object Register : SplashNavigation

    @Serializable
    data object ForgotPassword : SplashNavigation

    @Serializable
    data class VerificationCode(val email: String) : SplashNavigation

    @Serializable
    data class ResetPassword(val email: String, val code: String) : SplashNavigation

}

