package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.MapCameraState

@Composable
expect fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState? = null,
    onCameraChanged: (MapCameraState) -> Unit = {}
)


