package com.app.dealspot.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.dealspot.presentation.theme.*
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.welcome
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
            Box(
                modifier = Modifier
                    .size(dimens_100)
                    .background(
                        blue,
                        RoundedCornerShape(dimens_20)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍽️",
                    fontSize = 50.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(dimens_30))

            // Welcome Title
            Text(
                text = stringResource(Res.string.welcome),
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimens_10))

            // Subtitle
            Text(
                text = "Discover amazing food deals and discounts",
                fontSize = text_size_16,
                color = grey_700,
                fontWeight = FontWeight.W400,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimens_50))

            // Get Started Button
            Button(
                onClick = navigateToOnboarding,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens_56),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(dimens_12)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = text_size_18,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )
            }

            Spacer(modifier = Modifier.height(dimens_20))

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

            Spacer(modifier = Modifier.height(dimens_15))

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
