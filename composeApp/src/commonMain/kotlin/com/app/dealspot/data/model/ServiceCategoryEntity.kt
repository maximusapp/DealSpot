package com.app.dealspot.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource

class ServiceCategoryEntity(
    val id: Long = 0,
    val name: String,
    val icon: DrawableResource,
    val services: List<ServiceEntity>
)