package com.app.dealspot.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import com.app.dealspot.presentation.navigation.SplashNavigation
import com.app.dealspot.presentation.ui.auth.login.LoginScreen
import com.app.dealspot.presentation.ui.auth.registration.RegistrationScreen
import com.app.dealspot.presentation.ui.welcome.WelcomeScreen

@Composable
internal fun SplashNav(
    splashViewModel: SplashViewModel = koinInject(),
//    loginViewModel: LoginViewModel = koinInject(),
//    registerViewModel: RegisterViewModel = koinInject(),
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
//                viewModel = loginViewModel,
                navigateToMain = navigateToMain,
                navigateToRegister = { navigator.navigate(SplashNavigation.Register) },
//                state = loginViewModel.state.value,
//                events = loginViewModel::onTriggerEvent
            )
        }
        composable<SplashNavigation.Register> {
            RegistrationScreen(
//                viewModel = registerViewModel,
                navigateToMain = navigateToMain,
                backClicked = { navigator.popBackStack() }
//                state = registerViewModel.state.value,
//                events = registerViewModel::onTriggerEvent
            )
        }
    }

}