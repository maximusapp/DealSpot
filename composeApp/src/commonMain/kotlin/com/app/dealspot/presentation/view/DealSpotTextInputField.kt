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
    inputText: (String) -> Unit
) {

    var text by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val trailingPasswordIcon = @Composable {
        val icon = if (passwordVisible) Res.drawable.ic_visibility_on else Res.drawable.ic_visibility_off
        val description = if (passwordVisible) "Hide password" else "Show password"
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(painter = painterResource(icon), contentDescription = description, tint = grey_middle)
        }
    }

    OutlinedTextField(
        value = prevValue.ifEmpty { text },
        label = { Text(text = placeHolderText, color = DealSpotDark) },
        onValueChange = {
            text = it
            inputText.invoke(it)
        },
        modifier = modifier.height(dimens_58).fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(dimens_5),
        leadingIcon = if (leftIcon != null) {
            {
                Icon(
                    painter = painterResource(leftIcon),
                    contentDescription = "Some Icon",
                    tint = leftIconTint
                )
            }
        } else {
            null
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
        isError = false, // Set true if needed to indicate an error state
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions
    )
}