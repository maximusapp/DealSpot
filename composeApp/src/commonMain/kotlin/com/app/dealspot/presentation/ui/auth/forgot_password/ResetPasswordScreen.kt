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
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.ui.auth.forgot_password.base.TopBackButtonAndAppName
import com.app.dealspot.presentation.view.BlurWhite80Background
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import com.app.dealspot.presentation.view.DialogErrorWithOkButton
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.confirm_new_password
import dealspot.composeapp.generated.resources.congratulations
import dealspot.composeapp.generated.resources.ic_lock
import dealspot.composeapp.generated.resources.ic_verified_green
import dealspot.composeapp.generated.resources.new_password
import dealspot.composeapp.generated.resources.password_reset_success
import dealspot.composeapp.generated.resources.password_reset_success_description
import dealspot.composeapp.generated.resources.reset_password
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = koinInject(),
    email: String = "",
    verificationCode: String = "",
    onBackToLogin: () -> Unit = {},
    onPasswordReset: () -> Unit = {}
) {
    val resetPasswordState: ResetPasswordState by viewModel.resetPasswordState.collectAsStateWithLifecycle()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage: StringResource? by remember { mutableStateOf(null) }

    println("ResetPasswordScreen. Email: $email, VerificationCode: $verificationCode")

    // Set the verification code in the ViewModel
    if (verificationCode.isNotEmpty()) {
        viewModel.setVerificationCode(verificationCode)
    }

    if (email.isNotEmpty()) {
        viewModel.setEmail(email = email)
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

            // Top back icon and App name
            TopBackButtonAndAppName(
                onBackClicked = {
                    println("ResetPasswordScreen. Back icon clicked")
                    onBackToLogin.invoke()
                }
            )

            SpacerHeight25Dp()

            // Title
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.reset_password),
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            // Description
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Enter your new password for",
                fontSize = text_size_14,
                color = grey_middle,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            // Email address
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = email,
                fontSize = text_size_14,
                color = DealSpotDark,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight25Dp()

            // New password input field
            DealSpotTextInputField(
                modifier = Modifier.focusRequester(focusRequester),
                placeHolderText = stringResource(Res.string.new_password),
                isPasswordField = true,
                leftIcon = Res.drawable.ic_lock,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)
                    }
                )
            ) { password ->
                viewModel.setNewPassword(password)
            }

            SpacerHeight10Dp()

            // Confirm password input field
            DealSpotTextInputField(
                modifier = Modifier,
                placeHolderText = stringResource(Res.string.confirm_new_password),
                isPasswordField = true,
                leftIcon = Res.drawable.ic_lock,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            ) { confirmPassword ->
                viewModel.setConfirmPassword(confirmPassword)
            }

            SpacerHeight20Dp()

            // Reset Password button
            DealSpotDarkButton(
                buttonText = stringResource(Res.string.reset_password),
                onClick = {
                    viewModel.resetPassword()
                }
            )
        }
    }

    when (val state = resetPasswordState) {
        is ResetPasswordState.Success -> {
            showSuccessDialog = true
        }
        is ResetPasswordState.Error -> {
            errorMessage = state.message
            showErrorDialog = true
        }
        else -> {}
    }

    // Loading indicator
    if (resetPasswordState is ResetPasswordState.Loading) {
        BlurWhite80Background()
        CircularLoadingIndicator()
    }

    // Success dialog
    if (showSuccessDialog) {
        DialogErrorWithOkButton(
            dialogTitle = stringResource(Res.string.congratulations),
            dialogText = stringResource(Res.string.password_reset_success_description),
            icon = Res.drawable.ic_verified_green,
            onOkClicked = {
                showSuccessDialog = false
                onPasswordReset()
            }
        )
    }

    // Error dialog
    if (showErrorDialog) {
        println("ResetPasswordScreen. showErrorDialog: $showErrorDialog")
        println("ResetPasswordScreen. errorMessage: $errorMessage")

        errorMessage?.let {
            DialogErrorWithOkButton(
                dialogTitle = "Oops!",
                dialogText = stringResource(it),
                onOkClicked = {
                    showErrorDialog = false
                    viewModel.clearState()
                }
            )
        }
    }
}