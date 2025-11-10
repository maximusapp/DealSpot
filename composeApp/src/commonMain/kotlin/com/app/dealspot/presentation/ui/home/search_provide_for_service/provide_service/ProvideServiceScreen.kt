package com.app.dealspot.presentation.ui.home.search_provide_for_service.provide_service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.business.ScreenType
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.ui.components.TopBar
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.chat
import dealspot.composeapp.generated.resources.looking_for_service
import dealspot.composeapp.generated.resources.provide_service
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProvideServiceScreen(
    onBackClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = dimens_20, end = dimens_20, top = dimens_60),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                title = stringResource(Res.string.provide_service),
                screenType = ScreenType.CHATS,
                onBackClicked = {
                    onBackClicked.invoke()
                }
            )

            SpacerHeight25Dp()

            Text(
                text = "Here you can create service you provided",
                fontSize = text_size_24,
                color = Grey,
                fontWeight = FontWeight.W700,
                fontFamily = latoFontFamily()
            )
        }

    }
}