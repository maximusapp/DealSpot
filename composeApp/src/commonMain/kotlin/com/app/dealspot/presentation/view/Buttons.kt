package com.app.dealspot.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_18
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_circle_check
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
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (enable) DealSpotDark else grey_middle
        ),
        border = BorderStroke(
            width = dimens_1,
            color = if (enable) DealSpotDark else grey_light
        ),
        shape = RoundedCornerShape(dimens_12),
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
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontSize = text_size_16,
            color = grey_700,
            fontWeight = FontWeight.W500,
            fontFamily = latoFontFamily()
        )
    }
}