package com.app.dealspot.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
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
        val goToLocationTrigger = viewModel.goToCurrentLocationTrigger.collectAsState().value
        AppMap(
            modifier = Modifier.fillMaxSize(),
            initialCamera = camera,
            onCameraChanged = { viewModel.updateCamera(it) },
            goToCurrentLocationTrigger = goToLocationTrigger
        )

        // Top right icons: Notifications and Location
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 50.dp, end = 12.dp)
                .align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Notifications icon
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(25.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        viewModel.resetCurrentLocationTrigger()
                        onOpenNotification.invoke()
                    }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_notifications_bold_500),
                    contentDescription = "Notifications",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
            
            // Location icon (go to current location)
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(25.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        viewModel.goToCurrentLocation()
                    }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MyLocation,
                    contentDescription = "Go to current location",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
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
                onSettingsClick =  {
                    viewModel.resetCurrentLocationTrigger()
                    onOpenSettings.invoke()
                },
                onChatsClick =  {
                    viewModel.resetCurrentLocationTrigger()
                    onOpenChats.invoke()
                },
                onProfileClick = {
                    viewModel.resetCurrentLocationTrigger()
                    onOpenProfile.invoke()
                },
                onPlusClick = { /* TODO: handle */ }
            )

            SpacerHeight100Dp()
        }
    }
}