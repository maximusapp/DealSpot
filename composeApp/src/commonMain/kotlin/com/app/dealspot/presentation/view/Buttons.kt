package com.app.dealspot.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_18
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DealSpotDarkButton(
    modifier: Modifier = Modifier,
    buttonText: String = "Some text",
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(dimens_50),
        colors = ButtonDefaults.buttonColors(
            containerColor = DealSpotDark,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(dimens_12)
    ) {
        Text(
            text = buttonText,
            fontSize = text_size_18,
            fontWeight = FontWeight.W600,
            fontFamily = latoFontFamily()
        )
    }
}

@Composable
@Preview
fun DealSpotOutlineButton(
    modifier: Modifier = Modifier,
    buttonText: String = "Some text",
    enable: Boolean = true,
    textSize: TextUnit = text_size_16,
    buttonHeight: Dp = dimens_50,
    fillWidth: Boolean = true,
    shape: Shape = RoundedCornerShape(dimens_12),
    containerColor: Color = Color.Transparent,
    borderColor: Color = if (enable) DealSpotDark else grey_light,
    onClick: () -> Unit = {}
) {
    val appliedModifier = if (fillWidth) modifier.fillMaxWidth() else modifier

    OutlinedButton(
        onClick = onClick,
        modifier = appliedModifier
            .height(buttonHeight),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (enable) DealSpotDark else grey_middle,
            containerColor = containerColor
        ),
        border = BorderStroke(
            width = dimens_1,
            color = borderColor
        ),
        shape = shape,
        enabled = enable
    ) {
        Text(
            text = buttonText,
            fontSize = textSize,
            fontWeight = FontWeight.W600,
            fontFamily = latoFontFamily()
        )
    }
}

@Composable
@Preview
fun DealSpotTextButton(
    modifier: Modifier = Modifier,
    buttonText: String = "Some text",
    fontWeight: FontWeight = FontWeight.W500,
    textColor: Color = grey_700,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontSize = text_size_16,
            color = textColor,
            fontWeight = fontWeight,
            fontFamily = latoFontFamily()
        )
    }
}