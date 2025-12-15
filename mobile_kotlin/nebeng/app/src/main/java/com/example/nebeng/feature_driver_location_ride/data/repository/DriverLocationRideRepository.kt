package com.example.nebeng.feature_driver_location_ride.data.repository

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary
import kotlinx.coroutines.flow.Flow

interface DriverLocationRideRepository {
    suspend fun getDriverLocationRideById(
        token: String,
        rideId: Int
    ): Flow<Result<DriverLocationRideSummary>>

    suspend fun createByPassengerRideIdDriverLocationRide(
        token: String,
        request: CreateByPassengerRideIdDriverLocationRideRequest,
        rideId: Int
    ): Flow<Result<DriverLocationRideSummary>>
}