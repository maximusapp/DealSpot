package com.app.dealspot.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.app.dealspot.data.model.MapCameraState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(

) : ViewModel() {
    private val _cameraState = MutableStateFlow<MapCameraState?>(null)
    val cameraState: StateFlow<MapCameraState?> = _cameraState.asStateFlow()

    fun updateCamera(state: MapCameraState) {
        _cameraState.value = state
    }

}