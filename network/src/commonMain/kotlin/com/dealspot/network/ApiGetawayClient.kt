package com.dealspot.network

import com.dealspot.network.core_cognito.Engine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun apiGetawayClient(): HttpClient {
    val jsonBuilder = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    return HttpClient(Engine) {
        install(ContentNegotiation) {
            json(json = jsonBuilder)
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}