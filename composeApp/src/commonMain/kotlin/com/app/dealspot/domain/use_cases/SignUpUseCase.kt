package com.app.dealspot.domain.use_cases

import com.app.dealspot.data.AuthRepositoryImpl
import com.app.dealspot.data.model.SignUpResponse

class SignUpUseCase(
    private val authRepositoryImpl: AuthRepositoryImpl
) {
    suspend fun invoke(name: String, email: String, password: String, age: String, phoneNumber: String, gender: Int): SignUpResponse {
        val response = authRepositoryImpl.signUp(
            name = name, email = email, password = password, age = age, phoneNumber = phoneNumber, gender = gender
        )
        println("SignUpUseCase. Response: $response")

        return response
    }
}