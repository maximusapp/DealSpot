package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.DealType
import com.app.dealspot.data.model.CreateDealRequest
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.ServiceCategoryEntity
import com.app.dealspot.data.model.ServiceEntity
import com.app.dealspot.domain.use_cases.deals.CreateDealUseCase
import com.app.dealspot.presentation.utils.booleanToInt
import com.app.dealspot.presentation.utils.getCurrentDateTime
import kotlinx.coroutines.launch

class LookingForServiceViewModel(
    private val createDealUseCase: CreateDealUseCase
) : ViewModel() {
    var problemName: String = ""
    private set
    var problemDescription: String = ""
    private set
    var selectedCategory: ServiceCategoryEntity? = null
    private set
    var selectedService: ServiceEntity? = null
    private set
    private var isUrgent: Boolean = false
    private var selectedLocation: LatLngEntity? = null

    fun setName(name: String) {
        problemName = name
    }

    fun setDescription(description: String) {
        problemDescription = description
    }

    fun setCategoryInfo(category: ServiceCategoryEntity, service: ServiceEntity) {
        selectedCategory = category
        selectedService = service
    }

    fun setUrgent(urgent: Boolean) {
        isUrgent = urgent
    }

    fun setLocation(location: LatLngEntity) {
        selectedLocation = location
    }

    fun getSelectedLocation(): LatLngEntity? {
        return selectedLocation
    }

    fun publishDeal() {
        viewModelScope.launch {
            val location = selectedLocation
            val category = selectedCategory
            val service = selectedService
            
            if (location == null || category == null || service == null) {
                println("DealRepositoryImpl. publishDeal. Missing required fields")
                return@launch
            }
            
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
                isActive = 1
            )
            
            println("DealRepositoryImpl. publishDeal. Sending request: $request")

            createDealUseCase.createDeal(request)
        }
    }
}