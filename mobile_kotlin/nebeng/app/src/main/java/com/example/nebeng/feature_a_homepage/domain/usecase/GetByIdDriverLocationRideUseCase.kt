package com.example.nebeng.feature_a_homepage.domain.usecase

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_ride.data.repository.DriverLocationRideRepository
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetByIdDriverLocationRideUseCase @Inject constructor(
    private val repository: DriverLocationRideRepository
) {
    suspend operator fun invoke(
        token: String,
        rideId: Int
    ): Flow<Result<DriverLocationRideSummary>> {
        return repository.getDriverLocationRideById(token, rideId)
    }
}