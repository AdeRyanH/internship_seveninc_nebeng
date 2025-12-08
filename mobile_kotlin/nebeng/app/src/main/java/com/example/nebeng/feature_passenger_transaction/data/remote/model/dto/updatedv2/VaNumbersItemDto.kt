package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class VaNumbersItemDto(
    @field:SerializedName("bank")
    val bank: String,

    @field:SerializedName("va_number")
    val vaNumber: String
)
