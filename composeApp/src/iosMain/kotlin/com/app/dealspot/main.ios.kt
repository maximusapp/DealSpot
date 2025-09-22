package com.app.dealspot

import androidx.compose.ui.window.ComposeUIViewController
import com.app.dealspot.presentation.App
import com.app.dealspot.common.Context

fun mainViewController() = ComposeUIViewController { App(Context()) }

