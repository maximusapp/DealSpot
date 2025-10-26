package com.app.dealspot.presentation.ui.auth.forgot_password

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.dealspot.business.VerificationCodeErrorType
import com.app.dealspot.business.VerificationCodeState
import com.app.dealspot.business.constants.LENGTH_6
import com.app.dealspot.presentation.theme.BorderColor
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.PrimaryVariantColor
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.SpacerWidth5Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_42
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_12
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_20
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.ui.auth.forgot_password.base.TopBackButtonAndAppName
import com.app.dealspot.presentation.view.BlurWhite80Background
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealSpotDarkButton
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.enter_verification_code
import dealspot.composeapp.generated.resources.verification_code_description
import dealspot.composeapp.generated.resources.verify_code
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
fun VerificationCodeScreen(
    viewModel: VerificationCodeViewModel = koinInject(),
    email: String = "",
    onBackToLogin: () -> Unit = {},
    onCodeVerified: (String, String) -> Unit
) {
    val verificationState: VerificationCodeState by viewModel.verificationState.collectAsStateWithLifecycle()
    var needShowInfoMessage by remember { mutableStateOf(false) }
    var needShowLoading by remember { mutableStateOf(false) }
    var errorType by remember { mutableStateOf(VerificationCodeErrorType.NONE) }

    var code by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = List(6) { FocusRequester() }

    println("VerificationCodeScreen. Email: $email")

    // Handle state changes
    when (val state = verificationState) {
        is VerificationCodeState.Success -> {
            onCodeVerified(email, viewModel.verificationCode)
        }
        is VerificationCodeState.Error -> {
            errorType = state.type
            needShowInfoMessage = true
            needShowLoading = false
        }
        is VerificationCodeState.Loading -> {
            needShowLoading = true
        }
        else -> {
            needShowLoading = false
        }
    }

    Box(
        modifier = Modifier.padding(horizontal = dimens_20).fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight60Dp()

            // Top back icon and App name
            TopBackButtonAndAppName(
                onBackClicked = {
                    println("VerificationCodeScreen. Back icon clicked")
                    onBackToLogin.invoke()
                }
            )

            SpacerHeight25Dp()

            // Title
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.enter_verification_code),
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily()
            )

            SpacerHeight10Dp()

            // Description
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(Res.string.verification_code_description),
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

            // 6-digit input fields
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                code.forEachIndexed { index, digit ->
                    Box(
                        modifier = Modifier.width(dimens_42)
                            .height(dimens_42)
                            .border(
                                width = dimens_1,
                                color = BorderColor,
                                shape = RoundedCornerShape(dimens_5)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = digit,
                            onValueChange = { value ->
                                if (value.length <= 1 && value.all { it.isDigit() }) {
                                    val newCode = code.toMutableList()
                                    val previousValue = newCode[index]
                                    newCode[index] = value
                                    code = newCode

                                    // Move focus forward when entering a digit
                                    if (value.isNotEmpty() && index < 5) {
                                        focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    }
                                    
                                    // Move focus backward when deleting a digit
                                    if (value.isEmpty() && previousValue.isNotEmpty() && index > 0) {
                                        focusRequesters.getOrNull(index - 1)?.requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .focusRequester(focusRequesters[index])
                                .fillMaxWidth(),
                            singleLine = true,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = text_size_20,
                                color = grey_700
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    SpacerWidth5Dp()
                }
            }

            // Error text when entered code is not full
            if (needShowInfoMessage) {
                SpacerHeight10Dp()
                println("needShowInfoMessage: $needShowInfoMessage")
                println("errorType: $errorType")

                val message = viewModel.getErrorTypeMessage(errorType)
                message?.let {
                    Text(
                        text = stringResource(it),
                        color = PrimaryVariantColor,
                        style = TextStyle(fontSize = text_size_12)
                    )
                }
            }

            SpacerHeight20Dp()

            // Verify Code button
            DealSpotDarkButton(
                buttonText = stringResource(Res.string.verify_code),
                onClick = {
                    println("VerificationCodeScreen. Verify code button clicked")

                    val finalCode = code.joinToString("")
                    if (finalCode.isNotEmpty() && finalCode.length == LENGTH_6) {
                        viewModel.setVerificationCode(finalCode)
                        viewModel.verifyCode()
                        needShowLoading = true
                    } else {
                        errorType = VerificationCodeErrorType.ERROR_CODE_SHOULD_BE_6_DIGITS
                        needShowInfoMessage = true
                    }
                }
            )
        }
    }

    // Loading indicator
    if (needShowLoading) {
        BlurWhite80Background()
        CircularLoadingIndicator()
    }
}