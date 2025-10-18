package com.app.dealspot.data.model

import com.dealspot.network.core_cognito.GetUserResponse

data class UserProfileResponse(
    val error: Throwable? = null,
    val data: GetUserResponse? = null
)