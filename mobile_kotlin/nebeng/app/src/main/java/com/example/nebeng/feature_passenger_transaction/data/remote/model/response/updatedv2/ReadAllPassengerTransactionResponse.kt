package com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2

import com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2.DataDto
import com.google.gson.annotations.SerializedName

data class ReadAllPassengerTransactionResponse(

	@field:SerializedName("data")
	val data: List<DataDto>
)

//data class PassengerRideBooking(
//
//	@field:SerializedName("booking_code")
//	val bookingCode: String,
//
//	@field:SerializedName("passenger_ride_id")
//	val passengerRideId: Int,
//
//	@field:SerializedName("total_price")
//	val totalPrice: Int,
//
//	@field:SerializedName("updated_at")
//	val updatedAt: String,
//
//	@field:SerializedName("reject_status")
//	val rejectStatus: Any,
//
//	@field:SerializedName("created_at")
//	val createdAt: String,
//
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("customer_id")
//	val customerId: Int,
//
//	@field:SerializedName("seats_reserved")
//	val seatsReserved: Int,
//
//	@field:SerializedName("status")
//	val status: String
//)

//data class PaymentResponseRaw(
//
//	@field:SerializedName("transaction_id")
//	val transactionId: String,
//
//	@field:SerializedName("fraud_status")
//	val fraudStatus: String,
//
//	@field:SerializedName("status_message")
//	val statusMessage: String,
//
//	@field:SerializedName("transaction_status")
//	val transactionStatus: String,
//
//	@field:SerializedName("status_code")
//	val statusCode: String,
//
//	@field:SerializedName("payment_amounts")
//	val paymentAmounts: List<Any>,
//
//	@field:SerializedName("signature_key")
//	val signatureKey: String,
//
//	@field:SerializedName("gross_amount")
//	val grossAmount: String,
//
//	@field:SerializedName("merchant_id")
//	val merchantId: String,
//
//	@field:SerializedName("va_numbers")
//	val vaNumbers: List<VaNumbersItem>,
//
//	@field:SerializedName("settlement_time")
//	val settlementTime: String,
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

//data class DataItem(
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
//	@field:SerializedName("passenger_ride_booking")
//	val passengerRideBooking: PassengerRideBooking,
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
//	val customerId: Int,
//
//	@field:SerializedName("payment_method")
//	val paymentMethod: PaymentMethod,
//
//	@field:SerializedName("customer")
//	val customer: Customer
//)

//data class PaymentMethod(
//
//	@field:SerializedName("account_number")
//	val accountNumber: String,
//
//	@field:SerializedName("updated_at")
//	val updatedAt: String,
//
//	@field:SerializedName("account_name")
//	val accountName: String,
//
//	@field:SerializedName("bank_name")
//	val bankName: String,
//
//	@field:SerializedName("created_at")
//	val createdAt: String,
//
//	@field:SerializedName("id")
//	val id: Int
//)

//data class Customer(
//
//	@field:SerializedName("face_img")
//	val faceImg: Any,
//
//	@field:SerializedName("id_card_fullname")
//	val idCardFullname: String,
//
//	@field:SerializedName("verified")
//	val verified: Boolean,
//
//	@field:SerializedName("credit_amount")
//	val creditAmount: Int,
//
//	@field:SerializedName("created_at")
//	val createdAt: String,
//
//	@field:SerializedName("telephone")
//	val telephone: String,
//
//	@field:SerializedName("full_address")
//	val fullAddress: String,
//
//	@field:SerializedName("face_with_id_img")
//	val faceWithIdImg: Any,
//
//	@field:SerializedName("profile_img")
//	val profileImg: Any,
//
//	@field:SerializedName("id_card_img")
//	val idCardImg: Any,
//
//	@field:SerializedName("full_name")
//	val fullName: String,
//
//	@field:SerializedName("updated_at")
//	val updatedAt: String,
//
//	@field:SerializedName("user_id")
//	val userId: Int,
//
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("id_card_number")
//	val idCardNumber: String,
//
//	@field:SerializedName("id_card_birthdate")
//	val idCardBirthdate: String
//)

//data class VaNumbersItem(
//
//	@field:SerializedName("bank")
//	val bank: String,
//
//	@field:SerializedName("va_number")
//	val vaNumber: String
//)
