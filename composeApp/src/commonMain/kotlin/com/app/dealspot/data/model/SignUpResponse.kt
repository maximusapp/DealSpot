package com.app.dealspot.data.model

data class SignUpResponse (
    var userSub: String? = null,
    var email: String? = null,
    var password: String? = null,
    var error: Error? = null
)