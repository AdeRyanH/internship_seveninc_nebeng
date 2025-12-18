package com.example.nebeng.feature_passenger_transaction.data.remote.api.updatedv2

import com.example.nebeng.feature_passenger_transaction.data.remote.model.request.CreatePassengerTransactionRequest
import com.example.nebeng.feature_passenger_transaction.data.remote.model.request.PatchStatusByIdPassengerTransactionRequest
import com.example.nebeng.feature_passenger_transaction.data.remote.model.request.UpdatePassengerTransactionRequest
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.CreatePassengerTransactionUpdatedResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.DeletePassengerTransactionResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.PatchStatusByIdPassengerTransactionResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.ReadAllPassengerTransactionResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.ReadByIdPassengerTransactionResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.ReadByPassengerRideBookingIdPassengerTransactionResponse
import com.example.nebeng.feature_passenger_transaction.data.remote.model.response.updatedv2.UpdatePassengerTransactionResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PassengerTransactionUpdatedV2Api {
    /* ============================================================
     🔹 1. Get All Passenger Transactions
     ============================================================ */
    @GET("api/passenger-transactions")
    suspend fun getAllPassengerTransactions(
        @Header("Authorization") token: String
    ): ReadAllPassengerTransactionResponse


    /* ============================================================
       🔹 2. Get Passenger Transaction By Id
       ============================================================ */
    @GET("api/passenger-transactions/{id}")
    suspend fun getPassengerTransactionById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ReadByIdPassengerTransactionResponse


    /* ============================================================
       🔹 3. Get Passenger Transaction By Passenger Ride Booking Id
       ============================================================ */
    @GET("api/passenger-transactions/booking/{passenger_ride_booking_id}")
    suspend fun getPassengerTransactionByPassengerRideBookingId(
        @Header("Authorization") token: String,
        @Path("passenger_ride_booking_id") passengerRideBookingId: Int
    ): ReadByPassengerRideBookingIdPassengerTransactionResponse


    /* ============================================================
       🔹 4. Create Passenger Transaction
       ============================================================ */
    @POST("api/passenger-transactions")
    suspend fun createPassengerTransaction(
        @Header("Authorization") token: String,
        @Body request: CreatePassengerTransactionRequest
    ): CreatePassengerTransactionUpdatedResponse


    /* ============================================================
       🔹 5. Update Passenger Transaction By Id
       ============================================================ */
    @PUT("api/passenger-transactions/{id}")
    suspend fun updatePassengerTransactionById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: UpdatePassengerTransactionRequest
    ): UpdatePassengerTransactionResponse


    /* ============================================================
       🔹 6. Patch Status Passenger Transaction By Id
       ============================================================ */
    @PATCH("api/passenger-transactions/{id}/status")
    suspend fun patchStatusPassengerTransactionById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: PatchStatusByIdPassengerTransactionRequest
    ): PatchStatusByIdPassengerTransactionResponse


    /* ============================================================
       🔹 7. Delete Passenger Transaction By Id
       ============================================================ */
    @DELETE("api/passenger-transactions/{id}")
    suspend fun deletePassengerTransactionById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DeletePassengerTransactionResponse
}