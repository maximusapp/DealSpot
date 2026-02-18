package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealRequest(
    val dealId: String,
    val dealType: Int, // 0 = Find deal, 1 = provide deal
    val requestType: Int, // 0 = SEND_REQUEST,  1 = CANCEL_REQUEST
    val userSub: String
)