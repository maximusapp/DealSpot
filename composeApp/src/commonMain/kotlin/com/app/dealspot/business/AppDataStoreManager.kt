package com.app.dealspot.business

import com.app.dealspot.common.Context
import com.app.dealspot.common.getBooleanData
import com.app.dealspot.common.getData
import com.app.dealspot.common.putData

const val APP_DATASTORE = "com.app.DealSpot"

class AppDataStoreManager(val context: Context) : AppDataStore {

    override suspend fun setValue(key: String, value: String) {
        context.putData(key, value)
    }

    override suspend fun readValue(
        key: String,
    ): String? {
        return context.getData(key)
    }

    override suspend fun readBooleanValue(key: String): Boolean? {
        return context.getBooleanData(key)
    }

    override suspend fun setValue(key: String, value: Boolean) {
        context.putData(key, value)
    }

    override suspend fun putString(key: String, value: String) {
        context.putData(key, value)
    }

    override suspend fun getString(key: String): String? {
        return context.getData(key)
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return context.getBooleanData(key)
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        context.putData(key, value)
    }
}