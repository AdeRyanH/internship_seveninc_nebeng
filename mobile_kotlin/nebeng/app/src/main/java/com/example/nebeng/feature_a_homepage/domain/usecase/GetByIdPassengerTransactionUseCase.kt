package com.example.nebeng.feature_a_homepage.domain.usecase

import android.util.Log
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_passenger_transaction.data.repository.updated.PassengerTransactionUpdatedRepository
import com.example.nebeng.feature_passenger_transaction.data.repository.updatedv2.PassengerTransactionUpdatedV2Repository
import com.example.nebeng.feature_passenger_transaction.domain.model.updatedv2.PassengerTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetByIdPassengerTransactionUseCase @Inject constructor(
    private val repository: PassengerTransactionUpdatedV2Repository
) {
    suspend operator fun invoke(token: String, id: Int): Flow<Result<PassengerTransaction>> {
//        return repository.getPassengerTransactionById(token, id)
        return repository.getPassengerTransactionById(token, id).onEach {
            if (it is Result.Success) {
                Log.d(
                    "PAGE 6 & 7 POOLING",
                    "API GET trxId=$id paymentStatus=${it.data.paymentStatus}"
                )
            }
        }
    }
}