package com.app.dealspot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateDealResponse(
    val success: Boolean = false,
    val message: String = "",
    val dealId: String? = null
)

