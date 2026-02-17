package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateDealResponse(
    val success: Boolean,
    val message: String,
    val dealId: String? = null
)

