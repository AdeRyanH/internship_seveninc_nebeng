package com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor

import com.example.nebeng.feature_a_homepage.domain.model.driver.nebeng_motor.DriverLocationRideDriver

data class DriverRideUiState(
    val rideIdActive: Int? = null,
    val isSendingLocation: Boolean = false,
    val lastLocation: DriverLocationRideDriver? = null,
    val errorMessage: String? = null
)