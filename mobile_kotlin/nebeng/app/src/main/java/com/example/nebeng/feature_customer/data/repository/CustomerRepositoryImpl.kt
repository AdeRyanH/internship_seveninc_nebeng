package com.example.nebeng.feature_customer.data.repository

import android.util.Log
import com.example.nebeng.feature_customer.data.remote.api.CustomerApi
import com.example.nebeng.feature_customer.data.remote.model.request.CreateCustomerRequest
import com.example.nebeng.feature_customer.data.remote.model.request.PatchAddCreditAmountCustomerRequest
import com.example.nebeng.feature_customer.data.remote.model.request.PatchDeductCreditAmountCustomerRequest
import com.example.nebeng.feature_customer.data.remote.model.request.UpdateCustomerRequest
import com.example.nebeng.feature_customer.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_customer.data.remote.model.mapper.toDomain
import com.example.nebeng.feature_customer.data.remote.model.mapper.toSummary
import com.example.nebeng.feature_customer.data.remote.model.mapper.toUserCustomerSummary
import com.example.nebeng.feature_customer.domain.model.CustomerSummary
import com.example.nebeng.feature_customer.domain.model.UserCustomerSummary
//import com.example.nebeng.feature_passenger_ride_booking.data.remote.model.mapper.toFullDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class CustomerRepositoryImpl @Inject constructor(
    private val api: CustomerApi
): CustomerRepository {
    override suspend fun getAllCustomers(token: String): Flow<Result<List<Customer>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getAllCustomers("Bearer $token")
            if (response.isSuccessful) {
                val customers = response.body()?.data?.map { it.toDomain() }.orEmpty()
                emit(Result.Success(customers))
                Log.d("CustomerRepo", "✅ getAllCustomers: ${customers.size} customer(s) loaded")
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch customers"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching customers"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCustomerById(
        token: String,
        id: Int,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getCustomerById("Bearer $token", id)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ getCustomerById: ${customer.fullName}")
                } else emit(Result.Error("Customer not found"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch customer by ID"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching customer by ID"))
        }
    }.flowOn(Dispatchers.IO)

