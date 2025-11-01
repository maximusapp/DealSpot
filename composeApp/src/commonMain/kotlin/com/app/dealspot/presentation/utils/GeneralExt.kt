package com.app.dealspot.presentation.utils

inline fun runCatchingWithPrint(type: String, block: () -> Unit = {}) {
    return try {
        block()
    } catch (e: Exception) {
        print("Error occur when try: $type, Error: ${e.message}")
    }
}