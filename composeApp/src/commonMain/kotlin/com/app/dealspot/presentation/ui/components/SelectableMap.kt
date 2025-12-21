package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.dealspot.data.model.LatLngEntity

@Composable
expect fun SelectableMap(
    modifier: Modifier,
    selectedPosition: LatLngEntity? = null,
    onMapClick: (LatLngEntity) -> Unit,
    onLocationAvailable: (LatLngEntity) -> Unit
)





