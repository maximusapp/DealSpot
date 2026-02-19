package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GetDealResponse(
    val deal: DealEntity? = null
)