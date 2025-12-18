package com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2

import com.google.gson.annotations.SerializedName
import com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2.DataDto

data class UpdatePassengerTransactionResponse(

	@field:SerializedName("data")
	val data: DataDto
)

//data class VaNumbersItem(
//
//	@field:SerializedName("bank")
//	val bank: String,
//
//	@field:SerializedName("va_number")
//	val vaNumber: String
//)

//data class Data(
//
//	@field:SerializedName("transaction_date")
//	val transactionDate: String,
//
//	@field:SerializedName("transaction_code")
//	val transactionCode: String,
//
//	@field:SerializedName("midtrans_transaction_id")
//	val midtransTransactionId: String,
//
//	@field:SerializedName("payment_response_raw")
//	val paymentResponseRaw: PaymentResponseRaw,
//
//	@field:SerializedName("payment_status")
//	val paymentStatus: String,
//
//	@field:SerializedName("created_at")
//	val createdAt: String,
//
//	@field:SerializedName("payment_proof_img")
//	val paymentProofImg: String,
//
//	@field:SerializedName("credit_used")
//	val creditUsed: Int,
//
//	@field:SerializedName("payment_method_id")
//	val paymentMethodId: Int,
//
//	@field:SerializedName("payment_type")
//	val paymentType: String,
//
//	@field:SerializedName("updated_at")
//	val updatedAt: String,
//
//	@field:SerializedName("total_amount")
//	val totalAmount: Int,
//
//	@field:SerializedName("midtrans_order_id")
//	val midtransOrderId: String,
//
//	@field:SerializedName("payment_expired_at")
//	val paymentExpiredAt: String,
//
//	@field:SerializedName("passenger_ride_booking_id")
//	val passengerRideBookingId: Int,
//
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("va_number")
//	val vaNumber: String,
//
//	@field:SerializedName("customer_id")
//	val customerId: Int
//)

//data class PaymentResponseRaw(
//
//	@field:SerializedName("status_message")
//	val statusMessage: String,
//
//	@field:SerializedName("transaction_id")
//	val transactionId: String,
//
//	@field:SerializedName("fraud_status")
//	val fraudStatus: String,
//
//	@field:SerializedName("transaction_status")
//	val transactionStatus: String,
//
//	@field:SerializedName("status_code")
//	val statusCode: String,
//
//	@field:SerializedName("merchant_id")
//	val merchantId: String,
//
//	@field:SerializedName("gross_amount")
//	val grossAmount: String,
//
//	@field:SerializedName("va_numbers")
//	val vaNumbers: List<VaNumbersItem>,
//
//	@field:SerializedName("payment_type")
//	val paymentType: String,
//
//	@field:SerializedName("transaction_time")
//	val transactionTime: String,
//
//	@field:SerializedName("currency")
//	val currency: String,
//
//	@field:SerializedName("expiry_time")
//	val expiryTime: String,
//
//	@field:SerializedName("order_id")
//	val orderId: String
//)
