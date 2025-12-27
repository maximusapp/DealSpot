package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.DealType
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.data.model.CreateDealRequest
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.ServiceCategoryEntity
import com.app.dealspot.data.model.ServiceEntity
import com.app.dealspot.data.model.TokenResponse
import com.app.dealspot.domain.use_cases.deals.CreateDealUseCase
import com.app.dealspot.domain.use_cases.profile.GetUserUseCase
import com.app.dealspot.presentation.utils.booleanToInt
import com.app.dealspot.presentation.utils.getCurrentDateTime
import io.ktor.util.logging.Logger
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LookingForServiceViewModel(
    private val createDealUseCase: CreateDealUseCase,
    private val dataStore: AppDataStore,
    private val getUserUseCase: GetUserUseCase
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

    init {
        viewModelScope.launch {
            try {
                val tokenUserDate = dataStore.getString(key = DataStoreKeys.TOKEN_USER_DATA).orEmpty()
                val decodedUserTokenData = Json.decodeFromString<TokenResponse>(tokenUserDate)
                val userSub = dataStore.getString(key = DataStoreKeys.USER_SUB).orEmpty()

                println("LookingForServiceViewModel. userSub: $userSub")
                println("LookingForServiceViewModel. decodedUserTokenData: $decodedUserTokenData")

                if (userSub.isEmpty()) {
                    println("LookingForServiceViewModel. Need get user sub")

                    val getUserResponse = getUserUseCase.invoke(decodedUserTokenData.accessToken)
                    println("LookingForServiceViewModel. getUserResponse: $getUserResponse")

                    val attrMap: Map<String, String> = getUserResponse?.UserAttributes
                        ?.associate { it.Name to it.Value }           // or it.getName() / it.getValue()
                        ?: emptyMap()

                    val userSubResponse: String = attrMap["sub"].orEmpty()
                    println("LookingForServiceViewModel. userSub from response: $userSubResponse")

                    dataStore.putString(key = DataStoreKeys.USER_SUB, value = userSubResponse)
                }
            } catch (e: Exception) {
                println("Error occur while init user. Error: ${e.message}")
            }

        }
    }

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