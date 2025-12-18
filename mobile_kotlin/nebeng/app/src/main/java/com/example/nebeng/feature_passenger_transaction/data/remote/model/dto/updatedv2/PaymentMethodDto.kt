package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class PaymentMethodDto(

    @field:SerializedName("account_number")
    val accountNumber: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("account_name")
    val accountName: String,

    @field:SerializedName("bank_name")
    val bankName: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int
)
