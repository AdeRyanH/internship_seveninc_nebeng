package com.example.nebeng.feature_passenger_pricing.di

import com.example.nebeng.feature_passenger_pricing.data.remote.api.PassengerPricingApi
import com.example.nebeng.feature_passenger_pricing.data.repository.PassengerPricingRepository
import com.example.nebeng.feature_passenger_pricing.data.repository.PassengerPricingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassengerPricingModule {
    @Provides
    @Singleton
    fun providePassengerPricingApi(
        retrofit: Retrofit
    ): PassengerPricingApi = retrofit.create(PassengerPricingApi::class.java)

    @Provides
    @Singleton
    fun providePassengerPricingRepository(
        api: PassengerPricingApi
    ): PassengerPricingRepository = PassengerPricingRepositoryImpl(api)
}