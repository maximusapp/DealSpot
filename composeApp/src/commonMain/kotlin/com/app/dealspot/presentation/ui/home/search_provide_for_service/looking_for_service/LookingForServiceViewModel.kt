package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.ServiceCategoryEntity
import com.app.dealspot.data.model.ServiceEntity
import kotlinx.coroutines.launch

class LookingForServiceViewModel(

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

//    fun getSelectedCategory(): ServiceCategoryEntity? {
//        return selectedCategory
//    }
//
//    fun getSelectedService(): ServiceEntity? {
//        return selectedService
//    }


    fun publishDeal() {
        viewModelScope.launch {
            println("Deal name: $problemName")
            println("Deal descr: $problemDescription")
            println("Deal category: $selectedCategory")
            println("Deal service: $selectedService")
            println("Deal urgent: $isUrgent")
            println("Deal location: ${selectedLocation?.latitude}, ${selectedLocation?.longitude}")
        }
    }
}