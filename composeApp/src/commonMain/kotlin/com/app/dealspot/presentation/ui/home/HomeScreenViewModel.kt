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
    
    private val _goToCurrentLocationTrigger = MutableStateFlow(0)
    val goToCurrentLocationTrigger: StateFlow<Int> = _goToCurrentLocationTrigger.asStateFlow()

    fun updateCamera(state: MapCameraState) {
        _cameraState.value = state
    }
    
    fun goToCurrentLocation() {
        _goToCurrentLocationTrigger.value++
    }

}