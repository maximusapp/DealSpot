package com.app.dealspot.presentation.ui.home.base

import androidx.lifecycle.ViewModel
import com.app.dealspot.domain.model.LatLngEntity
import com.app.dealspot.domain.model.ServiceCategoryEntity
import com.app.dealspot.domain.model.ServiceEntity

abstract class BaseServiceViewModel() : ViewModel() {
    var problemName: String = ""
        private set
    var problemDescription: String = ""
        private set
    var selectedCategory: ServiceCategoryEntity? = null
        private set
    var selectedService: ServiceEntity? = null
        private set

    var selectedLocation: LatLngEntity? = null
    private set


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

    fun setLocation(location: LatLngEntity) {
        selectedLocation = location
    }

}