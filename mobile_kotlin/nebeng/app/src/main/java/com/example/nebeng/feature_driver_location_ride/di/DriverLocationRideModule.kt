package com.example.nebeng.feature_driver_location_ride.di

import com.example.nebeng.feature_driver_location_ride.data.remote.api.DriverLocationRideApi
import com.example.nebeng.feature_driver_location_ride.data.repository.DriverLocationRideRepository
import com.example.nebeng.feature_driver_location_ride.data.repository.DriverLocationRideRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DriverLocationRideModule {
    @Provides
    @Singleton
    fun provideDriverLocationRideApi(
        retrofit: Retrofit
    ): DriverLocationRideApi = retrofit.create(DriverLocationRideApi::class.java)

    @Provides
    @Singleton
    fun provideDriverLocationRideRepository(
        api: DriverLocationRideApi
    ): DriverLocationRideRepository = DriverLocationRideRepositoryImpl(api)
}