package com.app.dealspot

import androidx.compose.ui.window.ComposeUIViewController
import com.app.dealspot.common.Context
import com.app.dealspot.presentation.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

fun MainViewController() = ComposeUIViewController { 
    // Initialize coroutines context for iOS
    val coroutineScope = CoroutineScope(SupervisorJob() + CoroutinesHelper.getMainDispatcher())

    App(Context())
}
