package com.app.dealspot.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import presentation.navigation.MainNavigation
import com.app.dealspot.presentation.ui.home.HomeScreen
import com.app.dealspot.presentation.ui.home.HomeScreenViewModel

@Composable
internal fun MainNav(
    homeViewModel: HomeScreenViewModel = koinInject(),
    onLogout: () -> Unit = {}
) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = MainNavigation.Main,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<MainNavigation.Main> {
            HomeScreen(

            )
        }

        composable<MainNavigation.Profile> {
//            ProfileScreen()
        }

        composable<MainNavigation.Settings> {
//            SettingsScreen()
        }

        composable<MainNavigation.About> {
//            AboutScreen()
        }

    }

}