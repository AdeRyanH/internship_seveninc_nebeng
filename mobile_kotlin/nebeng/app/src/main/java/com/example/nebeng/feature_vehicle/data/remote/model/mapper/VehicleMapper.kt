package com.example.nebeng.feature_vehicle.data.remote.model.mapper

import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_vehicle.data.remote.model.dto.DataDto
import com.example.nebeng.feature_vehicle.data.remote.model.dto.DataItemDto
import com.example.nebeng.feature_vehicle.domain.model.Vehicle
import com.example.nebeng.feature_vehicle.data.remote.model.response.DataReadById

// 🔹 Untuk Create/Update Response
fun DataDto.toDomain(): Vehicle =
    Vehicle(
        id = id,
        driverId = driverId,
        registrationNumber = registrationNumber,
        registrationYear = registrationYear.toString(),
        registrationExpired = registrationExpired,
        registrationImg = registrationImg,
        vehicleName = vehicleName,
        vehicleColor = vehicleColor,
        vehicleType = VehicleType.fromString(vehicleType),
        vehicleImg = vehicleImg,
        vehicleVerified = vehicleVerified,
        vehicleRejectedReason = vehicleRejectedReason
    )

// 🔹 Untuk ReadAll / ReadByDriver Response
fun DataItemDto.toDomain(): Vehicle =
    Vehicle(
        id = id,
        driverId = driver.id,
        registrationNumber = registrationNumber,
        registrationYear = registrationYear,
        registrationExpired = registrationExpired,
        registrationImg = registrationImg,
        vehicleName = vehicleName,
        vehicleColor = vehicleColor,
        vehicleType = VehicleType.fromString(vehicleType),
        vehicleImg = vehicleImg,
        vehicleVerified = vehicleVerified,
        vehicleRejectedReason = vehicleRejectedReason
    )

// 🔹 Untuk ReadById Response
fun DataReadById.toDomain(): Vehicle =
    Vehicle(
        id = id,
        driverId = driver.id,
        registrationNumber = registrationNumber,
        registrationYear = registrationYear,
        registrationExpired = registrationExpired,
        registrationImg = registrationImg,
        vehicleName = vehicleName,
        vehicleColor = vehicleColor,
        vehicleType = VehicleType.fromString(vehicleType),
        vehicleImg = vehicleImg,
        vehicleVerified = vehicleVerified,
        vehicleRejectedReason = vehicleRejectedReason
    )
