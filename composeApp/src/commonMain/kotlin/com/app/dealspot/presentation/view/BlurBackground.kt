package com.app.dealspot.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.presentation.theme.white_80_transparent

@Composable
fun BlurWhite80Background() {
    Box(
        modifier = Modifier.fillMaxSize().background(color = white_80_transparent).clickable(enabled = false) { }
    )
}