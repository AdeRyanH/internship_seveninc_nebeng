package com.example.nebeng.feature_driver_location_ride.data.remote.api


import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import com.example.nebeng.feature_driver_location_ride.data.remote.model.response.CreateByPassengerRideIdDriverLocationRideResponse
import com.example.nebeng.feature_driver_location_ride.data.remote.model.response.ReadByIdDriverLocationRideResponse
import retrofit2.http.*

interface DriverLocationRideApi {
    @GET("api/location-goods/{ride_id}/location")
    suspend fun getDriverLocationRideById(
        @Header("Authorization") token: String,
        @Path("ride_id") rideId: Int
    ): ReadByIdDriverLocationRideResponse

    @POST("api/location-goods/{ride_id}/location")
    suspend fun createByPassengerRideIdDriverLocationRide(
        @Header("Authorization") token: String,
        @Body request: CreateByPassengerRideIdDriverLocationRideRequest,
        @Path("ride_id") rideId: Int
    ): CreateByPassengerRideIdDriverLocationRideResponse
}