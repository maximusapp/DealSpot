package com.app.dealspot.common

import kotlinx.coroutines.flow.MutableSharedFlow
import platform.Foundation.NSUserDefaults


actual suspend fun Context.putData(key: String, `object`: String) {
    val sharedFlow = MutableSharedFlow<String>()
    NSUserDefaults.standardUserDefaults().setObject(`object`, key)
    sharedFlow.emit(`object`)
}

actual suspend inline fun Context.getData(key: String): String? {
    return NSUserDefaults.standardUserDefaults().stringForKey(key)
}

actual suspend fun Context.putData(key: String, `object`: Boolean) {
    val sharedFlow = MutableSharedFlow<Boolean>()
    NSUserDefaults.standardUserDefaults().setObject(`object`, key)
    sharedFlow.emit(`object`)
}

actual suspend inline fun Context.getBooleanData(key: String): Boolean? {
    val isAppOpenedFirstTimeDefaults = NSUserDefaults.standardUserDefaults()
    return if (isAppOpenedFirstTimeDefaults.objectForKey(key) != null) {
        isAppOpenedFirstTimeDefaults.boolForKey(key)
    } else {
        true
    }
}