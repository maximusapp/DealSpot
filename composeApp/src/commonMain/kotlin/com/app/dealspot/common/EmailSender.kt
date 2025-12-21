package com.app.dealspot.common

import androidx.compose.runtime.Composable

expect class EmailSender {
    fun openEmailClient(
        to: String,
        subject: String,
        body: String
    )
}

@Composable
expect fun rememberEmailSender(): EmailSender

