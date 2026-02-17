package com.app.dealspot.presentation.ui.home.search_provide_for_service.provide_service

import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.DealType
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.domain.model.CreateDealRequest
import com.app.dealspot.domain.use_cases.deals.CreateDealUseCase
import com.app.dealspot.presentation.ui.home.base.BaseServiceViewModel
import com.app.dealspot.presentation.utils.getCurrentDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProvideServiceViewModel(
    private val createDealUseCase: CreateDealUseCase,
    private val dataStore: AppDataStore
) : BaseServiceViewModel() {

    var specialization: String = ""
        private set

    var serviceDescription: String = ""
        private set
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    fun setSpecialization(spec: String) {
        println("ProvideServiceViewModel. Need setSpecialization: $spec")
        specialization = spec
    }

    fun setServiceDescription(description: String) {
        println("ProvideServiceViewModel. Need setServiceDescription: $description")
        serviceDescription = description
    }

    fun publishDeal() {
        viewModelScope.launch {
            val location = selectedLocation
            val category = selectedCategory
            val service = selectedService
            val userSub = dataStore.getString(key = DataStoreKeys.USER_SUB).orEmpty()
            val userName = dataStore.getString(key = DataStoreKeys.USER_NAME).orEmpty()


            if (location == null || category == null || service == null) {
                println("ProvideServiceViewModel. publishDeal. Missing required fields")
                return@launch
            }

            // Show bottom sheet immediately with loading state
            _showBottomSheet.value = true
            _isLoading.value = true
            
            try {
                val request = CreateDealRequest(
                    type = DealType.PROVIDE_SERVICE.ordinal,
                    name = specialization,
                    description = serviceDescription,
                    categoryId = category.id,
                    categoryName = category.name,
                    serviceId = service.id,
                    serviceName = service.name,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    isUrgent = 0,
                    dateTime = getCurrentDateTime(),
                    isActive = 1,
                    userSub = userSub,
                    userName = userName
                )

                println("ProvideServiceViewModel. publishDeal. Sending request: $request")

                val response = createDealUseCase.createDeal(request)
                
                if (response.success) {
                    // Show success state
                    _isLoading.value = false
                } else {
                    // Handle error if needed
                    println("ProvideServiceViewModel. publishDeal failed: ${response.message}")
                    _isLoading.value = false
                    // Could show error state here if needed
                }
            } catch (e: Exception) {
                println("ProvideServiceViewModel. publishDeal error: ${e.message}")
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