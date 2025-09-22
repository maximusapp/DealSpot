package com.app.dealspot.common

expect suspend fun Context.putData(key: String, `object`: String)
expect suspend fun Context.getData(key: String): String?
expect suspend fun Context.putData(key: String, `object`: Boolean)
expect suspend fun Context.getBooleanData(key: String): Boolean?