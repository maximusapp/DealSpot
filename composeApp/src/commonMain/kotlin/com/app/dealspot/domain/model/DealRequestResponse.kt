package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealRequestResponse(
    val success: Boolean = false,
    val requestType: Int = 0,
    val message: String = ""
)

