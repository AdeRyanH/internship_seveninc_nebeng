package com.example.nebeng.feature_a_homepage.domain.usecase

import com.example.nebeng.feature_driver_location_ride.data.repository.DriverLocationRideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary

class CreateByPassengerRideIdDriverLocationRideUseCase @Inject constructor(
    private val repository: DriverLocationRideRepository
) {
    suspend operator fun invoke(
        token: String,
        rideId: Int,
        request: CreateByPassengerRideIdDriverLocationRideRequest
    ): Flow<Result<DriverLocationRideSummary>> {
        return repository.createByPassengerRideIdDriverLocationRide(token, rideId, request)
    }
}