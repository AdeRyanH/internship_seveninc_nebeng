package com.example.nebeng.feature_passenger_ride.domain.usecase

import com.example.nebeng.feature_passenger_ride.data.repository.PassengerRideRepository
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRide
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import kotlinx.coroutines.flow.Flow

class ReadPassengerRideByDriverIdUseCase @Inject constructor(
    private val repository: PassengerRideRepository
) {
    suspend operator fun invoke(token: String, driverId: Int): Flow<Result<List<PassengerRide>>> {
        return repository.getPassengerRidesByDriverId(token, driverId)
    }
}