package com.example.nebeng.feature_driver_location_ride.data.remote.model.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @field:SerializedName("ride_id")
    val rideId: Int,

    @field:SerializedName("driver_id")
    val driverId: Int,

    @field:SerializedName("is_active")
    val isActive: Boolean,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("last_seen_at")
    val lastSeenAt: String,

    @field:SerializedName("longitude")
    val longitude: Double
)