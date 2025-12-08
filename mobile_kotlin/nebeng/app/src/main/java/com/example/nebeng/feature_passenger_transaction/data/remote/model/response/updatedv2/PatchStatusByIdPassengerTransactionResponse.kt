package com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2

import com.google.gson.annotations.SerializedName
import com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2.DataDto

data class PatchStatusByIdPassengerTransactionResponse(

	@field:SerializedName("data")
	val data: DataDto,

	@field:SerializedName("message")
	val message: String
)

//data class Data(
//
//	@field:SerializedName("transaction_date")
//	val transactionDate: String,
//
//	@field:SerializedName("transaction_code")
//	val transactionCode: String,
//
//	@field:SerializedName("midtrans_transaction_id")
//	val midtransTransactionId: Any,
//
//	@field:SerializedName("payment_response_raw")
//	val paymentResponseRaw: Any,
//
//	@field:SerializedName("payment_status")
//	val paymentStatus: String,
//
//	@field:SerializedName("created_at")
//	val createdAt: String,
//
//	@field:SerializedName("payment_proof_img")
//	val paymentProofImg: Any,
//
//	@field:SerializedName("credit_used")
//	val creditUsed: Int,
//
//	@field:SerializedName("payment_method_id")
//	val paymentMethodId: Int,
//
//	@field:SerializedName("payment_type")
//	val paymentType: Any,
//
//	@field:SerializedName("updated_at")
//	val updatedAt: String,
//
//	@field:SerializedName("total_amount")
//	val totalAmount: Int,
//
//	@field:SerializedName("midtrans_order_id")
//	val midtransOrderId: Any,
//
//	@field:SerializedName("payment_expired_at")
//	val paymentExpiredAt: Any,
//
//	@field:SerializedName("passenger_ride_booking_id")
//	val passengerRideBookingId: Int,
//
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("va_number")
//	val vaNumber: Any,
//
//	@field:SerializedName("customer_id")
//	val customerId: Int
//)
