package com.app.dealspot.data.model

import com.dealspot.network.core_cognito.IdentityProviderException


data class Error(
    var errorType: IdentityProviderException,
    var errorMsg: String
)