package com.app.dealspot.presentation.ui.auth.forgot_password.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.textLatoDisplayLargeDarkW600
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.app_name
import dealspot.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopBackButtonAndAppName(
    onBackClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_back),
            contentDescription = "Back icon",
            modifier = Modifier
                .size(dimens_30)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .clickable(
                    onClick = {
                        println("RegistrationScreen. Back icon clicked")
                        onBackClicked.invoke()
                    }
                ),
            tint = Grey
        )

        textLatoDisplayLargeDarkW600(text = stringResource(Res.string.app_name))
    }
}