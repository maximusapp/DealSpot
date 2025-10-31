package com.app.dealspot.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.dealspot.presentation.theme.SpacerHeight100Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.ui.components.AppMap
import com.app.dealspot.presentation.ui.components.BottomBar

@Composable
internal fun HomeScreen(

) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppMap(modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .padding(bottom = dimens_20)
                .align(Alignment.BottomCenter)
                .wrapContentSize()
        ) {
            BottomBar(
                homeSelected = true,
                onHomeClick = { /* already on Home */ },
                onPlusClick = { /* TODO: handle */ }
            )

            SpacerHeight100Dp()
        }
    }
}