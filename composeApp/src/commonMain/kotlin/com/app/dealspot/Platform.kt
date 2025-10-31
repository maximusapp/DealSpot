package com.app.dealspot

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

//@Composable
//expect fun AvatarPicker(currentUri: String, onPick: (String) -> Unit)