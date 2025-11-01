package com.app.dealspot.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.SpacerHeight100Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.ui.components.AppMap
import com.app.dealspot.presentation.ui.components.BottomBar
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_notifications_bold_500
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    onOpenNotification: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenChats: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: HomeScreenViewModel = koinInject()
        val camera = viewModel.cameraState.collectAsState().value
        AppMap(
            modifier = Modifier.fillMaxSize(),
            initialCamera = camera,
            onCameraChanged = { viewModel.updateCamera(it) }
        )

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 50.dp, end = 12.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(50.dp))
                .clickable {
                    onOpenNotification.invoke()
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_notifications_bold_500),
                    contentDescription = "Chats",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = dimens_20)
                .align(Alignment.BottomCenter)
                .wrapContentSize()
        ) {
            BottomBar(
                homeSelected = true,
                settingsSelected = false,
                chatsSelected = false,
                onHomeClick = { /* already on Home */ },
                onSettingsClick = onOpenSettings,
                onChatsClick = onOpenChats,
                onProfileClick = onOpenProfile,
                onPlusClick = { /* TODO: handle */ }
            )

            SpacerHeight100Dp()
        }
    }
}