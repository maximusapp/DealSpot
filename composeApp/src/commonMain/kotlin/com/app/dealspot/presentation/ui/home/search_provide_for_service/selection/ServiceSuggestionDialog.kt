package com.app.dealspot.presentation.ui.home.search_provide_for_service.selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.app.dealspot.common.rememberEmailSender
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight12Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_16
import com.app.dealspot.presentation.theme.dimens_200
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.white
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun ServiceSuggestionDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    supportEmail: String,
    onSendSuggestion: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }
    var serviceName by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val emailSender = rememberEmailSender()

    if (visible) {
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                shape = RoundedCornerShape(dimens_16),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(dimens_1, RoundedCornerShape(dimens_16))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimens_8, bottom = dimens_8)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.suggest_service),
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = latoFontFamily()),
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                    )

                    DealSpotTextInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
                        placeHolderText = stringResource(Res.string.category_name),
                        isPasswordField = false,
                        imeAction = ImeAction.Next,
                        labelTextColor = Grey
                    ) { category ->
                        categoryName = category
                    }

                    DealSpotTextInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                        placeHolderText = stringResource(Res.string.service_name),
                        isPasswordField = false,
                        imeAction = ImeAction.Done,
                        labelTextColor = Grey
                    ) { service ->
                        serviceName = service
                    }

                    DealSpotDarkButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        buttonText = stringResource(Res.string.send),
                        isEnable = categoryName.isNotBlank() && serviceName.isNotBlank(),
                        onClick = {
                            // Close dialogs first
                            onSendSuggestion()
                            
                            // Open email app with pre-filled email
                            val subject = "Service Suggestion: $serviceName"
                            val body = """
                                Category: $categoryName
                                Service: $serviceName
                                
                                Please add this service to the appropriate category.
                            """.trimIndent()

                            emailSender.openEmailClient(
                                to = supportEmail,
                                subject = subject,
                                body = body
                            )
                        }
                    )

                    SpacerHeight12Dp()

                    DealSpotOutlineButton(
                        modifier = Modifier.width(dimens_200),
                        buttonText = stringResource(Res.string.cancel),
                        buttonHeight = dimens_45,
                        fillWidth = false,
                        shape = RoundedCornerShape(18.dp),
                        containerColor = white,
                        borderColor = grey_middle
                    ) {
                        onDismissRequest()
                    }

                    // Add extra padding at the bottom to ensure content scrolls above keyboard
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

