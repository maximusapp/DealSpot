package com.app.dealspot.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_18
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun DealSpotDarkButton(
    buttonText: String = "Some text",
    onClick:() -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = Modifier
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