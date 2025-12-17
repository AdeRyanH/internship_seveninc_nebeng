package com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor

data class PaymentMethodCustomer(
    // Tabel Payment Method
    val idPaymentMethod: Int,
    val bankName: String,
    val accountName:String,
    val accountNumber: String
)
