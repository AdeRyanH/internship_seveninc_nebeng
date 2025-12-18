package com.example.nebeng.feature_passenger_transaction.data.remote.model.mapper.updatedv2

import com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2.DataDto
import com.example.nebeng.feature_passenger_transaction.domain.model.updatedv2.PassengerTransaction

fun DataDto.toPassengerTransactionUpdatedV2(): PassengerTransaction {
    return PassengerTransaction(
        id = id,
        transactionDate = transactionDate,
        transactionCode = transactionCode,
        midtransTransactionId = midtransTransactionId,
        paymentStatus = paymentStatus,
        createdAt = createdAt,
        paymentProofImg = paymentProofImg,
        creditUsed = creditUsed,
        paymentMethodId = paymentMethodId,
        paymentType = paymentType,
        updatedAt = updatedAt,
        totalAmount = totalAmount,
        midtransOrderId = midtransOrderId,
        paymentExpiredAt = paymentExpiredAt,
        passengerRideBookingId = passengerRideBookingId,
        vaNumber = vaNumber,
        customerId = customerId,
    )
}