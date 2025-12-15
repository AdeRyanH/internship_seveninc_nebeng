package com.example.nebeng.feature_driver_location_good.data.remote.model.mapper

import com.example.nebeng.feature_driver_location_good.data.remote.model.dto.DataDto
import com.example.nebeng.feature_driver_location_good.domain.model.DriverLocationGoodSummary

fun DataDto.toSummary(): DriverLocationGoodSummary {
    return DriverLocationGoodSummary(
        id = id,
        goodId = goodId,
        driverId = driverId,
        latitude = latitude,
        longitude = longitude,
        lastSeenAt = lastSeenAt,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
