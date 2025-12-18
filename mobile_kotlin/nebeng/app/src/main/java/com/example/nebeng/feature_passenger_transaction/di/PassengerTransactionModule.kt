package com.example.nebeng.feature_passenger_transaction.di

import com.example.nebeng.feature_passenger_transaction.data.remote.api.PassengerTransactionApi
import com.example.nebeng.feature_passenger_transaction.data.remote.api.updated.PassengerTransactionUpdatedApi
import com.example.nebeng.feature_passenger_transaction.data.remote.api.updatedv2.PassengerTransactionUpdatedV2Api
import com.example.nebeng.feature_passenger_transaction.data.repository.PassengerTransactionRepository
import com.example.nebeng.feature_passenger_transaction.data.repository.PassengerTransactionRepositoryImpl
import com.example.nebeng.feature_passenger_transaction.data.repository.updated.PassengerTransactionUpdatedRepository
import com.example.nebeng.feature_passenger_transaction.data.repository.updated.PassengerTransactionUpdatedRepositoryImpl
import com.example.nebeng.feature_passenger_transaction.data.repository.updatedv2.PassengerTransactionUpdatedV2Repository
import com.example.nebeng.feature_passenger_transaction.data.repository.updatedv2.PassengerTransactionUpdatedV2RepositoryImpl
import com.example.nebeng.feature_passenger_transaction.domain.usecase.CreatePassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.DeletePassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.PassengerTransactionUseCases
import com.example.nebeng.feature_passenger_transaction.domain.usecase.PatchStatusByIdPassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.ReadAllPassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.ReadByIdPassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.ReadByPassengerRideBookingIdPassengerTransactionUseCase
import com.example.nebeng.feature_passenger_transaction.domain.usecase.UpdatePassengerTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassengerTransactionModule {
    @Provides
    @Singleton
    fun providePassengerTransactionUpdatedV2Api(
        retrofit: Retrofit
    ): PassengerTransactionUpdatedV2Api = retrofit.create(PassengerTransactionUpdatedV2Api::class.java)

    @Provides
    @Singleton
    fun providePassengerTransactionUpdatedV2Repository(
        api: PassengerTransactionUpdatedV2Api
    ): PassengerTransactionUpdatedV2Repository = PassengerTransactionUpdatedV2RepositoryImpl(api)

    @Provides
    @Singleton
    fun providePassengerTransactionUpdatedApi(
        retrofit: Retrofit
    ): PassengerTransactionUpdatedApi = retrofit.create(PassengerTransactionUpdatedApi::class.java)

    @Provides
    @Singleton
    fun providePassengerTransactionUpdatedRepository(
        api: PassengerTransactionUpdatedApi
    ): PassengerTransactionUpdatedRepository = PassengerTransactionUpdatedRepositoryImpl(api)


    @Provides
    @Singleton
    fun providePassengerTransactionApi(
       retrofit: Retrofit
    ): PassengerTransactionApi = retrofit.create(PassengerTransactionApi::class.java)

    @Provides
    @Singleton
    fun providePassengerTransactionRepository(
        api: PassengerTransactionApi
    ): PassengerTransactionRepository = PassengerTransactionRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePassengerTransactionUseCase(
        repository: PassengerTransactionRepository
    ): PassengerTransactionUseCases {
        return PassengerTransactionUseCases(
             create                         = CreatePassengerTransactionUseCase(repository),
             readAll                        = ReadAllPassengerTransactionUseCase(repository),
             readById                       = ReadByIdPassengerTransactionUseCase(repository),
             readByPassengerRideBookingId   = ReadByPassengerRideBookingIdPassengerTransactionUseCase(repository),
             update                         = UpdatePassengerTransactionUseCase(repository),
             patchStatus                    = PatchStatusByIdPassengerTransactionUseCase(repository),
             delete                         = DeletePassengerTransactionUseCase(repository)
        )
    }
}