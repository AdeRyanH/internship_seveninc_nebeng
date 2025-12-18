package com.example.nebeng.feature_driver_location_ride.data.remote.model.mapper

import com.example.nebeng.feature_driver_location_ride.data.remote.model.dto.DataDto
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary

fun DataDto.toSummary(): DriverLocationRideSummary {
    return DriverLocationRideSummary(
        id = id,
        rideId = rideId,
        driverId = driverId,
        latitude = latitude,
        longitude = longitude,
        lastSeenAt = lastSeenAt,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}