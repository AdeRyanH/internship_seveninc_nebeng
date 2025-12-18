package com.example.nebeng.feature_passenger_ride.data.remote.model.mapper

import com.example.nebeng.feature_passenger_ride.data.remote.model.dto.PassengerRideDto
import com.example.nebeng.feature_passenger_ride.data.remote.model.response.DataItem
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRide
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRideSummary

// Untuk memetakan mana data field dari hasil response API yang diperlukan untuk keperluan apa yang ditampilkan ke UI ataupun bisa diextend untu menjaga integritas keamanan selama aplikasi dalam mode runtime
/**
 * Mapper untuk response GET (yang berisi nested object)
 */
//fun DataItem.toDomain(): PassengerRide {
//    return PassengerRide(
//        id = id,
//        driverName = driver.fullName,
//        vehicleType = vehicleType,
//        departureTerminalName = departureTerminal.name,
//        arrivalTerminalName = arrivalTerminal.name,
//        departureTime = departureTime,
//        seatsAvailable = seatsAvailable,
//        seatsReserved = seatsReserved,
//        pricePerSeat = pricePerSeat,
//        commissionPercentage = commissionPercentage,
//        rideStatus = rideStatus,
//        createdAt = createdAt,
//        updatedAt = updatedAt
//    )
//}
//fun DataItem.toDomain(): PassengerRide {
//    return PassengerRide(
//        id                      = id,
////        driverName              = driver?.fullName ?: "",
//        driverId                = 1,
//        vehicleType             = vehicleType,
////        departureTerminalName   = departureTerminal?.name ?: "",
////        arrivalTerminalName     = arrivalTerminal?.name ?: "",
//        departureTerminalId     = 1,
//        arrivalTerminalId       = 1,
//        departureTime           = departureTime,
//        seatsAvailable          = seatsAvailable,
//        seatsReserved           = seatsReserved,
//        pricePerSeat            = pricePerSeat,
//        commissionPercentage    = commissionPercentage,
//        rideStatus              = rideStatus,
//        createdAt               = createdAt,
//        updatedAt               = updatedAt
//    )
//}
//
///**
// * Mapper untuk response POST/PUT (yang pakai DTO sederhana tanpa nested object)
// */
//fun PassengerRideDto.toDomain(): PassengerRide {
//    return PassengerRide(
//        id                      = id,
////        driverName              = "", // kosong karena DTO tidak berisi driver nested
//        driverId                = 1,
//        vehicleType             = vehicleType,
////        departureTerminalName   = "", // kosong karena DTO tidak berisi terminal nested
////        arrivalTerminalName     = "",
//        departureTerminalId     = 1,
//        arrivalTerminalId       = 1,
//        departureTime           = departureTime,
//        seatsAvailable          = seatsAvailable,
//        seatsReserved           = seatsReserved,
//        pricePerSeat            = pricePerSeat,
//        commissionPercentage    = commissionPercentage,
//        rideStatus              = rideStatus,
//        createdAt               = createdAt,
//        updatedAt               = updatedAt
//    )
//}
//
///**
// * Helper function untuk GET list
// */
////fun List<DataItem>.toDomainList(): List<PassengerRide> =
////    map { it.toDomain() }
//
//fun DataItem.toSummary(): PassengerRideSummary {
//    return PassengerRideSummary(
//        id                      = id,
////        driverName              = driver?.fullName ?: "",
//        driverId                = 1,
//        vehicleType             = vehicleType,
////        departureTerminalName   = departureTerminal?.name ?: "",
////        arrivalTerminalName     = arrivalTerminal?.name ?: "",
//        departureTerminalId     = 1,
//        arrivalTerminalId       = 1,
//        departureTime           = departureTime,
//        seatsAvailable          = seatsAvailable,
//        seatsReserved           = seatsReserved,
//        pricePerSeat            = pricePerSeat,
//        commissionPercentage    = commissionPercentage,
//        rideStatus              = rideStatus,
//        createdAt               = createdAt,
//        updatedAt               = updatedAt
//    )
//}
//
//fun PassengerRideDto.toSummary(): PassengerRideSummary {
//    return PassengerRideSummary(
//        id                      = id,
//        driverId                = 1,
//        vehicleType             = vehicleType,
//        departureTerminalId     = 1,
//        arrivalTerminalId       = 1,
//        departureTime           = departureTime,
//        seatsAvailable          = seatsAvailable,
//        seatsReserved           = seatsReserved,
//        pricePerSeat            = pricePerSeat,
//        commissionPercentage    = commissionPercentage,
//        rideStatus              = rideStatus,
//        createdAt               = createdAt,
//        updatedAt               = updatedAt
//    )
//}

