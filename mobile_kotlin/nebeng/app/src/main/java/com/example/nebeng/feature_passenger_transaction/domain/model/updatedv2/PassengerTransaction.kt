package com.example.nebeng.feature_passenger_transaction.domain.model.updatedv2

data class PassengerTransaction(
    val id: Int,
    val transactionDate: String,
    val transactionCode: String,
    val midtransTransactionId: String,
    val paymentStatus: String,
    val createdAt: String,
    val paymentProofImg: String?,
    val creditUsed: Int,
    val paymentMethodId: Int,
    val paymentType: String,
    val updatedAt: String,
    val totalAmount: Int,
    val midtransOrderId: String,
    val paymentExpiredAt: String,
    val passengerRideBookingId: Int,
    val vaNumber: String,
    val customerId: Int,
)
