package com.app.dealspot.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.*
import com.app.dealspot.presentation.utils.DealSpotDarkButton
import com.app.dealspot.presentation.utils.Spacer10Height
import com.app.dealspot.presentation.utils.Spacer15Height
import com.app.dealspot.presentation.utils.Spacer20Height
import com.app.dealspot.presentation.utils.Spacer30Height
import com.app.dealspot.presentation.utils.Spacer50Height
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_deal_spot
import dealspot.composeapp.generated.resources.welcome
import dealspot.composeapp.generated.resources.welcome_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                fontWeight = FontWeight.W400,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center
            )

            Spacer50Height()

            // Get Started Button
            DealSpotDarkButton(
                buttonText = "Get started",
                onClick = {
                    println("WelcomeScreen. Get started button clicked")
                    navigateToOnboarding.invoke()
                }
            )

            Spacer20Height()

            // Login Button
            OutlinedButton(
                onClick = navigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens_56),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = blue
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                shape = RoundedCornerShape(dimens_12)
            ) {
                Text(
                    text = "Login",
                    fontSize = text_size_18,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )
            }

            Spacer15Height()

            // Register Link
            TextButton(
                onClick = navigateToRegister
            ) {
                Text(
                    text = "Don't have an account? Sign Up",
                    fontSize = text_size_14,
                    color = grey_700,
                    fontWeight = FontWeight.W500,
                    fontFamily = latoFontFamily()
                )
            }
        }
    }

}
