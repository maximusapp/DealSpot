package com.app.dealspot.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.dealspot.business.APP_DATASTORE
import kotlinx.coroutines.flow.first


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_DATASTORE)

actual suspend fun Context.getData(key: String): String? {
    return dataStore.data.first()[stringPreferencesKey(key)] ?: ""
}

actual suspend fun Context.putData(key: String, `object`: String) {
    dataStore.edit {
        it[stringPreferencesKey(key)] = `object`
    }
}

actual suspend fun Context.getBooleanData(key: String): Boolean? {
    return dataStore.data.first()[booleanPreferencesKey(key)] ?: true
}

actual suspend fun Context.putData(key: String, `object`: Boolean) {
    dataStore.edit {
        it[booleanPreferencesKey(key)] = `object`
    }
}