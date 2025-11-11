package com.app.dealspot.presentation.ui.components

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
import com.app.dealspot.business.ScreenType
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.titleSmallW700HeadlineLarge
import com.app.dealspot.presentation.theme.titleSmallW700HeadlineMedium
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(
    title: String = "",
    screenType: ScreenType,
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
                        println("TopBar. Back icon clicked. ScreenType: $screenType")
                        onBackClicked.invoke()
                    }
                ),
            tint = Grey
        )

        titleSmallW700HeadlineLarge(text = title, textColor = DealSpotDark)
    }
}