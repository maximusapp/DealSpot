package com.app.dealspot.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_24

@Composable
internal fun HomeScreen(

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimens_20),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to Home screen",
            fontSize = text_size_24,
            color = Grey,
            fontWeight = FontWeight.W700,
            fontFamily = latoFontFamily()
        )
    }

}