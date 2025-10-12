package com.app.dealspot.presentation.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight15Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight40Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.SpacerWidth5Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.textLatoDisplayLargeDarkW600
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.app_name
import dealspot.composeapp.generated.resources.do_not_have_account
import dealspot.composeapp.generated.resources.email
import dealspot.composeapp.generated.resources.forgot_password
import dealspot.composeapp.generated.resources.ic_lock
import dealspot.composeapp.generated.resources.ic_mail
import dealspot.composeapp.generated.resources.lets_get_started
import dealspot.composeapp.generated.resources.login
import dealspot.composeapp.generated.resources.password
import dealspot.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit = {}
) {

    var isLoginButtonClicked by remember { mutableStateOf(false) }
//    val loginDataValidationState: LoginDataValidation by viewModel.loginDataValidationState.collectAsStateWithLifecycle()
//    val emailThatNeedToConfirm: String by viewModel.emailConfirmationState.collectAsStateWithLifecycle()
//    var showEmailVerificationDialog by remember { mutableStateOf(true) }
//    val loginState: LoginState by viewModel.loginState.collectAsStateWithLifecycle()

//    viewModel.checkEmailConfirmationState()

    Box(
        modifier = Modifier.padding(horizontal = dimens_20).fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SpacerHeight60Dp()

            textLatoDisplayLargeDarkW600(text = stringResource(Res.string.app_name))

            SpacerHeight25Dp()

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.login),
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.lets_get_started),
                fontSize = text_size_14,
                color = grey_middle,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight25Dp()

            DealSpotTextInputField(
                modifier = Modifier,
                placeHolderText = stringResource(Res.string.email),
                isPasswordField = false,
                leftIcon = Res.drawable.ic_mail
            ) { email ->
                println("Login screen. Email is: $email")
//                events(AuthEvent.OnUpdateEmailLogin(email))
            }

            SpacerHeight10Dp()

            DealSpotTextInputField(
                modifier = Modifier,
                placeHolderText = stringResource(Res.string.password),
                isPasswordField = true,
                leftIcon = Res.drawable.ic_lock
            ) { password ->
                println("Login screen. Password is: $password")
//                events(AuthEvent.OnUpdatePasswordLogin(password))
            }

            SpacerHeight15Dp()

            Text(
                modifier = Modifier.align(Alignment.End).clickable {
                    println("Login screen. Forgot password text clicked.")
//                        navigateToMaingateToRegister.invoke()
                },
                text = stringResource(Res.string.forgot_password),
                fontSize = text_size_14,
                color = DealSpotDark,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight20Dp()

            DealSpotDarkButton(
                buttonText = stringResource(Res.string.login),
                onClick = {
                    println("LoginScreen. Login button clicked")

                    isLoginButtonClicked = true
                }
            )

            SpacerHeight20Dp()

            Row {
                Text(
                    modifier = Modifier,
                    text = stringResource(Res.string.do_not_have_account),
                    fontSize = text_size_14,
                    color = grey_middle,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerWidth5Dp()

                Text(
                    modifier = Modifier.clickable {
                        println("Login screen. Sign up text clicked.")
                        navigateToRegister.invoke()
                    },
                    text = stringResource(Res.string.sign_up),
                    fontSize = text_size_14,
                    color = DealSpotDark,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )
            }

            if (isLoginButtonClicked) {
                isLoginButtonClicked = false
//                viewModel.checkDataValidationState()
            }

//            when (val actualState = loginDataValidationState) {
//                is LoginDataValidation.FAILURE -> {
//                    println("loginDataValidationState. State: $actualState")
//
//                    DialogErrorWithOkButton(
//                        dialogTitle = "Opps!",
//                        dialogText = actualState.message,
//                        icon = Icons.Outlined.ErrorOutline,
//                        onOkClicked = {
//                            println("DialogErrorWithOkButton. Ok button clicked")
//
//                            viewModel.clearDataValidationState()
//                            isLoginButtonClicked = false
//
//                            viewModel.state.value = viewModel.state.value.copy(isLoading = false)
//                        },
//                        buttonText = stringResource(Res.string.ok)
//                    )
//                }
//
//                is LoginDataValidation.SUCCESS -> {
//                    println("loginDataValidationState. State: $actualState")
//
//                    viewModel.state.value = viewModel.state.value.copy(isLoading = true)
//
//                    viewModel.clearDataValidationState()
//                    viewModel.login(viewModel.state.value.emailLogin, viewModel.state.value.passwordLogin)
//                }
//
//                is LoginDataValidation.NONE -> {
//                    /** Ignore **/
//                }
//            }

//            println("state.isLoading: ${state.isLoading}")
//            if (state.isLoading) {
//                CircularLoadingIndicator(iconDrawableId = Res.drawable.ic_loading_grey, paddingBottom = dimens_40)
//            }

        }

    }

//    if (emailThatNeedToConfirm.isNotEmpty() && showEmailVerificationDialog) {
//        println("Observe emailThatNeedToConfirm: $emailThatNeedToConfirm")
//
//        EmailVerificationScreen(
//            email = emailThatNeedToConfirm,
//            onLogin = {
//                /* Login after email verified */
//                showEmailVerificationDialog = false
//                viewModel.loginAfterEmailVerified()
//            },
//            onDismissRequest = {
//                showEmailVerificationDialog = false // Close the dialog
//            }
//        )
//    }

//    when (val loginStateResp = loginState) {
//        is LoginState.Loading -> {
//
//        }
//
//        is LoginState.Success -> {
//            println("loginStateResp. State: $loginStateResp")
//            viewModel.saveUserCredentialsToDataStore(loginStateResp.response.tokenResponse)
//            navigateToMain.invoke()
//        }
//
//        is LoginState.Error -> {
//            viewModel.state.value = viewModel.state.value.copy(isLoading = false)
//            var message = ""
//
//            when (loginStateResp.type) {
//                is IdentityProviderException.NotAuthorized -> {
//                    message = stringResource(Res.string.incorrect_username_or_password)
//                }
//            }
//
//            DialogErrorWithOkButton(
//                dialogTitle = "Opps!",
//                dialogText = message,
//                icon = Icons.Outlined.ErrorOutline,
//                onOkClicked = {
//                    println("DialogErrorWithOkButton. Ok button clicked")
//
//                    viewModel.clearLoginState()
//                    isLoginButtonClicked = false
//                },
//                buttonText = stringResource(Res.string.ok)
//            )
//        }
//
//        is LoginState.None -> {
//            /** Ignore **/
//        }
//    }

}