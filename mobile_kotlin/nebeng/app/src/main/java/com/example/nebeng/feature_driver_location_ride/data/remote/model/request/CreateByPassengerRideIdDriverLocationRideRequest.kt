package com.example.nebeng.feature_driver_location_ride.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class CreateByPassengerRideIdDriverLocationRideRequest(

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("longitude")
	val longitude: Double
)