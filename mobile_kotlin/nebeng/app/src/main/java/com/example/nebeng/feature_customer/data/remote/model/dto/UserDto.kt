package com.example.nebeng.feature_customer.data.remote.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @field:SerializedName("user_type") val userType: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("created_at") val createdAt: String,
    @field:SerializedName("id") val id: Int,
//    @field:SerializedName("banned") val banned: Int,
    @field:SerializedName("banned") val banned: Boolean,
//    @field:SerializedName("banned") val banned: Any?,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("username") val username: String
)
