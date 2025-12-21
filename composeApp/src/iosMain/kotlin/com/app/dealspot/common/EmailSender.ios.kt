package com.app.dealspot.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIApplication
import platform.Foundation.NSString
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.NSCharacterSet

actual class EmailSender {
    actual fun openEmailClient(
        to: String,
        subject: String,
        body: String
    ) {
        // Create mailto URL
        val subjectStr = NSString.create(string = subject)
        val bodyStr = NSString.create(string = body)
        val encodedSubject = subjectStr.stringByAddingPercentEncodingWithAllowedCharacters(NSCharacterSet.URLQueryAllowedCharacterSet())?.toString() ?: subject
        val encodedBody = bodyStr.stringByAddingPercentEncodingWithAllowedCharacters(NSCharacterSet.URLQueryAllowedCharacterSet())?.toString() ?: body
        val mailtoUrl = "mailto:$to?subject=$encodedSubject&body=$encodedBody"
        
        val url = platform.Foundation.NSURL.URLWithString(mailtoUrl)
        if (url != null) {
            UIApplication.sharedApplication().openURL(url)
        }
    }
}

@Composable
actual fun rememberEmailSender(): EmailSender {
    return remember { EmailSender() }
}

