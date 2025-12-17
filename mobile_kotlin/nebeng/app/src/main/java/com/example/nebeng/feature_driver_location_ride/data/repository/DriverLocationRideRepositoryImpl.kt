package com.example.nebeng.feature_driver_location_ride.data.repository

import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver_location_ride.data.remote.api.DriverLocationRideApi
import com.example.nebeng.feature_driver_location_ride.data.remote.model.mapper.toSummary
import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import com.example.nebeng.feature_driver_location_ride.domain.model.DriverLocationRideSummary
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class DriverLocationRideRepositoryImpl @Inject constructor(
    private val api: DriverLocationRideApi
): DriverLocationRideRepository {
    override suspend fun getDriverLocationRideById(
        token: String,
        rideId: Int,
    ): Flow<Result<DriverLocationRideSummary>> = flow {
        emit(Result.Loading)

        try {
            val response = api.getDriverLocationRideById("Bearer $token", rideId)

            if (response.success && response.data != null) {
                emit(Result.Success(response.data.toSummary()))
            } else {
                emit(
                    value = Result.Error(
                        message = response.message ?: "Driver location not available"
                    )
                )
            }

        } catch (e: HttpException) {
            emit(value = Result.Error(message = parseBackendError(e)))
        } catch (e: IOException) {
            emit(value = Result.Error(message = "Network error. Please check your connection."))
        } catch (e: Exception) {
            emit(value = Result.Error(message = "Unexpected error occurred."))
        }
    }

    override suspend fun createByPassengerRideIdDriverLocationRide(
        token: String,
        rideId: Int,
        request: CreateByPassengerRideIdDriverLocationRideRequest,
    ): Flow<Result<DriverLocationRideSummary>> = flow {
        emit(Result.Loading)

        try {
            // ✅ BENAR: panggil POST
            val response = api.createByPassengerRideIdDriverLocationRide(
                token = "Bearer $token",
                rideId = rideId,
                request = request,
            )

            if (response.success && response.data != null) {
                emit(Result.Success(response.data.toSummary()))
            } else {
                emit(
                    Result.Error(
                        response.message ?: "Failed to update driver location"
                    )
                )
            }

        } catch (e: HttpException) {
            emit(Result.Error(parseBackendError(e)))
        } catch (e: IOException) {
            emit(Result.Error("Network error. Please check your connection."))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error occurred."))
        }
    }

    /**
     * PRIVATE HELPERS
     */
    private fun parseBackendError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val json = JSONObject(errorBody ?: "")
            json.optString("message", "Server error (${e.code()})")
        } catch (_: Exception) {
            "Server error (${e.code()})"
        }
    }
}