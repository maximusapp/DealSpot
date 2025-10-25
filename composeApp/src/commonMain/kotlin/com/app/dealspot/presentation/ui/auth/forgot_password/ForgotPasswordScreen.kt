package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.textLatoDisplayLargeDarkW600
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_22
import com.app.dealspot.presentation.view.BlurWhite80Background
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import com.app.dealspot.presentation.view.DialogErrorWithOkButton
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.app_name
import dealspot.composeapp.generated.resources.email
import dealspot.composeapp.generated.resources.forgot_password_description
import dealspot.composeapp.generated.resources.forgot_password_title
import dealspot.composeapp.generated.resources.ic_back
import dealspot.composeapp.generated.resources.ic_mail
import dealspot.composeapp.generated.resources.send_code
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = koinInject(),
    onBackToLogin: () -> Unit = {},
    onCodeSent: (String) -> Unit = {}
) {
    val forgotPasswordState: ForgotPasswordState by viewModel.forgotPasswordState.collectAsStateWithLifecycle()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Handle state changes
    LaunchedEffect(forgotPasswordState) {
        when (val state = forgotPasswordState) {
            is ForgotPasswordState.Success -> {
                onCodeSent(viewModel.email)
            }

            is ForgotPasswordState.Error -> {
                errorMessage = state.message
                showErrorDialog = true
            }

            else -> {  }
        }
    }

    Box(
        modifier = Modifier.padding(horizontal = dimens_20).fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight60Dp()
            
            // App name
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "Back icon",
                    modifier = Modifier
                        .size(dimens_30)
                        .align(Alignment.CenterStart)
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                println("RegistrationScreen. Back icon clicked")
                                onBackToLogin.invoke()
                            }
                        ),
                    tint = Grey
                )

                textLatoDisplayLargeDarkW600(text = stringResource(Res.string.app_name))
            }
            SpacerHeight25Dp()

            // Title
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.forgot_password_title),
                fontSize = text_size_22,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            // Description
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.forgot_password_description),
                fontSize = text_size_14,
                color = grey_middle,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight25Dp()

            // Email input field
            DealSpotTextInputField(
                modifier = Modifier.focusRequester(focusRequester),
                placeHolderText = stringResource(Res.string.email),
                isPasswordField = false,
                leftIcon = Res.drawable.ic_mail,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            ) { email ->
                viewModel.setEmail(email)
            }

            SpacerHeight20Dp()

            // Send Code button
            DealSpotDarkButton(
                buttonText = stringResource(Res.string.send_code),
                onClick = {
                    viewModel.sendCodeToEmail()
                }
            )
        }
    }

    // Loading indicator
    if (forgotPasswordState is ForgotPasswordState.Loading) {
        BlurWhite80Background()
        CircularLoadingIndicator()
    }


    // Error dialog
    if (showErrorDialog) {
        DialogErrorWithOkButton(
            dialogTitle = "Error",
            dialogText = errorMessage,
            onOkClicked = {
                showErrorDialog = false
                viewModel.clearState()
            }
        )
    }
}