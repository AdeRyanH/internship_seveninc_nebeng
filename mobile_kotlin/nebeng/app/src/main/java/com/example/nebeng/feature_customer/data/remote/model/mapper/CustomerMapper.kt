package com.example.nebeng.feature_customer.data.remote.model.mapper

import com.example.nebeng.core.utils.UserType
import com.example.nebeng.feature_customer.data.remote.model.dto.DataDto
import com.example.nebeng.feature_customer.data.remote.model.dto.DataItemDto
import com.example.nebeng.feature_customer.data.remote.model.dto.UserDto
import com.example.nebeng.feature_customer.domain.model.Customer
import com.example.nebeng.feature_customer.domain.model.CustomerSummary
import com.example.nebeng.feature_customer.domain.model.UserCustomerSummary
import com.example.nebeng.feature_user.domain.model.User

/* ============================================================
   🔹 Mapper untuk DataDto (digunakan oleh endpoint create/update/detail)
   ============================================================ */
fun DataDto.toDomain(): Customer {
    return Customer(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        verified = verified,
        faceImg = faceImg,
        faceWithIdImg = faceWithIdImg,
        idCardImg = idCardImg,
        idCardNumber = idCardNumber,
        idCardFullName = idCardFullname,
        idCardBirthdate = idCardBirthdate,
        creditAmount = creditAmount,
        createdAt = createdAt,
        updatedAt = updatedAt

//        user = null // biasanya DataDto tidak mengandung nested user
    )
}

/* ============================================================
   🔹 Mapper untuk DataItemDto (digunakan oleh endpoint list/read all/read by user id)
   ============================================================ */
fun DataItemDto.toDomain(): Customer {
    return Customer(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        verified = verified,
        faceImg = faceImg,
        faceWithIdImg = faceWithIdImg,
        idCardImg = idCardImg,
        idCardNumber = idCardNumber,
        idCardFullName = idCardFullname,
        idCardBirthdate = idCardBirthdate,
        creditAmount = creditAmount,
        createdAt = createdAt,
        updatedAt = updatedAt

//        user = user.toDomain() // nested object
    )
}

/* ============================================================
   🔹 Mapper untuk UserDto → User (relasi ke feature_user)
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

fun DataItemDto.toUserCustomerSummary(): UserCustomerSummary {
    return UserCustomerSummary(
        customerId = id,
        customerName = fullName
    )
}


fun DataDto.toSummary(): CustomerSummary{
    return CustomerSummary(
        id = id,
        userId = userId,
        fullName = fullName,
        telephone = telephone,
        fullAddress = fullAddress,
        profileImg = profileImg,
        verified = verified,
        faceImg = faceImg,
        faceWithIdImg = faceWithIdImg,
        idCardImg = idCardImg,
        idCardNumber = idCardNumber,
        idCardFullName = idCardFullname,
        idCardBirthdate = idCardBirthdate,
        creditAmount = creditAmount,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
