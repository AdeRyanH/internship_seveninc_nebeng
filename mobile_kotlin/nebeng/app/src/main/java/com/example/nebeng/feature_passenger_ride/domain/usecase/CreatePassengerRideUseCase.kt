package com.example.nebeng.feature_passenger_ride.domain.usecase

import com.example.nebeng.feature_passenger_ride.data.repository.PassengerRideRepository
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRide
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_passenger_ride.data.remote.model.request.CreatePassengerRideRequest
import kotlinx.coroutines.flow.Flow

class CreatePassengerRideUseCase @Inject constructor(
    private val repository: PassengerRideRepository
) {
    suspend operator fun invoke(token: String, request: CreatePassengerRideRequest): Flow<Result<PassengerRide>> {
        return repository.createPassengerRide(token, request)
    }
}