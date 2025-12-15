package com.example.nebeng.feature_driver_location_ride.data.repository

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_ride.data.remote.api.DriverLocationRideApi
import com.example.nebeng.feature_driver_location_ride.data.remote.model.mapper.toSummary
import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DriverLocationRideRepositoryImpl @Inject constructor(
    private val api: DriverLocationRideApi
): DriverLocationRideRepository {
    override suspend fun getDriverLocationRideById(
        token: String,
        rideId: Int,
    ): Flow<Result<DriverLocationRideSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getDriverLocationRideById("Bearer $token", rideId)
            val data = response.data.toSummary()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error while getDriverLocationRideById"))
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun createByPassengerRideIdDriverLocationRide(
        token: String,
        request: CreateByPassengerRideIdDriverLocationRideRequest,
        rideId: Int,
    ): Flow<Result<DriverLocationRideSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.createByPassengerRideIdDriverLocationRide("Bearer $token", request, rideId)
            val data = response.data.toSummary()
            emit(Result.Success(data))
        } catch(e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error while createByPassengerRideIdDriverLocationRide"))
        }
    }.flowOn(Dispatchers.IO)
}