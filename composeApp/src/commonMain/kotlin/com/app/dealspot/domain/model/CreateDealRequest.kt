package com.app.dealspot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateDealRequest(
    val type: Int, // LOOKING_FOR_SERVICE, PROVIDE_SERVICE from DealType
    val name: String,
    val description: String,
    val categoryId: Long,
    val categoryName: String,
    val serviceId: Long,
    val serviceName: String,
    val latitude: Double,
    val longitude: Double,
    val isUrgent: Int,
    val dateTime: String,
    val isActive: Int,
    val userSub: String,
    val userName: String
)




