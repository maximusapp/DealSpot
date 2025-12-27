package com.app.dealspot.domain.use_cases.profile

import com.app.dealspot.data.ProfileRepositoryImpl
import com.dealspot.network.core_cognito.GetUserResponse

class GetUserUseCase(
    private val profileRepository: ProfileRepositoryImpl
) {

    suspend fun invoke(accessToken: String): GetUserResponse? {
        return profileRepository.getCurrentUser(accessToken = accessToken)
    }

}