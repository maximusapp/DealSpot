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
import com.app.dealspot.presentation.navigation.MainNavigation
import com.app.dealspot.presentation.ui.home.notifications.NotificationsScreen
import com.app.dealspot.presentation.ui.home.profile.ProfileScreen
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
internal fun MainNav(
    homeViewModel: HomeScreenViewModel = koinInject(),
    onLogout: () -> Unit = {}
) {
    val navigator = rememberNavController()

    val backStackEntry by navigator.currentBackStackEntryAsState()

    AnimatedContent(
        targetState = backStackEntry?.destination?.route,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "main-nav-transition"
    ) {
        NavHost(
            startDestination = MainNavigation.Main,
            navController = navigator,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<MainNavigation.Main> {
                HomeScreen(
                    onOpenNotification = { navigator.navigate(MainNavigation.Notifications) },
                    onOpenSettings = { navigator.navigate(MainNavigation.Settings) },
                    onOpenChats = { navigator.navigate(MainNavigation.Chats) },
                    onOpenProfile = { navigator.navigate(MainNavigation.Profile) }
                )
            }

            composable<MainNavigation.Notifications> {
                NotificationsScreen (
                    onBackClicked = { navigator.popBackStack() }
                )
            }

            composable<MainNavigation.Profile> {
                ProfileScreen(
                    onBackClicked = { navigator.popBackStack() }
                )
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

}