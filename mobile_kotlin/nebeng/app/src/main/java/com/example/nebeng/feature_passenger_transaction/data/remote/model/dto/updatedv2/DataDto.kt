package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class DataDto(

    @field:SerializedName("transaction_date")
    val transactionDate: String,

    @field:SerializedName("transaction_code")
    val transactionCode: String,

    @field:SerializedName("midtrans_transaction_id")
    val midtransTransactionId: String,

    @field:SerializedName("payment_response_raw")
    val paymentResponseRaw: PaymentResponseRawDto,

    @field:SerializedName("payment_status")
    val paymentStatus: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("payment_proof_img")
    val paymentProofImg: String,

    @field:SerializedName("credit_used")
    val creditUsed: Int,

    @field:SerializedName("passenger_ride_booking")
    val passengerRideBooking: PassengerRideBookingDto,

    @field:SerializedName("payment_method_id")
    val paymentMethodId: Int,

    @field:SerializedName("payment_type")
    val paymentType: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("total_amount")
    val totalAmount: Int,

    @field:SerializedName("midtrans_order_id")
    val midtransOrderId: String,

    @field:SerializedName("payment_expired_at")
    val paymentExpiredAt: String,

    @field:SerializedName("passenger_ride_booking_id")
    val passengerRideBookingId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("va_number")
    val vaNumber: String,

    @field:SerializedName("customer_id")
    val customerId: Int,

    @field:SerializedName("payment_method")
    val paymentMethod: PaymentMethodDto,

    @field:SerializedName("customer")
    val customer: CustomerDto
)
