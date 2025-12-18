package com.example.nebeng.feature_driver_location_good.domain.model

data class DriverLocationGoodSummary(
    val id: Int,
    val goodId: Int,
    val driverId: Int,
    val latitude: Double,
    val longitude: Double,
    val lastSeenAt: String,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)
