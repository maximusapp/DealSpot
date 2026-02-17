package com.app.dealspot.domain.model

data class MapCameraState(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float = 14f,
    val bearing: Float = 0f,
    val tilt: Float = 0f
)