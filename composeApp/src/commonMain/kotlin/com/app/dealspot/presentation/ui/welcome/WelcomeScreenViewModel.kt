package com.app.dealspot.presentation.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.constants.DataStoreKeys.IS_APP_FIRST_TIME_OPENED
import kotlinx.coroutines.launch

class WelcomeScreenViewModel(
    private val appDataStore: AppDataStore
): ViewModel() {

    suspend fun isAppFirstTimeOpened(): Boolean {
        val isFirstTimeOpened: String? = appDataStore.getString(key = IS_APP_FIRST_TIME_OPENED)

        return isFirstTimeOpened.isNullOrEmpty()
    }

    fun updateAppFirstTimeOpened() {
        viewModelScope.launch {
            appDataStore.putString(key = IS_APP_FIRST_TIME_OPENED, value = "1")
        }
    }
}