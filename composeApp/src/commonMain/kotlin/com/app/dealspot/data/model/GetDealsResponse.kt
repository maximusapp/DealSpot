package com.app.dealspot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetDealsResponse(
    val items: List<DealEntity> = emptyList(),
    val count: Int = 0
)

