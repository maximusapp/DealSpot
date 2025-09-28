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

}

