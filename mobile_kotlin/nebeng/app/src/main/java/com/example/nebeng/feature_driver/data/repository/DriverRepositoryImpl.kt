package com.example.nebeng.feature_driver.data.repository

import android.util.Log
import com.example.nebeng.feature_driver.data.remote.api.DriverApi
import com.example.nebeng.feature_driver.data.remote.model.request.CreateDriverRequest
import com.example.nebeng.feature_driver.data.remote.model.request.UpdateDriverRequest
import com.example.nebeng.feature_driver.domain.model.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_driver.data.remote.model.mapper.toDomain
import com.example.nebeng.feature_driver.data.remote.model.mapper.toSummary
import com.example.nebeng.feature_driver.domain.model.DriverSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

@Singleton
class DriverRepositoryImpl @Inject constructor(
    private val api: DriverApi
): DriverRepository {
    override suspend fun getAllDrivers(token: String): Flow<Result<List<Driver>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getAllDrivers("Bearer $token")
            if (response.isSuccessful) {
                val data = response.body()?.dataItem?.map { it.toDomain() }.orEmpty()
                emit(Result.Success(data))
                Log.d("DriverRepo", "✅ getAllDrivers: ${data.size} driver(s) loaded")
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch all drivers"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching drivers"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDriverById(token: String, id: Int): Flow<Result<Driver>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getDriverById("Bearer $token", id)
            if (response.isSuccessful) {
                val data = response.body()?.data?.toDomain()
                if (data != null) {
                    emit(Result.Success(data))
                    Log.d("DriverRepo", "✅ getDriverById: ${data.fullName}")
                } else {
                    emit(Result.Error("Driver data not found"))
                }
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch driver by ID"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching driver by ID"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun createDriver(
        token: String,
        request: CreateDriverRequest
    ): Flow<Result<Driver>> = flow {
        emit(Result.Loading)
        try {
            val response = api.createDriver("Bearer $token", request)
            if (response.isSuccessful) {
                val data = response.body()?.data?.toDomain()
                if (data != null) {
                    emit(Result.Success(data))
                    Log.d("DriverRepo", "✅ createDriver: ${data.fullName}")
                } else {
                    emit(Result.Error("Driver creation response empty"))
                }
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to create driver"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while creating driver"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateDriver(
        token: String,
        id: Int,
        request: UpdateDriverRequest
    ): Flow<Result<Driver>> = flow {
        emit(Result.Loading)
        try {
            val response = api.updateDriver("Bearer $token", id, request)
            if (response.isSuccessful) {
                val data = response.body()?.data?.toDomain()
                if (data != null) {
                    emit(Result.Success(data))
                    Log.d("DriverRepo", "✅ updateDriver: ${data.fullName}")
                } else {
                    emit(Result.Error("Update driver response empty"))
                }
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to update driver"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while updating driver"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteDriver(token: String, id: Int): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        try {
            val response = api.deleteDriver("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Result.Success(true))
                Log.d("DriverRepo", "✅ deleteDriver: id=$id deleted")
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to delete driver"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while deleting driver"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDriverByIdSummary(token: String, id: Int): Flow<Result<DriverSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getDriverById("Bearer $token", id)
            if (response.isSuccessful) {
                val data = response.body()?.data?.toSummary()
                if (data != null) {
                    emit(Result.Success(data))
                    Log.d("DriverRepo", "✅ getDriverById: ${data.fullName}")
                } else {
                    emit(Result.Error("Driver data not found"))
                }
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch driver by ID"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching driver by ID"))
        }
    }.flowOn(Dispatchers.IO)
}