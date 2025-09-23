package com.app.dealspot.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.business.AppDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val appDataStore: AppDataStore
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val isLoggedIn = appDataStore.getString(key = DataStoreKeys.IS_USER_LOGGED_IN) == "1"
            _isUserLoggedIn.value = isLoggedIn
        }
    }

    fun isUserLoggedIn(): Boolean {
        return _isUserLoggedIn.value
    }

    fun updateLoginStatus() {
        checkLoginStatus()
    }
}