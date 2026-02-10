package com.app.dealspot.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.dimens_58
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.transparent
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_visibility_off
import dealspot.composeapp.generated.resources.ic_visibility_on
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DealSpotTextInputField(
    modifier: Modifier,
    placeHolderText: String = "Test placeholder",
    isPasswordField: Boolean = false,
    leftIcon: DrawableResource? = null,
    leftIconTint: Color = grey_middle,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Unspecified,
    prevValue: String = "",
    labelTextColor: Color = DealSpotDark,
    isSingleLine: Boolean = true,
    inputText: (String) -> Unit
) {
    BaseDealSpotTextField(
        modifier = modifier,
        value = prevValue,
        onValueChange = inputText,
        isPasswordField = isPasswordField,
        leftIcon = leftIcon,
        leftIconTint = leftIconTint,
        keyboardType = keyboardType,
        keyboardActions = keyboardActions,
        imeAction = imeAction,
        isSingleLine = isSingleLine,
        labelTextColor = labelTextColor,
        placeHolderText = placeHolderText,
        useFloatingLabel = true
    )
}

@Composable
fun DealSpotTextInputFieldWithInnerPlaceholderText(
    modifier: Modifier,
    placeHolderText: String = "Test placeholder",
    isPasswordField: Boolean = false,
    leftIcon: DrawableResource? = null,
    leftIconTint: Color = grey_middle,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Unspecified,
    prevValue: String = "",
    labelTextColor: Color = grey_700,
    isSingleLine: Boolean = true,
    inputText: (String) -> Unit
) {
    BaseDealSpotTextField(
        modifier = modifier,
        value = prevValue,
        onValueChange = inputText,
        isPasswordField = isPasswordField,
        leftIcon = leftIcon,
        leftIconTint = leftIconTint,
        keyboardType = keyboardType,
        keyboardActions = keyboardActions,
        imeAction = imeAction,
        isSingleLine = isSingleLine,
        labelTextColor = labelTextColor,
        placeHolderText = placeHolderText,
        useFloatingLabel = false
    )
}

@Composable
private fun BaseDealSpotTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordField: Boolean,
    leftIcon: DrawableResource?,
    leftIconTint: Color,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    isSingleLine: Boolean,
    labelTextColor: Color,
    placeHolderText: String,
    useFloatingLabel: Boolean
) {
    var text by remember { mutableStateOf(value) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Keep internal text in sync with external value when caller changes prevValue
    LaunchedEffect(value) {
        if (value != text) {
            text = value
        }
    }

    val trailingPasswordIcon = @Composable {
        val icon = if (passwordVisible) Res.drawable.ic_visibility_on else Res.drawable.ic_visibility_off
        val description = if (passwordVisible) "Hide password" else "Show password"
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(painter = painterResource(icon), contentDescription = description, tint = grey_middle)
        }
    }

    val currentValue = text

    OutlinedTextField(
        value = currentValue,
        placeholder = if (!useFloatingLabel) {
            {
                if (currentValue.isEmpty()) {
                    Text(text = placeHolderText, color = labelTextColor)
                }
            }
        } else null,
        label = if (useFloatingLabel) {
            { Text(text = placeHolderText, color = labelTextColor) }
        } else null,
        onValueChange = {
            text = it
            onValueChange.invoke(it)
        },
        modifier = modifier
            .height(dimens_58)
            .fillMaxWidth(),
        singleLine = isSingleLine,
        shape = RoundedCornerShape(dimens_5),
        leadingIcon = leftIcon?.let {
            {
                Icon(
                    painter = painterResource(it),
                    contentDescription = "Some Icon",
                    tint = leftIconTint
                )
            }
        },
        trailingIcon = if (isPasswordField) trailingPasswordIcon else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = transparent,
            unfocusedContainerColor = transparent,
            focusedBorderColor = grey_light,
            unfocusedBorderColor = grey_light
        ),
        visualTransformation = if (isPasswordField) {
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        isError = false,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions
    )
}