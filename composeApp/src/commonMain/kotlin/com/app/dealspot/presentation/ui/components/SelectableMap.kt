package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.MapCameraState

@Composable
expect fun SelectableMap(
    modifier: Modifier,
    initialCamera: MapCameraState? = null,
    selectedPosition: LatLngEntity? = null,
    onCameraChanged: (MapCameraState) -> Unit = {},
    onMapClick: (LatLngEntity) -> Unit,
    onLocationAvailable: (LatLngEntity) -> Unit
)

