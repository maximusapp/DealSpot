package com.app.dealspot.data.model

import org.jetbrains.compose.resources.StringResource

data class SignUpResponse (
    var userSub: String? = null,
    var email: String? = null,
    var password: String? = null,
    var error: StringResource? = null
)