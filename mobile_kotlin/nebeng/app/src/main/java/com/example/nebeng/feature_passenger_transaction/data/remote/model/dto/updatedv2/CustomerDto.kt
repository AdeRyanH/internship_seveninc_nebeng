package com.example.nebeng.feature_passenger_transaction.data.remote.model.dto.updatedv2

import com.google.gson.annotations.SerializedName

data class CustomerDto(

    @field:SerializedName("face_img")
    val faceImg: String,

    @field:SerializedName("id_card_fullname")
    val idCardFullname: String,

    @field:SerializedName("verified")
    val verified: Boolean,

    @field:SerializedName("credit_amount")
    val creditAmount: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("telephone")
    val telephone: String,

    @field:SerializedName("full_address")
    val fullAddress: String,

    @field:SerializedName("face_with_id_img")
    val faceWithIdImg: String,

    @field:SerializedName("profile_img")
    val profileImg: String,

    @field:SerializedName("id_card_img")
    val idCardImg: String,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("id_card_number")
    val idCardNumber: String,

    @field:SerializedName("id_card_birthdate")
    val idCardBirthdate: String
)
