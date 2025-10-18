package com.dealspot.network.core_cognito

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.DarwinLegacy

internal actual val Engine: HttpClientEngine = DarwinLegacy.create()

