package com.app.dealspot.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.dealspot.presentation.navigation.SplashNavigation
import com.app.dealspot.presentation.ui.auth.forgot_password.ForgotPasswordScreen
import com.app.dealspot.presentation.ui.auth.forgot_password.VerificationCodeScreen
import com.app.dealspot.presentation.ui.auth.login.LoginScreen
import com.app.dealspot.presentation.ui.auth.registration.RegistrationScreen
import com.app.dealspot.presentation.ui.welcome.WelcomeScreen

@Composable
internal fun SplashNav(
    navigateToMain: () -> Unit
) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = SplashNavigation.Welcome,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<SplashNavigation.Welcome> {
            WelcomeScreen(
                navigateToOnboarding = { navigator.navigate(SplashNavigation.Onboarding) },
                navigateToMain = navigateToMain,
                navigateToLogin = { navigator.navigate(SplashNavigation.Login) },
                navigateToRegister = { navigator.navigate(SplashNavigation.Register) }
            )
        }
        composable<SplashNavigation.Onboarding> {
            OnboardingScreen(
                onGetStartedClick = { navigator.popBackStack() },
                onSkipClick = { navigator.popBackStack() }
            )
        }
        composable<SplashNavigation.Login> {
            LoginScreen(
                navigateToMain = navigateToMain,
                navigateToRegister = { navigator.navigate(SplashNavigation.Register) },
                navigateToForgotPassword = { navigator.navigate(SplashNavigation.ForgotPassword) }
            )
        }
        composable<SplashNavigation.Register> {
            RegistrationScreen(
                navigateToMain = navigateToMain,
                backClicked = { navigator.popBackStack() }
            )
        }
        composable<SplashNavigation.ForgotPassword> {
            ForgotPasswordScreen(
                onBackToLogin = { navigator.popBackStack() },
                onCodeSent = { email -> navigator.navigate(SplashNavigation.VerificationCode(email = email)) }
            )
        }
        composable<SplashNavigation.VerificationCode> { backStackEntry ->
            val route = backStackEntry.toRoute<SplashNavigation.VerificationCode>()
            val email = route.email
            VerificationCodeScreen(
                email = email,
                onBackToLogin = {
                    navigator.popBackStack()
                    navigator.popBackStack()
                }
            )
        }
    }
}