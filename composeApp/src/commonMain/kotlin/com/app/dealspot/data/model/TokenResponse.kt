package com.app.dealspot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String = "",
    val expiresIn: Int = -1,
    val idToken: String = "",
    val refreshToken: String = "",
    val tokenType: String = "",
)