package com.app.dealspot.data.model


data class LoginResponse(
    val tokenResponse: TokenResponse? = null,
    val error: Error? = null
)