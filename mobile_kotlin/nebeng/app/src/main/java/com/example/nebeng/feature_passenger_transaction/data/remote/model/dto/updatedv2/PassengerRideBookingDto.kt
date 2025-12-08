package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class PassengerRideBookingDto(

    @field:SerializedName("booking_code")
    val bookingCode: String,

    @field:SerializedName("passenger_ride_id")
    val passengerRideId: Int,

    @field:SerializedName("total_price")
    val totalPrice: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("reject_status")
    val rejectStatus: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("customer_id")
    val customerId: Int,

    @field:SerializedName("seats_reserved")
    val seatsReserved: Int,

    @field:SerializedName("status")
    val status: String
)
