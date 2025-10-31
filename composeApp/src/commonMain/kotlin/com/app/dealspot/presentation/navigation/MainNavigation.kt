package presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavigation {
    @Serializable
    data object Main : MainNavigation

    @Serializable
    data object Profile : MainNavigation

    @Serializable
    data object Settings : MainNavigation

    @Serializable
    data object Chats : MainNavigation

    @Serializable
    data object About : MainNavigation
}