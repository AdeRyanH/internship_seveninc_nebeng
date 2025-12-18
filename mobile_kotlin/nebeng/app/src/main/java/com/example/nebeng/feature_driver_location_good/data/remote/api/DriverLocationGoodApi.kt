package com.example.nebeng.feature_driver_location_good.data.remote.api

import com.example.nebeng.feature_driver_location_good.data.remote.model.request.CreateByGoodIdDriverLocationGoodRequest
import com.example.nebeng.feature_driver_location_good.data.remote.model.response.CreateByGoodRideIdDriverLocationGoodResponse
import com.example.nebeng.feature_driver_location_good.data.remote.model.response.ReadByIdDriverLocationGoodResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DriverLocationGoodApi {
    @GET("api/location-goods/{good_id}/location")
    suspend fun getDriverLocationGoodById(
        @Header("Authorization") token: String,
        @Path("good_id") goodId: Int
    ): ReadByIdDriverLocationGoodResponse

    // 🔹 5. Create Driver Withdrawal
    @POST("api/location-goods/{good_id}/location")
    suspend fun createDriverLocationGood(
        @Header("Authorization") token: String,
        @Body request: CreateByGoodIdDriverLocationGoodRequest,
        @Path("good_id") goodId: Int
    ): CreateByGoodRideIdDriverLocationGoodResponse
}