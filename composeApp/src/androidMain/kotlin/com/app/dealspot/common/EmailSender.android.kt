package com.app.dealspot.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

actual class EmailSender(private val context: android.content.Context) {
    actual fun openEmailClient(
        to: String,
        subject: String,
        body: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        context.startActivity(Intent.createChooser(intent, "Send email"))
    }
}

@Composable
actual fun rememberEmailSender(): EmailSender {
    val context = LocalContext.current
    return remember { EmailSender(context) }
}

