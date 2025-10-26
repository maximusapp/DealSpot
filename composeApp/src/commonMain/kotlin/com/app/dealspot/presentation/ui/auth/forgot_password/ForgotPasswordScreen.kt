package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.dealspot.business.ResetPasswordState
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_22
import com.app.dealspot.presentation.ui.auth.forgot_password.base.TopBackButtonAndAppName
import com.app.dealspot.presentation.view.BlurWhite80Background
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import com.app.dealspot.presentation.view.DialogErrorWithOkButton
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.email
import dealspot.composeapp.generated.resources.forgot_password
import dealspot.composeapp.generated.resources.forgot_password_description
import dealspot.composeapp.generated.resources.ic_error_red
import dealspot.composeapp.generated.resources.ic_mail
import dealspot.composeapp.generated.resources.send_code
import org.jetbrains.compose.resources.StringResource
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
    val forgotPasswordState: ResetPasswordState by viewModel.forgotPasswordState.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage: StringResource? by remember { mutableStateOf(null) }

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
            
            // Top back icon and App name
            TopBackButtonAndAppName(
                onBackClicked = {
                    println("ForgotPasswordScreen. Back icon clicked")
                    onBackToLogin.invoke()
                }
            )

            SpacerHeight25Dp()

            // Title
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.forgot_password),
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
                    println("ForgotPasswordScreen. Send code button clicked")
                    viewModel.sendCodeToEmail()
                }
            )
        }
    }

    when (val state = forgotPasswordState) {
        is ResetPasswordState.Success -> {
            onCodeSent(viewModel.email)
        }

        is ResetPasswordState.Error -> {
            errorMessage = state.message
            showErrorDialog = true
        }

        else -> {  }
    }

    // Loading indicator
    if (forgotPasswordState is ResetPasswordState.Loading) {
        BlurWhite80Background()
        CircularLoadingIndicator()
    }

    // Error dialog
    if (showErrorDialog) {
        errorMessage?.let { message ->
            DialogErrorWithOkButton(
                dialogTitle = "Opps!",
                dialogText = stringResource(message),
                icon = Res.drawable.ic_error_red,
                onOkClicked = {
                    showErrorDialog = false
                    viewModel.clearState()
                }
            )
        }
    }
}