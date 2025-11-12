package com.app.dealspot.data.model

import androidx.compose.ui.graphics.vector.ImageVector

class ServiceCategoryEntity(
    val id: Long = 0,
    val name: String,
    val icon: ImageVector,
    val services: List<ServiceEntity>
)