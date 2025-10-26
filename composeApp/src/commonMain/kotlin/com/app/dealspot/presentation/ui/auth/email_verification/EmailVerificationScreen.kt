package com.app.dealspot.presentation.ui.auth.email_verification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.dealspot.business.ResendVerificationCodeState
import com.app.dealspot.business.VerificationCodeErrorType
import com.app.dealspot.business.VerificationEmailState
import com.app.dealspot.business.constants.LENGTH_6
import com.app.dealspot.business.constants.TIME_5_SEC
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.PrimaryVariantColor
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight15Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerWidth10Dp
import com.app.dealspot.presentation.theme.blueSplashText
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.grey_50_transparent
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_12
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_22
import com.app.dealspot.presentation.theme.white
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.SixDigitsView
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.check_your_email
import dealspot.composeapp.generated.resources.confirmation_code_was_resent
import dealspot.composeapp.generated.resources.did_not_get_code
import dealspot.composeapp.generated.resources.enter_six_numbers_of_code
import dealspot.composeapp.generated.resources.enter_verification_code
import dealspot.composeapp.generated.resources.ic_close
import dealspot.composeapp.generated.resources.ic_mail
import dealspot.composeapp.generated.resources.invalid_verification_code
import dealspot.composeapp.generated.resources.send_new_code
import dealspot.composeapp.generated.resources.verify_email
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
fun EmailVerificationScreen(
    email: String,
    needShowCloseIcon: Boolean = true,
    codeLength: Int = 6,
    viewModel: EmailVerificationScreenViewModel = koinInject(),
    onLogin: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val emailVerificationState: VerificationEmailState by viewModel.verificationEmailState.collectAsStateWithLifecycle()
    val resendVerificationCodeState: ResendVerificationCodeState by viewModel.resendConfirmationCodeState.collectAsStateWithLifecycle()

    var code by remember { mutableStateOf(List(codeLength) { "" }) }
    var needShowInfoMessage by remember { mutableStateOf(false) }
    var needShowLoading by remember { mutableStateOf(false) }
    var errorType by remember { mutableStateOf(VerificationCodeErrorType.NONE) }

    if (needShowInfoMessage) {
        LaunchedEffect(Unit) {
            delay(TIME_5_SEC)
            needShowInfoMessage = false
        }
    }

    println("EmailVerificationScreen. Launch")

    when (val actualState = emailVerificationState) {
        is VerificationEmailState.Success -> {
            println("emailVerificationState. State: $actualState")

            viewModel.setEmailVerified()
            viewModel.processEmailVerificationState()
            onLogin.invoke()
        }

        is VerificationEmailState.Error -> {
            println("emailVerificationState. State: $actualState")

            errorType = VerificationCodeErrorType.CONFIRMATION_CODE_INCORRECT
            needShowInfoMessage = true
            needShowLoading = false
            viewModel.processEmailVerificationState()
        }

        is VerificationEmailState.None -> {
            /** Ignore **/
        }
    }

    when (val actualState = resendVerificationCodeState) {
        is ResendVerificationCodeState.Success -> {
            println("ResendVerificationCodeState. State: $actualState")

            errorType = VerificationCodeErrorType.CONFIRMATION_CODE_RESEND
            needShowInfoMessage = true
            needShowLoading = false

            viewModel.processResendCodeState()
        }

        is ResendVerificationCodeState.Error -> {
            println("ResendVerificationCodeState. State: $actualState")

            viewModel.processResendCodeState()
        }

        is ResendVerificationCodeState.None -> {
            /** Ignore */
        }
    }

    Box(
        modifier = Modifier
            .clickable(enabled = false) { }
            .fillMaxSize()
            .background(color = grey_50_transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(dimens_30)
                .background(white, shape = RoundedCornerShape(dimens_10)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (needShowCloseIcon) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = dimens_5, end = dimens_5)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_close),
                            contentDescription = ""
                        )
                    }
                }
            }

            Icon(
                painter = painterResource(Res.drawable.ic_mail),
                contentDescription = null,
                modifier = Modifier.size(dimens_50)
            )

            SpacerHeight15Dp()

            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(Res.string.check_your_email),
                fontSize = text_size_22,
                color = Grey,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(Res.string.enter_verification_code),
                color = Color.Gray,
            )

            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = email,
                fontSize = text_size_16,
                color = Grey,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily()
            )

            SpacerHeight25Dp()

            SixDigitsView { newCode ->
                println("SixDigitsView. Code: $newCode")
                code = newCode
            }

            /* Error text when entered code is not full */
            if (needShowInfoMessage) {
                SpacerHeight10Dp()

                when(errorType) {
                    VerificationCodeErrorType.ERROR_CODE_SHOULD_BE_6_DIGITS -> {
                        Text(text = stringResource(Res.string.enter_six_numbers_of_code), color = PrimaryVariantColor, style = TextStyle(fontSize = text_size_12))
                    }

                    VerificationCodeErrorType.CONFIRMATION_CODE_INCORRECT -> {
                        Text(text = stringResource(Res.string.invalid_verification_code), color = PrimaryVariantColor, style = TextStyle(fontSize = text_size_12))
                    }

                    VerificationCodeErrorType.CONFIRMATION_CODE_RESEND -> {
                        Text(text = stringResource(Res.string.confirmation_code_was_resent), color = DealSpotDark, style = TextStyle(fontSize = text_size_12))
                    }

                    VerificationCodeErrorType.NONE -> {
                        /** Ignore */
                    }
                }
            }

            SpacerHeight10Dp()

            /* Loading indicator */
            if (needShowLoading) {
                SpacerHeight15Dp()
                CircularLoadingIndicator()
                SpacerHeight25Dp()
            } else {
                Row {
                    Text(stringResource(Res.string.did_not_get_code), color = Color.Gray)
                    SpacerHeight10Dp()
                    SpacerWidth10Dp()

                    Text(
                        stringResource(Res.string.send_new_code),
                        color = blueSplashText,
                        modifier = Modifier.clickable {
                            needShowLoading = true
                            viewModel.resendCode()
                        }
                    )
                }

                SpacerHeight25Dp()

                DealSpotDarkButton (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = dimens_20),
                    buttonText = stringResource(Res.string.verify_email)
                ) {
                    println("EmailVerificationScreen. Verify code button clicked")

                    val finalCode = code.joinToString("")
                    if (finalCode.isNotEmpty() && finalCode.length == LENGTH_6) {
                        println("EmailVerificationScreen. Verify code is OK. Need verification. Code: $finalCode")
                        viewModel.verifyEmail(code = finalCode)
                        needShowLoading = true

                    } else {
                        println("EmailVerificationScreen. Verify code is not OK. Check verification code. Code: $finalCode")
                        errorType = VerificationCodeErrorType.ERROR_CODE_SHOULD_BE_6_DIGITS
                        needShowInfoMessage = true
                    }
                }

                SpacerHeight25Dp()
            }
        }
    }
}