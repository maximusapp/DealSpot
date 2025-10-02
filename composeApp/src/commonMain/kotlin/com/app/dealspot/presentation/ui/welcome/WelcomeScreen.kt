package com.app.dealspot.presentation.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.*
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.utils.Spacer10Height
import com.app.dealspot.presentation.utils.Spacer15Height
import com.app.dealspot.presentation.utils.Spacer30Height
import com.app.dealspot.presentation.utils.Spacer50Height
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.get_started
import dealspot.composeapp.generated.resources.ic_deal_spot
import dealspot.composeapp.generated.resources.login
import dealspot.composeapp.generated.resources.sign_up
import dealspot.composeapp.generated.resources.welcome
import dealspot.composeapp.generated.resources.welcome_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
internal fun WelcomeScreen(
//    viewModel: SplashViewModel,
//    loginViewModel: LoginViewModel,
//    registerViewModel: RegisterViewModel,
//    state: AuthState = AuthState(),
//    events: (AuthEvent) -> Unit = {},
    navigateToMain: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    navigateToRegister: () -> Unit = {},
    navigateToOnboarding: () -> Unit = {}
) {
    val viewModel: WelcomeScreenViewModel = koinInject()

    var isFirstTimeOpened: Boolean? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        isFirstTimeOpened = viewModel.isAppFirstTimeOpened()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimens_20),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo/Icon placeholder
            Image(
                painter = painterResource(Res.drawable.ic_deal_spot),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer30Height()

            // Welcome Title
            Text(
                text = stringResource(Res.string.welcome),
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center
            )

            Spacer10Height()

            // Subtitle
            Text(
                text = stringResource(Res.string.welcome_description),
                fontSize = text_size_16,
                color = grey_700,
                fontWeight = FontWeight.W500,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center
            )

            Spacer50Height()

            if (isFirstTimeOpened == true) {
                // Get Started Button (first-time only)
                DealSpotDarkButton(
                    buttonText = stringResource(Res.string.get_started),
                    onClick = {
                        println("WelcomeScreen. Get started button clicked")

                        viewModel.updateAppFirstTimeOpened()
                        navigateToOnboarding.invoke()
                    }
                )
            } else if (isFirstTimeOpened == false) {
                // Login Button
                DealSpotDarkButton(
                    buttonText = stringResource(Res.string.login),
                    onClick = {
                        println("WelcomeScreen. Login button clicked")

                        viewModel.updateAppFirstTimeOpened()
                        navigateToLogin.invoke()
                    }
                )

                Spacer15Height()

                // Register Button
                DealSpotOutlineButton(
                    buttonText = stringResource(Res.string.sign_up),
                    onClick = {
                        println("WelcomeScreen. Sign Up button clicked")

                        viewModel.updateAppFirstTimeOpened()
                        navigateToRegister.invoke()
                    }
                )
            }
        }
    }

}