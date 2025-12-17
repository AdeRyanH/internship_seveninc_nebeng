package com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor

import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.DriverLocationRideCustomer

data class DriverTrackingSession(
    val rideId: Int? = null,
    val isTracking: Boolean = false,
    val lastLocation: DriverLocationRideCustomer? = null,
    val errorMessage: String? = null
)
