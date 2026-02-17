package com.app.dealspot.domain.use_cases.deals

import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.domain.model.GetDealsRequest
import com.app.dealspot.domain.model.GetDealsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetDealsUseCase(
    private val dealRepository: DealRepositoryImpl
) {
    suspend operator fun invoke(type: Int): GetDealsResponse {
        return withContext(Dispatchers.IO) {
            dealRepository.getDeals(GetDealsRequest(type = type))
        }
    }
}

