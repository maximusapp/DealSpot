package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.domain.model.DealEntity
import com.app.dealspot.domain.model.MapCameraState

@Composable
expect fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState? = null,
    onCameraChanged: (MapCameraState) -> Unit = {},
    goToCurrentLocationTrigger: Int = 0,
    deals: List<DealEntity> = emptyList(),
    selectedDeal: DealEntity? = null,
    onDealSelected: (DealEntity?) -> Unit = {}
)


