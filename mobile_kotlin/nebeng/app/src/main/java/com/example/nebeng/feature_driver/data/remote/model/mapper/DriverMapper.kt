package com.example.nebeng.feature_driver.data.remote.model.mapper

import com.example.nebeng.core.utils.UserType
import com.example.nebeng.feature_driver.data.remote.model.dto.DataDto
import com.example.nebeng.feature_driver.data.remote.model.dto.DataItemDto
import com.example.nebeng.feature_driver.data.remote.model.dto.RatingsItemDto
import com.example.nebeng.feature_driver.data.remote.model.dto.UserDto
import com.example.nebeng.feature_driver.domain.model.Driver
import com.example.nebeng.feature_driver.domain.model.DriverSummary
import com.example.nebeng.feature_rating.domain.model.Rating
import com.example.nebeng.feature_user.domain.model.User
import kotlin.Int

/* ============================================================
   🔹 Mapper untuk DataDto (digunakan oleh endpoint detail/create/update)
   ============================================================ */
fun DataDto.toDomain(): Driver {
    return Driver(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        faceImg = faceImg,
        idCardImg = idCardImg,
        faceWithIdImg = faceWithIdImg,
        driverLicenseImg = driverLicenseImg,
        policeClearanceCertificateImg = policeClearanceCertificateImg,
        creditScore = creditScore,
        balance = balance,
        idCardVerified = idCardVerified,
        driverLicenseVerified = driverLicenseVerified,
        policeClearanceVerified = policeClearanceVerified,
        idCardNumber = idCardNumber,
        driverLicenseNumber = driverLicenseNumber,
        driverLicenseType = driverLicenseType,
        idCardBirthdate = idCardBirthdate,
        driverLicenseExpired = driverLicenseExpired,
        policeClearanceCertificateExpired = policeClearanceCertificateExpired,
        createdAt = createdAt,
        updatedAt = updatedAt,
        idCardFullname = idCardFullname,
        policeClearanceCertificateNumber = policeClearanceCertificateNumber,
        policeClearanceCertificateFullname = policeClearanceCertificateFullname,

        totalRating = null,
        averageRating = null,
        ratingCount = null

        // relasi kosong karena endpoint ini tidak kirim ratings/user
//        ratings = emptyList(),
//        user = null
    )
}

/* ============================================================
   🔹 Mapper untuk DataItemDto (digunakan oleh endpoint list)
   ============================================================ */
fun DataItemDto.toDomain(): Driver {
    return Driver(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        faceImg = faceImg,
        idCardImg = idCardImg,
        faceWithIdImg = faceWithIdImg,
        driverLicenseImg = driverLicenseImg,
        policeClearanceCertificateImg = policeClearanceCertificateImg,
        creditScore = creditScore,
        balance = balance,
        idCardVerified = idCardVerified,
        driverLicenseVerified = driverLicenseVerified,
        policeClearanceVerified = policeClearanceVerified,
        idCardNumber = idCardNumber,
        driverLicenseNumber = driverLicenseNumber,
        driverLicenseType = driverLicenseType,
        idCardBirthdate = idCardBirthdate,
        driverLicenseExpired = driverLicenseExpired,
        policeClearanceCertificateExpired = policeClearanceCertificateExpired,
        idCardFullname = idCardFullname,
        policeClearanceCertificateNumber = policeClearanceCertificateNumber,
        policeClearanceCertificateFullname = policeClearanceCertificateFullname,
        createdAt = createdAt,
        updatedAt = updatedAt,
        totalRating = totalRating,
        averageRating = averageRating,
        ratingCount = ratingCount

        // 🔹 map nested relasi
//        ratings = ratings.map { it.toDomain() },
//        user = user.toDomain()
    )
}

/* ============================================================
   🔹 Mapper untuk RatingsItemDto → DriverRating
   ============================================================ */
fun RatingsItemDto.toDomain(): Rating {
    return Rating(
        id = id,
        driverId = driverId,
        customerId =  customerId,
        rating = rating,
        feedback = feedback,
        createdAt = createdAt
    )
}

/* ============================================================
   🔹 Mapper untuk UserDto → DriverUser
   ============================================================ */
fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        userType = UserType.fromString(userType),
        banned = banned,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


//=================================================================================
fun DataItemDto.toSummary(): DriverSummary{
    return DriverSummary(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        faceImg = faceImg,
        idCardImg = idCardImg,
        faceWithIdImg = faceWithIdImg,
        driverLicenseImg = driverLicenseImg,
        policeClearanceCertificateImg = policeClearanceCertificateImg,
        creditScore = creditScore,
        balance = balance,
        idCardVerified = idCardVerified,
        driverLicenseVerified = driverLicenseVerified,
        policeClearanceVerified = policeClearanceVerified,
        idCardNumber = idCardNumber,
        driverLicenseNumber = driverLicenseNumber,
        driverLicenseType = driverLicenseType,
        idCardBirthdate = idCardBirthdate,
        driverLicenseExpired = driverLicenseExpired,
        policeClearanceCertificateExpired = policeClearanceCertificateExpired,
        idCardFullname = idCardFullname,
        policeClearanceCertificateNumber = policeClearanceCertificateNumber,
        policeClearanceCertificateFullname = policeClearanceCertificateFullname,
        createdAt = createdAt,
        updatedAt = updatedAt,
        totalRating = totalRating,
        averageRating = averageRating,
        ratingCount = ratingCount
    )
}


fun DataDto.toSummary(): DriverSummary{
    return DriverSummary(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        faceImg = faceImg,
        idCardImg = idCardImg,
        faceWithIdImg = faceWithIdImg,
        driverLicenseImg = driverLicenseImg,
        policeClearanceCertificateImg = policeClearanceCertificateImg,
        creditScore = creditScore,
        balance = balance,
        idCardVerified = idCardVerified,
        driverLicenseVerified = driverLicenseVerified,
        policeClearanceVerified = policeClearanceVerified,
        idCardNumber = idCardNumber,
        driverLicenseNumber = driverLicenseNumber,
        driverLicenseType = driverLicenseType,
        idCardBirthdate = idCardBirthdate,
        driverLicenseExpired = driverLicenseExpired,
        policeClearanceCertificateExpired = policeClearanceCertificateExpired,
        idCardFullname = idCardFullname,
        policeClearanceCertificateNumber = policeClearanceCertificateNumber,
        policeClearanceCertificateFullname = policeClearanceCertificateFullname,
        createdAt = createdAt,
        updatedAt = updatedAt,
        totalRating = 0,
        averageRating = 0f,
        ratingCount = 0
    )
}