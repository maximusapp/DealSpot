package com.app.dealspot.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.dealspot.common.Context
import com.app.dealspot.di.appModule
import com.app.dealspot.presentation.navigation.AppNavigation
import com.app.dealspot.presentation.theme.AppTheme
import com.app.dealspot.presentation.ui.splash.MainNav
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import com.app.dealspot.presentation.ui.splash.SplashNav

@Composable
@Preview
fun App(context: Context) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        AppTheme {
            val navigator = rememberNavController()
            val viewModel: SharedViewModel = koinInject()
            val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

            LaunchedEffect(key1 = isUserLoggedIn) {
                if (!isUserLoggedIn) {
                    navigator.popBackStack()
                    navigator.navigate(AppNavigation.Splash)
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navigator,
                    startDestination = AppNavigation.Splash,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<AppNavigation.Splash> {
                        SplashNav(navigateToMain = {
                            println("Navigate from Splash: To Main Screen")
                            navigator.popBackStack()
                            navigator.navigate(AppNavigation.Main)
                        })
                    }
                    composable<AppNavigation.Main> {
                        MainNav(
                            onLogout = {
                                println("Logout triggered from MainNav")
                                viewModel.updateLoginStatus()
                                navigator.popBackStack()
                                navigator.navigate(AppNavigation.Splash)
                            }
                        )
                    }
                }
            }

        }
    }
}