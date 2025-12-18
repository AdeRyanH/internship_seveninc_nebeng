package com.example.nebeng.feature_driver_location_ride.domain.model

data class DriverLocationRideSummary(
    val id: Int,
    val rideId: Int,
    val driverId: Int,
    val latitude: Double,
    val longitude: Double,
    val lastSeenAt: String,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)
