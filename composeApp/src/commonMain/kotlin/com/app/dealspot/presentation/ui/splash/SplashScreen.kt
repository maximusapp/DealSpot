package com.app.dealspot.presentation.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_24
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun SplashScreen(
//    viewModel: SplashViewModel,
//    loginViewModel: LoginViewModel,
//    registerViewModel: RegisterViewModel,
//    state: AuthState = AuthState(),
//    events: (AuthEvent) -> Unit = {},
    navigateToMain: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    navigateToRegister: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimens_20),
        contentAlignment = Alignment.Center
    ) {
        // Your main content goes here
        Text(
            text = "Welcome to Splash screen",
            fontSize = text_size_24,
            color = Grey,
            fontWeight = FontWeight.W700,
            fontFamily = latoFontFamily()
        )
    }

}
