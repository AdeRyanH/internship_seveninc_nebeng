package com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor

import com.example.nebeng.core.utils.BookingStatus

data class PassengerRideBookingCustomer(
    // Tabel Passenger Ride Booking
    val idBooking: Int,
    val passengerRideId: Int,
    val customerId: Int,
    val createdAtBooking: String,
    val bookingCode: String?,
    val totalPrice: Int,
    val bookingStatus: BookingStatus,
    val seatsReservedBooking: Int
)