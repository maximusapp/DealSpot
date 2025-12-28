package com.app.dealspot.presentation.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.app.dealspot.presentation.theme.Grey
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun DialogErrorWithOkButton(
    onOkClicked: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: DrawableResource? = null,
    buttonText: String = "OK"
) {
    AlertDialog(
        icon = {
            if (icon != null) Icon(painterResource(icon), contentDescription = "Example Icon")
        },
        title = {
            Text(
                text = dialogTitle,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {  },
        confirmButton = {
            TextButton(
                onClick = { onOkClicked() }
            ) {
                Text(
                    text = buttonText,
                    color = Grey
                )
            }
        }
    )
}

@Composable
fun DialogSuccessWithOkButton(
    onOkClicked: () -> Unit,
    dialogTitle: String = "Success",
    dialogText: String,
    icon: DrawableResource? = null,
    buttonText: String = "OK"
) {
    AlertDialog(
        icon = {
            if (icon != null) Icon(painterResource(icon), contentDescription = "Success Icon")
        },
        title = {
            Text(
                text = dialogTitle,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = { onOkClicked() }
            ) {
                Text(
                    text = buttonText,
                    color = Grey
                )
            }
        }
    )
}