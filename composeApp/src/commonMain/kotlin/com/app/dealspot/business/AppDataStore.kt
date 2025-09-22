package com.app.dealspot.business


interface AppDataStore {
    suspend fun setValue(key: String, value: String)
    suspend fun readValue(key: String): String?
    suspend fun readBooleanValue(key: String): Boolean?
    suspend fun setValue(key: String, value: Boolean)
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun getBoolean(key: String): Boolean?
    suspend fun putBoolean(key: String, value: Boolean)
}