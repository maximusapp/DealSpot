package com.app.dealspot.presentation.ui.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.data.ProfileRepositoryImpl
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.data.model.TokenResponse
import com.app.dealspot.domain.use_cases.LoginUseCase
import com.app.dealspot.domain.use_cases.deals.GetDealsUseCase
import com.app.dealspot.domain.use_cases.profile.GetUserUseCase
import com.app.dealspot.data.model.DealEntity
import com.app.dealspot.presentation.SharedViewModel
import com.app.dealspot.presentation.utils.getCurrentDateTime
import com.dealspot.network.core_cognito.GetUserResponse
import com.dealspot.network.core_cognito.IdentityProviderException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeScreenViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val dataStore: AppDataStore,
    private val profileRepository: ProfileRepositoryImpl,
    private val sharedViewModel: SharedViewModel,
    private val getDealsUseCase: GetDealsUseCase
) : ViewModel() {
    private val _cameraState = MutableStateFlow<MapCameraState?>(null)
    val cameraState: StateFlow<MapCameraState?> = _cameraState.asStateFlow()
    
    private val _goToCurrentLocationTrigger = MutableStateFlow(0)
    val goToCurrentLocationTrigger: StateFlow<Int> = _goToCurrentLocationTrigger.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Filter state: type 0 = find_deal, type 1 = provide_deal
    // Default to provide_deal (type 1)
    private val _filterType = MutableStateFlow(1)
    val filterType: StateFlow<Int> = _filterType.asStateFlow()
    
    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()
    
    private val _deals = MutableStateFlow<List<DealEntity>>(emptyList())
    val deals: StateFlow<List<DealEntity>> = _deals.asStateFlow()

    fun updateCamera(state: MapCameraState) {
        _cameraState.value = state
    }
    
    fun goToCurrentLocation() {
        _goToCurrentLocationTrigger.value++
    }

    fun resetCurrentLocationTrigger() {
        _goToCurrentLocationTrigger.value = 0
    }
    
    fun setFilterActive(isActive: Boolean) {
        _isFilterActive.value = isActive
        // Fetch deals when filter is applied
        if (isActive) {
            fetchDeals(_filterType.value)
        }
    }
    
    fun setFilterType(type: Int) {
        _filterType.value = type
        // Fetch deals when filter type changes
        fetchDeals(type)
    }
    
    fun resetFilter() {
        _isFilterActive.value = false
        // Reset to default (provide_deal = type 1)
        _filterType.value = 1
        fetchDeals(1)
    }
    
    fun fetchDeals(type: Int) {
        viewModelScope.launch {
            try {
                println("HomeScreenViewModel. fetchDeals. Type: $type")
                val response = getDealsUseCase(type)
                _deals.value = response.items
                println("HomeScreenViewModel. fetchDeals. Fetched ${response.count} deals")
            } catch (e: Exception) {
                println("HomeScreenViewModel. fetchDeals error: ${e.message}")
                e.printStackTrace()
                _deals.value = emptyList()
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun initializeUser() {
        println("HomeScreenViewModel. initializeUser()")
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                // First check if email or password is empty
                val email = dataStore.getString(key = DataStoreKeys.USER_EMAIL).orEmpty()
                val password = dataStore.getString(key = DataStoreKeys.USER_PASSWORD).orEmpty()
                
                if (email.isEmpty() || password.isEmpty()) {
                    println("HomeScreenViewModel. Email or password is empty. Clearing user data and logging out.")
                    // Clear TOKEN_USER_DATA and set IS_USER_LOGGED_IN to "0"
                    dataStore.putString(key = DataStoreKeys.TOKEN_USER_DATA, value = "")
                    dataStore.putString(key = DataStoreKeys.IS_USER_LOGGED_IN, value = "0")
                    dataStore.putString(key = DataStoreKeys.ACCESS_TOKEN_LAST_UPDATED, value = "")
                    // Update SharedViewModel login status to trigger navigation
                    sharedViewModel.updateLoginStatus()
                    _isLoading.value = false
                    return@launch
                }
                
                // Check if token was updated less than 24 hours ago
                val lastUpdatedStr = dataStore.getString(key = DataStoreKeys.ACCESS_TOKEN_LAST_UPDATED).orEmpty()
                if (lastUpdatedStr.isNotEmpty()) {
                    val hoursSinceUpdate = calculateHoursSinceUpdate(lastUpdatedStr)
                    if (hoursSinceUpdate < 24) {
                        println("HomeScreenViewModel. Token was updated ${hoursSinceUpdate}h ago, less than 24 hours. Skipping refresh.")
                        _isLoading.value = false
                        // Fetch deals even when token refresh is skipped
                        fetchDeals(1)
                        return@launch
                    } else {
                        println("HomeScreenViewModel. Token was updated ${hoursSinceUpdate}h ago, more than 24 hours. Need to refresh.")
                    }
                } else {
                    println("HomeScreenViewModel. No last updated timestamp found. Proceeding with refresh.")
                }
                
                // Get access token from DataStore
                val tokenUserData = dataStore.getString(key = DataStoreKeys.TOKEN_USER_DATA).orEmpty()
                if (tokenUserData.isEmpty()) {
                    _isLoading.value = false
                    return@launch
                }
                
                val decodedUserTokenData = Json.decodeFromString<TokenResponse>(tokenUserData)
                val accessToken = decodedUserTokenData.accessToken
                
                // Try to get user
                val getUserResult = profileRepository.getCurrentUserWithResult(accessToken)
                getUserResult.fold(
                    onSuccess = { getUserResponse ->
                        println("HomeScreenViewModel. getUserResponse: $getUserResponse")
                        // Update last updated timestamp
                        saveAccessTokenLastUpdated()
                        saveUserSub(getUserResponse)
                        _isLoading.value = false
                        // Fetch deals after user initialization (default type 1 = provide_deal)
                        fetchDeals(1)
                    },
                    onFailure = { exception ->
                        println("HomeScreenViewModel. getUser failed: $exception")
                        // Check if it's NotAuthorized exception with expired token message
                        if (exception is IdentityProviderException.NotAuthorized && 
                            exception.message?.contains("Access Token has expired", ignoreCase = true) == true) {
                            // Login user
                            loginAndGetUser()
                        } else {
                            _isLoading.value = false
                        }
                    }
                )
            } catch (e: Exception) {
                println("HomeScreenViewModel. initializeUser error: ${e.message}")
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
    
    @OptIn(ExperimentalTime::class)
    private fun calculateHoursSinceUpdate(lastUpdatedStr: String): Double {
        return try {
            val lastUpdatedLocal = parseMySQLDateTime(lastUpdatedStr)
            val timeZone = TimeZone.currentSystemDefault()
            val lastUpdatedInstant = lastUpdatedLocal.toInstant(timeZone)
            val nowInstant = Clock.System.now()
            
            val duration = nowInstant - lastUpdatedInstant
            // Convert duration to hours (duration.inWholeHours returns whole hours as Int)
            duration.inWholeHours.toDouble()
        } catch (e: Exception) {
            println("HomeScreenViewModel. Error parsing last updated time: ${e.message}")
            e.printStackTrace()
            // If parsing fails, return a large number to force refresh
            999.0
        }
    }
    
    @OptIn(ExperimentalTime::class)
    private fun parseMySQLDateTime(dateTimeStr: String): LocalDateTime {
        // Parse "yyyy-MM-dd HH:mm:ss" format
        val parts = dateTimeStr.split(" ")
        if (parts.size != 2) {
            throw IllegalArgumentException("Invalid date format: $dateTimeStr")
        }
        
        val dateParts = parts[0].split("-")
        val timeParts = parts[1].split(":")
        
        if (dateParts.size != 3 || timeParts.size != 3) {
            throw IllegalArgumentException("Invalid date format: $dateTimeStr")
        }
        
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt()
        val day = dateParts[2].toInt()
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        val second = timeParts[2].toInt()
        
        // Convert month number to kotlinx.datetime.Month
        val monthObj = when (month) {
            1 -> kotlinx.datetime.Month.JANUARY
            2 -> kotlinx.datetime.Month.FEBRUARY
            3 -> kotlinx.datetime.Month.MARCH
            4 -> kotlinx.datetime.Month.APRIL
            5 -> kotlinx.datetime.Month.MAY
            6 -> kotlinx.datetime.Month.JUNE
            7 -> kotlinx.datetime.Month.JULY
            8 -> kotlinx.datetime.Month.AUGUST
            9 -> kotlinx.datetime.Month.SEPTEMBER
            10 -> kotlinx.datetime.Month.OCTOBER
            11 -> kotlinx.datetime.Month.NOVEMBER
            12 -> kotlinx.datetime.Month.DECEMBER
            else -> throw IllegalArgumentException("Invalid month: $month")
        }
        
        return LocalDateTime(year, monthObj, day, hour, minute, second)
    }
    
    private fun saveAccessTokenLastUpdated() {
        viewModelScope.launch {
            val currentDateTime = getCurrentDateTime()
            dataStore.putString(key = DataStoreKeys.ACCESS_TOKEN_LAST_UPDATED, value = currentDateTime)
            println("HomeScreenViewModel. Saved access token last updated: $currentDateTime")
        }
    }

    private fun loginAndGetUser() {
        viewModelScope.launch {
            try {
                val email = dataStore.getString(key = DataStoreKeys.USER_EMAIL).orEmpty()
                val password = dataStore.getString(key = DataStoreKeys.USER_PASSWORD).orEmpty()
                
                if (email.isEmpty() || password.isEmpty()) {
                    println("HomeScreenViewModel. email or password is empty. Email: $email, password: $password")
                    _isLoading.value = false
                    return@launch
                }
                
                println("HomeScreenViewModel. Logging in user: $email")
                val loginState = loginUseCase.invoke(email = email, password = password)
                
                when (loginState) {
                    is com.app.dealspot.business.LoginState.Success -> {
                        println("HomeScreenViewModel. Login successful")
                        // Save user credentials to DataStore
                        saveUserCredentialsToDataStore(loginState.response.tokenResponse)
                        
                        // Get user again with new token
                        val tokenResponse = loginState.response.tokenResponse
                        if (tokenResponse != null) {
                            val getUserResult = profileRepository.getCurrentUserWithResult(tokenResponse.accessToken)
                            getUserResult.fold(
                                onSuccess = { getUserResponse ->
                                    println("HomeScreenViewModel. getUserResponse after login: $getUserResponse")
                                    // Update last updated timestamp
                                    saveAccessTokenLastUpdated()
                                    saveUserSub(getUserResponse)
                                    _isLoading.value = false
                                    // Fetch deals after user initialization (default type 1 = provide_deal)
                                    fetchDeals(1)
                                },
                                onFailure = { exception ->
                                    println("HomeScreenViewModel. getUser failed after login: $exception")
                                    _isLoading.value = false
                                }
                            )
                        } else {
                            _isLoading.value = false
                        }
                    }
                    else -> {
                        println("HomeScreenViewModel. Login failed: $loginState")
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                println("HomeScreenViewModel. loginAndGetUser error: ${e.message}")
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }

    private fun saveUserCredentialsToDataStore(tokenResponse: TokenResponse?) {
        println("HomeScreenViewModel. saveUserCredentialsToDataStore.")
        println("tokenResponse: $tokenResponse")
        viewModelScope.launch {
            val tokenResponseValue = Json.encodeToString(tokenResponse)
            println("tokenResponseValue: $tokenResponseValue")

            dataStore.putString(key = DataStoreKeys.TOKEN_USER_DATA, value = tokenResponseValue)
            dataStore.putString(key = DataStoreKeys.IS_USER_LOGGED_IN, value = "1")

            println("Stored user token data: ${dataStore.getString(key = DataStoreKeys.TOKEN_USER_DATA)}")
        }
    }

    private fun saveUserSub(getUserResponse: GetUserResponse) {
        viewModelScope.launch {
            val attrMap: Map<String, String> = getUserResponse.UserAttributes
                .associate { it.Name to it.Value }

            val userSubResponse: String = attrMap["sub"].orEmpty()
            println("HomeScreenViewModel. userSub from response: $userSubResponse")

            if (userSubResponse.isNotEmpty()) {
                dataStore.putString(key = DataStoreKeys.USER_SUB, value = userSubResponse)
            }
        }
    }

}