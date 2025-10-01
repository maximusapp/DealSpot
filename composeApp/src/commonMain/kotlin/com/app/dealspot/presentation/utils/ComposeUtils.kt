package com.app.dealspot.presentation.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_15
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.dimens_60

@Composable
fun Spacer10Height() {
    Spacer(modifier = Modifier.height(dimens_10))
}

@Composable
fun Spacer15Height() {
    Spacer(modifier = Modifier.height(dimens_15))
}

@Composable
fun Spacer20Height() {
    Spacer(modifier = Modifier.height(dimens_20))
}

@Composable
fun Spacer30Height() {
    Spacer(modifier = Modifier.height(dimens_30))
}

@Composable
fun Spacer50Height() {
    Spacer(modifier = Modifier.height(dimens_50))
}

@Composable
fun Spacer60Height() {
    Spacer(modifier = Modifier.height(dimens_60))
}

