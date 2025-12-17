package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor

import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PaymentMethodCustomer

data class NebengMotorBookingUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // list rides dengan pagination
    val rides: List<PassengerRideCustomer> = emptyList(),
    val isEndReached: Boolean = false,

    // payment method
    val paymentMethods: List<PaymentMethodCustomer> = emptyList(),

    // setelah aggregator
//    val aggregatedRideDetail: PassengerRideAggregator.Result? = null,

    // apakah transaksi sudah selesai
    val transactionSuccess: Boolean = false
)