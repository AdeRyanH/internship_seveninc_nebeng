package com.example.nebeng.feature_driver_location_good.data.repository

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_good.data.remote.api.DriverLocationGoodApi
import com.example.nebeng.feature_driver_location_good.data.remote.model.mapper.toSummary
import com.example.nebeng.feature_driver_location_good.data.remote.model.request.CreateByGoodIdDriverLocationGoodRequest
import com.example.nebeng.feature_driver_location_good.domain.model.DriverLocationGoodSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DriverLocationGoodRepositoryImpl @Inject constructor(
    private val api: DriverLocationGoodApi
): DriverLocationGoodRepository {
    override suspend fun getDriverLocationGoodById(
        token: String,
        goodId: Int,
    ): Flow<Result<DriverLocationGoodSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getDriverLocationGoodById(token, goodId)
            val data = response.data.toSummary()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error while Get Driver Location Good By Id"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun createByGoodRideIdDriverLocationGood(
        token: String,
        request: CreateByGoodIdDriverLocationGoodRequest,
        goodId: Int,
    ): Flow<Result<DriverLocationGoodSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.createDriverLocationGood("Bearer $token", request, goodId)
            val data = response.data.toSummary()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error while Create By Passenger R"))
        }
    }
}