package com.app.dealspot.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.dealspot.presentation.ui.home.HomeScreen
import com.app.dealspot.presentation.ui.home.HomeScreenViewModel
import com.app.dealspot.presentation.ui.home.chats.ChatsScreen
import com.app.dealspot.presentation.ui.home.settings.SettingsScreen
import org.koin.compose.koinInject
import presentation.navigation.MainNavigation

@OptIn(ExperimentalMaterial3Api::class)
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
                onOpenSettings = { navigator.navigate(MainNavigation.Settings) },
                onOpenChats = { navigator.navigate(MainNavigation.Chats) }
            )
        }

        composable<MainNavigation.Profile> {
//            ProfileScreen()
        }

        composable<MainNavigation.Settings> {
            SettingsScreen(
                onBackClicked = { navigator.popBackStack() }
            )
        }

        composable<MainNavigation.Chats> {
            ChatsScreen(
                onBackClicked = { navigator.popBackStack() }
            )
        }

    }

}