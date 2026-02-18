package com.app.dealspot.presentation.ui.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.dealspot.business.ApplyDealRequestType
import com.app.dealspot.business.DealRequestState
import com.app.dealspot.domain.model.DealEntity
import com.app.dealspot.presentation.theme.SpacerHeight100Dp
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.ui.components.AppMap
import com.app.dealspot.presentation.ui.components.BottomBar
import com.app.dealspot.presentation.view.BlurWhite80Background
import com.app.dealspot.presentation.view.CircularLoadingIndicator
import com.app.dealspot.presentation.view.DealInfoBottomSheet
import com.app.dealspot.presentation.view.FilterBottomSheet
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_filter_active
import dealspot.composeapp.generated.resources.ic_filter_inactive_1
import dealspot.composeapp.generated.resources.ic_my_location
import dealspot.composeapp.generated.resources.ic_no_notifications
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Preview
@Composable
internal fun HomeScreen(
    onOpenNotification: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
    onOpenChats: () -> Unit = {},
    onOpenProfile: () -> Unit = {},
    onLookingService: () -> Unit = {},
    onProvideService: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: HomeScreenViewModel = koinInject()
        val camera = viewModel.cameraState.collectAsState().value
        val goToLocationTrigger = viewModel.goToCurrentLocationTrigger.collectAsState().value
        val isLoading = viewModel.isLoading.collectAsState().value
        val isFilterActive = viewModel.isFilterActive.collectAsState().value
        val filterType = viewModel.filterType.collectAsState().value
        val deals = viewModel.deals.collectAsState().value
        var showActionSheet by remember { mutableStateOf(false) }
        var showFilterSheet by remember { mutableStateOf(false) }
//        var selectedDeal by remember { mutableStateOf<DealEntity?>(null) }
        val selectedDeal = viewModel.selectedDeal.collectAsState().value
        val currentUserSub = viewModel.currentUserSub.value
        val sendDealRequestState by viewModel.sendDealRequestState.collectAsState()

        // Initialize user when screen opens
        LaunchedEffect(Unit) {
            viewModel.initializeUser()
            viewModel.getCurrentUserSub()
        }

        AppMap(
            modifier = Modifier.fillMaxSize(),
            initialCamera = camera,
            onCameraChanged = { viewModel.updateCamera(it) },
            goToCurrentLocationTrigger = goToLocationTrigger,
            deals = deals,
            selectedDeal = selectedDeal,
            onDealSelected = { viewModel.setSelectedDeal(deal = it) }
        )

        // Top right icons: Location, Filter, and Notifications
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 150.dp, end = dimens_12)
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(dimens_12)
        ) {
            // Filter icon
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(25.dp))
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        showFilterSheet = true
                    }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isFilterActive) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_filter_active),
                        contentDescription = "Filter",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.ic_filter_inactive_1),
                        contentDescription = "Filter",
                        tint = Color.Gray,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            
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
                    painter = painterResource(Res.drawable.ic_no_notifications),
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
                    painter = painterResource(Res.drawable.ic_my_location),
                    contentDescription = "Go to current location",
                    tint = Color.Unspecified,
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
                onPlusClick = { showActionSheet = true }
            )

            SpacerHeight100Dp()
        }

        HomeActionSheet(
            visible = showActionSheet,
            onDismissRequest = { showActionSheet = false },
            onFindServiceClick = {
                viewModel.resetCurrentLocationTrigger()
                onLookingService.invoke()
            },
            onProvideServiceClick = {
                viewModel.resetCurrentLocationTrigger()
                onProvideService.invoke()
            }
        )

        // Filter bottom sheet
        FilterBottomSheet(
            visible = showFilterSheet,
            selectedFilterType = filterType,
            onFilterTypeChanged = { type ->
                viewModel.setFilterType(type)
            },
            onClose = { showFilterSheet = false },
            onApplyFilter = { category, service ->
                viewModel.applyFilter(category, service)
            },
            onClearFilter = {
                viewModel.resetFilter()
            }
        )

        // Loading indicator
        if (isLoading) {
            BlurWhite80Background()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularLoadingIndicator()
            }
        }

        // Deal info sheet — on top of map, buttons, and bottom bar
        DealInfoBottomSheet(
            visible = selectedDeal != null,
            deal = selectedDeal,
            currentUserSub = currentUserSub,
            showLoading = sendDealRequestState == DealRequestState.Loading,
            sendDealRequestState = sendDealRequestState,
            onDismiss = { viewModel.setSelectedDeal(deal = null) },
            onSendRequest = {
                viewModel.getCurrentUserSub()
                viewModel.sendRequestToDeal(selectedDeal = selectedDeal, requestType = ApplyDealRequestType.SEND_REQUEST)
            },
            onCancelRequest = {
                viewModel.sendRequestToDeal(selectedDeal = selectedDeal, requestType = ApplyDealRequestType.CANCEL_REQUEST)
            },
            onClearSendDealRequestState = { viewModel.clearSendDealRequestState() }
        )
    }
}