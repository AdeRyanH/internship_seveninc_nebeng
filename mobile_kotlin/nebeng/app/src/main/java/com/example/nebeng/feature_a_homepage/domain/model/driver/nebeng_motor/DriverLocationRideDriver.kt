package com.example.nebeng.feature_a_homepage.domain.model.driver.nebeng_motor

data class DriverLocationRideDriver(
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