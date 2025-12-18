package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class PaymentResponseRawDto(

    @field:SerializedName("transaction_id")
    val transactionId: String,

    @field:SerializedName("fraud_status")
    val fraudStatus: String,

    @field:SerializedName("status_message")
    val statusMessage: String,

    @field:SerializedName("transaction_status")
    val transactionStatus: String,

    @field:SerializedName("status_code")
    val statusCode: String,

    @field:SerializedName("payment_amounts")
    val paymentAmounts: List<String>,

    @field:SerializedName("signature_key")
    val signatureKey: String,

    @field:SerializedName("gross_amount")
    val grossAmount: String,

    @field:SerializedName("merchant_id")
    val merchantId: String,

    @field:SerializedName("va_numbers")
    val vaNumbers: List<VaNumbersItemDto>,

    @field:SerializedName("settlement_time")
    val settlementTime: String,

    @field:SerializedName("payment_type")
    val paymentType: String,

    @field:SerializedName("transaction_time")
    val transactionTime: String,

    @field:SerializedName("currency")
    val currency: String,

    @field:SerializedName("expiry_time")
    val expiryTime: String,

    @field:SerializedName("order_id")
    val orderId: String
)