//    override suspend fun getCustomerByUserId(
//        token: String,
//        userId: Int,
//    ): Flow<Result<List<Customer>>> = flow {
//        emit(Result.Loading)
//        try {
//            val response = api.getCustomerByUserId("Bearer $token", userId)
//            if (response.isSuccessful) {
//                val customers = response.body()?.data?.map { it.toDomain() }.orEmpty()
//                emit(Result.Success(customers))
//                Log.d("CustomerRepo", "✅ getCustomerByUserId: ${customers.size} found for user=$userId")
//            } else {
//                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch by userId"))
//            }
//        } catch (e: Exception) {
//            emit(Result.Error(e.message ?: "Unknown error while fetching customer by userId"))
//        }
//    }.flowOn(Dispatchers.IO)
//    override suspend fun getCustomerByUserId(
//        token: String,
//        userId: Int
//    ): Flow<Result<Customer>> = flow {
//        emit(Result.Loading)
//        try {
//            val response = api.getCustomerByUserId("Bearer $token", userId)
//
//            if (response.isSuccessful) {
//                val dto = response.body()?.data?.toDomain()
//                if (dto != null) {
//                    emit(Result.Success(dto))
//                } else {
//                    emit(Result.Error("Customer null"))
//                }
//            } else {
//                emit(Result.Error(response.errorBody()?.string() ?: "Unknown error"))
//            }
//        } catch (e: Exception) {
//            emit(Result.Error(e.message ?: "Unknown exception"))
//        }
//    }.flowOn(Dispatchers.IO)
//    override suspend fun getCustomerByUserId(
//        token: String,
//        userId: Int
//    ): Flow<Result<Customer>> = flow {
//        emit(Result.Loading)
//        try {
//            val response = api.getCustomerByUserId("Bearer $token", userId)
//
//            if (response.isSuccessful) {
//                val list = response.body()?.data ?: emptyList()
//                val dto = list.firstOrNull()?.toDomain()
//
//                if (dto != null) emit(Result.Success(dto))
//                else emit(Result.Error("Customer not found"))
//            } else {
//                emit(Result.Error(response.errorBody()?.string() ?: "Unknown error"))
//            }
//        } catch (e: Exception) {
//            emit(Result.Error(e.message ?: "Unknown exception"))
//        }
//    }.flowOn(Dispatchers.IO)
    override suspend fun getCustomerByUserId(
        token: String,
        userId: Int,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getCustomerByUserId("Bearer $token", userId)

            if (!response.isSuccessful) {
                emit(Result.Error(response.errorBody()?.string() ?: "Unknown error"))
                return@flow
            }

            val dto = response.body()?.data?.toDomain()   // <-- langsung object, bukan array

            if (dto != null) emit(Result.Success(dto))
            else emit(Result.Error("Customer not found"))

        } catch (e: Exception) {
            Log.e("LOGIN_USECASE", "getCustomerByUserIdSummary ERROR: ${e.message}")
            emit(Result.Error(e.message ?: "Unknown exception"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun createCustomer(
        token: String,
        request: CreateCustomerRequest,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.createCustomer("Bearer $token", request)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ createCustomer: ${customer.fullName}")
                } else emit(Result.Error("Create response empty"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to create customer"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while creating customer"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateCustomer(
        token: String,
        id: Int,
        request: UpdateCustomerRequest,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.updateCustomer("Bearer $token", id, request)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ updateCustomer: ${customer.fullName}")
                } else emit(Result.Error("Update response empty"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to update customer"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while updating customer"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun patchVerifyTrue(
        token: String,
        id: Int,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.patchVerifyTrue("Bearer $token", id)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ patchVerifyTrue: id= $id verified")
                } else emit(Result.Error("Verify response empty"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to verify customer"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while verifying customer"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun patchAddCredit(
        token: String,
        id: Int,
        request: PatchAddCreditAmountCustomerRequest,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.patchAddCredit("Bearer $token", id, request)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ patchAddCredit: +${request.amount} credit to id=$id")
                } else emit(Result.Error("Add credit response empty"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to add credit"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while adding credit"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun patchDeductCredit(
        token: String,
        id: Int,
        request: PatchDeductCreditAmountCustomerRequest,
    ): Flow<Result<Customer>> = flow {
        emit(Result.Loading)
        try {
            val response = api.patchDeductCredit("Bearer $token", id, request)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toDomain()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ patchDeductCredit: -${request.amount} credit from id=$id")
                } else emit(Result.Error("Deduct credit response empty"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to deduct credit"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while deducting credit"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCustomer(
        token: String,
        id: Int,
    ): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        try {
            val response = api.deleteCustomer("Bearer $token", id)
            if (response.isSuccessful) {
                emit(Result.Success(true))
                Log.d("CustomerRepo", "✅ deleteCustomer: id=$id deleted")
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to delete customer"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while deleting customer"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getUserCustomerByUserIdSummary(
        token: String,
        userId: Int,
    ): Flow<Result<UserCustomerSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getCustomerByUserId("Bearer $token", userId)

            if (!response.isSuccessful) {
                emit(Result.Error(response.errorBody()?.string() ?: "Unknown error"))
                return@flow
            }

            val dto = response.body()?.data?.toUserCustomerSummary()   // <-- langsung object, bukan array

            if (dto != null) emit(Result.Success(dto))
            else emit(Result.Error("Customer not found"))

        } catch (e: Exception) {
            Log.e("LOGIN_USECASE", "getCustomerByUserIdSummary ERROR: ${e.message}")
            emit(Result.Error(e.message ?: "Unknown exception"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCustomerByIdSummary(
        token: String,
        id: Int,
    ): Flow<Result<CustomerSummary>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getCustomerById("Bearer $token", id)
            if (response.isSuccessful) {
                val customer = response.body()?.data?.toSummary()
                if (customer != null) {
                    emit(Result.Success(customer))
                    Log.d("CustomerRepo", "✅ getCustomerById: ${customer.fullName}")
                } else emit(Result.Error("Customer not found"))
            } else {
                emit(Result.Error(response.errorBody()?.string() ?: "Failed to fetch customer by ID"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error while fetching customer by ID"))
        }
    }.flowOn(Dispatchers.IO)
}