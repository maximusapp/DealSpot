package com.app.dealspot.business

import com.app.dealspot.data.model.LatLngEntity
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingPage(
    val title: StringResource,
    val description: StringResource,
    val imageRes: DrawableResource
)

data class Step1(
    val avatarUri: String = "",
    val fullName: String = "",
    val age: String = "",
    val gender: GenderType? = null // male | female
) {
    val isValid: Boolean
        get() = fullName.isNotBlank() && age.toIntOrNull()?.let { it in 16..100 } == true && gender in setOf(GenderType.MALE, GenderType.FEMALE)
}

data class Step2(
    val email: String = "",
    val phone: String = ""
) {
    val isValid: Boolean
        get() = email.contains('@') && phone.length >= 7
}

data class Step3(
    val password: String = "",
    val confirmPassword: String = ""
) {
    val isValid: Boolean
        get() = password.length >= 8 && password == confirmPassword
}

data class CreateDealEntity(
    val id: Long,
    val categoryId: Long,
    val guid: String,
    val name: String,
    val description: String,
    val type: DealType,
    val location: LatLngEntity,
    val isUrgent: Int,
    val dateTime: String
)