package com.app.dealspot.data

import com.dealspot.network.AWSConfig.CLIENT_ID
import com.dealspot.network.AWSConfig.REGION
import com.dealspot.network.IdentityProviderClient
import com.dealspot.network.core_cognito.GetUserResponse
import com.dealspot.network.core_cognito.IdentityProviderException

class ProfileRepositoryImpl() {
    private val provider = IdentityProviderClient(REGION, CLIENT_ID)

    suspend fun getCurrentUser(accessToken: String): GetUserResponse? {
        var result: GetUserResponse? = null

        provider.getUser(accessToken = accessToken).fold(
            onSuccess = {
                println("getCurrentUser. Success: $it")
                result = it
            },
            onFailure = {
                println("getCurrentUser. Failure: ${it}")

                when(it) {
                    is IdentityProviderException.NotAuthorized -> {
                        println("getCurrentUser. Failure: Status: ${it.status}, Message: ${it.message}, Cause: ${it.cause}")
                    }
                }

                result
            }
        )

        return result
    }

    suspend fun getCurrentUserWithResult(accessToken: String): com.dealspot.network.core_cognito.Result<GetUserResponse> {
        return provider.getUser(accessToken = accessToken)
    }
}