// ==========================================================
fun DataItem.toDomain(): PassengerRide {
    return PassengerRide(
        id                      = id,
//        driverName              = driver?.fullName ?: "",
        driverId                = driver.id,
        vehicleType             = vehicleType,
//        departureTerminalName   = departureTerminal?.name ?: "",
//        arrivalTerminalName     = arrivalTerminal?.name ?: "",
        departureTerminalId     = departureTerminal.id,
        arrivalTerminalId       = arrivalTerminal.id,
        departureTime           = departureTime,
        seatsAvailable          = seatsAvailable,
        seatsReserved           = seatsReserved,
        pricePerSeat            = pricePerSeat,
        commissionPercentage    = commissionPercentage,
        rideStatus              = rideStatus,
        createdAt               = createdAt,
        updatedAt               = updatedAt
    )
}

/**
 * Mapper untuk response POST/PUT (yang pakai DTO sederhana tanpa nested object)
 */
fun PassengerRideDto.toDomain(): PassengerRide {
    return PassengerRide(
        id                      = id,
//        driverName              = "", // kosong karena DTO tidak berisi driver nested
        driverId                = driverId,
        vehicleType             = vehicleType,
//        departureTerminalName   = "", // kosong karena DTO tidak berisi terminal nested
//        arrivalTerminalName     = "",
        departureTerminalId     = departureTerminalId,
        arrivalTerminalId       = arrivalTerminalId,
        departureTime           = departureTime,
        seatsAvailable          = seatsAvailable,
        seatsReserved           = seatsReserved,
        pricePerSeat            = pricePerSeat,
        commissionPercentage    = commissionPercentage,
        rideStatus              = rideStatus,
        createdAt               = createdAt,
        updatedAt               = updatedAt
    )
}

/**
 * Helper function untuk GET list
 */
//fun List<DataItem>.toDomainList(): List<PassengerRide> =
//    map { it.toDomain() }

fun DataItem.toSummary(): PassengerRideSummary {
    return PassengerRideSummary(
        id                      = id,
//        driverName              = driver?.fullName ?: "",
        driverId                = driver.id,
        vehicleType             = vehicleType,
//        departureTerminalName   = departureTerminal?.name ?: "",
//        arrivalTerminalName     = arrivalTerminal?.name ?: "",
        departureTerminalId     = departureTerminal.id,
        arrivalTerminalId       = arrivalTerminal.id,
        departureTime           = departureTime,
        seatsAvailable          = seatsAvailable,
        seatsReserved           = seatsReserved,
        pricePerSeat            = pricePerSeat,
        commissionPercentage    = commissionPercentage,
        rideStatus              = rideStatus,
        createdAt               = createdAt,
        updatedAt               = updatedAt
    )
}

fun PassengerRideDto.toSummary(): PassengerRideSummary {
    return PassengerRideSummary(
        id                      = id,
        driverId                = driverId,
        vehicleType             = vehicleType,
        departureTerminalId     = departureTerminalId,
        arrivalTerminalId       = arrivalTerminalId,
        departureTime           = departureTime,
        seatsAvailable          = seatsAvailable,
        seatsReserved           = seatsReserved,
        pricePerSeat            = pricePerSeat,
        commissionPercentage    = commissionPercentage,
        rideStatus              = rideStatus,
        createdAt               = createdAt,
        updatedAt               = updatedAt
    )
}
