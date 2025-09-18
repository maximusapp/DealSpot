package com.app.dealspot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform