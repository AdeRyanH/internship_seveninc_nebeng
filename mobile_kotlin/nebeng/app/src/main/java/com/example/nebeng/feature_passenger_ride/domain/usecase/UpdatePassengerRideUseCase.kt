package com.example.nebeng.feature_passenger_ride.domain.usecase

import com.example.nebeng.feature_passenger_ride.data.repository.PassengerRideRepository
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_passenger_ride.data.remote.model.request.UpdatePassengerRideRequest
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRide
import kotlinx.coroutines.flow.Flow

class UpdatePassengerRideUseCase @Inject constructor(
    private val repository: PassengerRideRepository
) {
    suspend operator fun invoke(token: String, id: Int, request: UpdatePassengerRideRequest): Flow<Result<PassengerRide>> {
        return repository.updatePassengerRide(token, id, request)
    }
}