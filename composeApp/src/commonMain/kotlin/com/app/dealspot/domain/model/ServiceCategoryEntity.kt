package com.app.dealspot.domain.model

import org.jetbrains.compose.resources.DrawableResource

class ServiceCategoryEntity(
    val id: Long = 0,
    val name: String,
    val icon: DrawableResource,
    val services: List<ServiceEntity>
)