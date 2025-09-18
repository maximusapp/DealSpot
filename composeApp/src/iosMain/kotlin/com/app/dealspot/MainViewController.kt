package com.app.dealspot

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun MainViewController() = ComposeUIViewController { 
    // Initialize coroutines context for iOS
    val coroutineScope = CoroutineScope(SupervisorJob() + CoroutinesHelper.getMainDispatcher())
    
    App()
}
