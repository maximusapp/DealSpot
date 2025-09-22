package com.app.dealspot.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Suppress("ComposableNaming")
@Composable
fun textLatoDisplayLargeBlueW800(
    text: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.displayLarge,
        color = blueSplashText,
        fontWeight = FontWeight.W800,
        fontFamily = latoFontFamily()
    )
}

@Suppress("ComposableNaming")
@Composable
fun textLatoDisplayLargeBlueW600(
    text: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.displayMedium,
        color = blueSplashText,
        fontWeight = FontWeight.W600,
        fontFamily = latoFontFamily()
    )
}

@Suppress("ComposableNaming")
@Composable
fun textLatoHeadlineLargeW700(
    text: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = Grey,
        fontWeight = FontWeight.W700,
        fontFamily = latoFontFamily()
    )
}

@Suppress("ComposableNaming")
@Composable
fun textLatoHeadlineLargeW700HeadlineSmall(
    text: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = grey_700,
        fontWeight = FontWeight.W700,
        fontFamily = latoFontFamily()
    )
}

@Suppress("ComposableNaming")
@Composable
fun textLatoSmall(
    text: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = grey_700,
        fontWeight = FontWeight.W600,
        fontFamily = latoFontFamily()
    )
}
