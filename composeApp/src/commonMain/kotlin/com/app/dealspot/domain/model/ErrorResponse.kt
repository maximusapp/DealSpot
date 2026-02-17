package com.app.dealspot.domain.model

import com.dealspot.network.core_cognito.IdentityProviderException


data class Error(
    var errorType: IdentityProviderException,
    var errorMsg: String
)