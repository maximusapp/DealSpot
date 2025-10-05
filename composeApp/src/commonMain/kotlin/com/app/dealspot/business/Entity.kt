package com.app.dealspot.business

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingPage(
    val title: StringResource,
    val description: StringResource,
    val imageRes: DrawableResource
)

data class Step1(
    val avatarUri: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: String = "" // male | female
) {
    val isValid: Boolean
        get() = firstName.isNotBlank() && lastName.isNotBlank() && age.toIntOrNull()?.let { it in 1..120 } == true && gender in setOf("male", "female")
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
        get() = password.length >= 6 && password == confirmPassword
}