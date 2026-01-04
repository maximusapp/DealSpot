package com.app.dealspot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DealEntity(
    val dealId: String? = null,
    val type: Int? = null, // 0 for find_deal, 1 for provide_deal
    val name: String? = null,
    val description: String? = null,
    val categoryId: Long? = null,
    val categoryName: String? = null,
    val serviceId: Long? = null,
    val serviceName: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isUrgent: Int? = null,
    val dateTime: String? = null,
    val isActive: Int? = null,
    val userSub: String? = null
)

