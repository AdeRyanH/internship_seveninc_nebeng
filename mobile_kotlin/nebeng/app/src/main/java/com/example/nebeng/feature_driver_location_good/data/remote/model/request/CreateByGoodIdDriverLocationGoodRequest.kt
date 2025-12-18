package com.example.nebeng.feature_driver_location_good.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class CreateByGoodIdDriverLocationGoodRequest(

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("longitude")
	val longitude: Any
)