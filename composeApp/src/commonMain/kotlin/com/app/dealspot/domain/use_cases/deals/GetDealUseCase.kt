package com.app.dealspot.domain.use_cases.deals

import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.domain.model.GetDealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetDealUseCase(
    private val dealRepository: DealRepositoryImpl
)  {
    suspend operator fun invoke(dealId: String): GetDealResponse {
        return withContext(Dispatchers.IO) {
            dealRepository.getDeal(dealId = dealId)
        }
    }
}