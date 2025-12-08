package com.example.nebeng.feature_passenger_transaction.data.repository.updatedv2

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_passenger_transaction.data.remote.model.request.*
import com.example.nebeng.feature_passenger_transaction.domain.model.updatedv2.PassengerTransaction
import kotlinx.coroutines.flow.Flow

interface PassengerTransactionUpdatedV2Repository {
    suspend fun getAllPassengerTransactions(token: String): Flow<Result<List<PassengerTransaction>>>

    suspend fun getPassengerTransactionById(token: String, id: Int): Flow<Result<PassengerTransaction>>

    suspend fun getPassengerTransactionByPassengerRideBookingId(
        token: String,
        passengerRideBookingId: Int
    ): Flow<Result<PassengerTransaction>>

    suspend fun createPassengerTransaction(
        token: String,
        request: CreatePassengerTransactionRequest
    ): Flow<Result<PassengerTransaction>>

    suspend fun updatePassengerTransactionById(
        token: String,
        id: Int,
        request: UpdatePassengerTransactionRequest
    ): Flow<Result<PassengerTransaction>>

    suspend fun patchStatusPassengerTransactionById(
        token: String,
        id: Int,
        request: PatchStatusByIdPassengerTransactionRequest
    ): Flow<Result<PassengerTransaction>>

    suspend fun deletePassengerTransactionById(token: String, id: Int): Flow<Result<String>>
}