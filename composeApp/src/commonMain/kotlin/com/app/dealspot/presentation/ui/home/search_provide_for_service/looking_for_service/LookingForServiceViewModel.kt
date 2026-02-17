package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.DealType
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.domain.model.CreateDealRequest
import com.app.dealspot.domain.use_cases.deals.CreateDealUseCase
import com.app.dealspot.domain.use_cases.profile.GetUserUseCase
import com.app.dealspot.presentation.ui.home.base.BaseServiceViewModel
import com.app.dealspot.presentation.utils.booleanToInt
import com.app.dealspot.presentation.utils.getCurrentDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LookingForServiceViewModel(
    private val createDealUseCase: CreateDealUseCase,
    private val dataStore: AppDataStore,
    private val getUserUseCase: GetUserUseCase
) : BaseServiceViewModel() {
    private var isUrgent: Boolean = false
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

//    init {
//        viewModelScope.launch {
//            try {
//                val tokenUserDate = dataStore.getString(key = DataStoreKeys.TOKEN_USER_DATA).orEmpty()
//                val decodedUserTokenData = Json.decodeFromString<TokenResponse>(tokenUserDate)
//                val userSub = dataStore.getString(key = DataStoreKeys.USER_SUB).orEmpty()
//
//                println("LookingForServiceViewModel. userSub: $userSub")
//                println("LookingForServiceViewModel. decodedUserTokenData: $decodedUserTokenData")
//
//                if (userSub.isEmpty()) {
//                    println("LookingForServiceViewModel. Need get user sub")
//
//                    val getUserResponse = getUserUseCase.invoke(decodedUserTokenData.accessToken)
//                    println("LookingForServiceViewModel. getUserResponse: $getUserResponse")
//
//                    val attrMap: Map<String, String> = getUserResponse?.UserAttributes
//                        ?.associate { it.Name to it.Value }
//                        ?: emptyMap()
//
//                    val userSubResponse: String = attrMap["sub"].orEmpty()
//                    println("LookingForServiceViewModel. userSub from response: $userSubResponse")
//
//                    dataStore.putString(key = DataStoreKeys.USER_SUB, value = userSubResponse)
//                }
//            } catch (e: Exception) {
//                println("Error occur while init user. Error: ${e.message}")
//            }
//
//        }
//    }

    fun setUrgent(urgent: Boolean) {
        isUrgent = urgent
    }

    fun publishDeal() {
        viewModelScope.launch {
            val location = selectedLocation
            val category = selectedCategory
            val service = selectedService
            val userSub = dataStore.getString(key = DataStoreKeys.USER_SUB).orEmpty()
            val userName = dataStore.getString(key = DataStoreKeys.REG_FULL_NAME).orEmpty()

            
            if (location == null || category == null || service == null) {
                println("LookingForServiceViewModel. publishDeal. Missing required fields")
                return@launch
            }
            
            // Show bottom sheet immediately with loading state
            _showBottomSheet.value = true
            _isLoading.value = true
            
            try {
                val request = CreateDealRequest(
                    type = DealType.LOOKING_FOR_SERVICE.ordinal,
                    name = problemName,
                    description = problemDescription,
                    categoryId = category.id,
                    categoryName = category.name,
                    serviceId = service.id,
                    serviceName = service.name,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    isUrgent = isUrgent.booleanToInt(),
                    dateTime = getCurrentDateTime(),
                    isActive = 1,
                    userSub = userSub,
                    userName = userName
                )
                
                println("LookingForServiceViewModel. publishDeal. Sending request: $request")

                val response = createDealUseCase.createDeal(request)
                
                if (response.success) {
                    // Show success state
                    _isLoading.value = false
                } else {
                    // Handle error if needed
                    println("LookingForServiceViewModel. publishDeal failed: ${response.message}")
                    _isLoading.value = false
                    // Could show error state here if needed
                }
            } catch (e: Exception) {
                println("LookingForServiceViewModel. publishDeal error: ${e.message}")
                e.printStackTrace()
                _isLoading.value = false
                // Could show error state here if needed
            }
        }
    }
    
    fun closeBottomSheet() {
        _showBottomSheet.value = false
        _isLoading.value = false
    }
}