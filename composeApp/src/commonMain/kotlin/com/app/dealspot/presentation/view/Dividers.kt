package com.app.dealspot.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.grey_light

@Composable
fun GreyLightLineHeight1DpDivider() {
    Box(
        modifier = Modifier.fillMaxWidth().height(height = dimens_1).background(color = grey_light)
    )
}

@Composable
fun HorizontalThicknessDividerAlpha008() {
    HorizontalDivider(
        Modifier,
        DividerDefaults.Thickness,
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}