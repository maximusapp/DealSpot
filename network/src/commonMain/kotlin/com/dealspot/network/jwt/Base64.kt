package com.dealspot.network.jwt

internal expect class Base64 {
    companion object {
        fun decode(input: String): String?
    }
}
