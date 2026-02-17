package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealRequest(
    val dealId: String,
    val dealType: Int,
    val requestType: Int,
    val userSub: String
)