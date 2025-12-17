package com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor

import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.VehicleType

data class PassengerRideCustomer(
    // Tabel PassengerRide
    val idPassengerRide: Int,
    val driverId: Int,
    val departureTerminalId: Int,
    val arrivalTerminalId: Int,
    val rideStatus: RideStatus,
    val seatsReservedRide: Int,
    val seatsAvailableRide: Int,
    val departureTime: String,
    val pricePerSeat: String,
    val vehicleType: VehicleType,
    val driverIdRide: Int
)
