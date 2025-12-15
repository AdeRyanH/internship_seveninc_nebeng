package com.example.nebeng.feature_driver_location_good.data.repository

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_good.data.remote.model.request.CreateByGoodIdDriverLocationGoodRequest
import com.example.nebeng.feature_driver_location_good.domain.model.DriverLocationGoodSummary
import kotlinx.coroutines.flow.Flow

interface DriverLocationGoodRepository {
    suspend fun getDriverLocationGoodById(
        token: String,
        goodId: Int
    ): Flow<Result<DriverLocationGoodSummary>>

    suspend fun createByGoodRideIdDriverLocationGood(
        token: String,
        request: CreateByGoodIdDriverLocationGoodRequest,
        goodId: Int
    ): Flow<Result<DriverLocationGoodSummary>>
}