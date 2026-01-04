package com.app.dealspot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetDealsRequest(
    val type: Int // 0 for find_deal, 1 for provide_deal
)

