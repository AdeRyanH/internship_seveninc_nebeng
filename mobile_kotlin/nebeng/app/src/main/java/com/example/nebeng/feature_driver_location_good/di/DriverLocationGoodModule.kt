package com.example.nebeng.feature_driver_location_good.di

import com.example.nebeng.feature_driver_location_good.data.remote.api.DriverLocationGoodApi
import com.example.nebeng.feature_driver_location_good.data.repository.DriverLocationGoodRepository
import com.example.nebeng.feature_driver_location_good.data.repository.DriverLocationGoodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DriverLocationGoodModule {
    @Provides
    @Singleton
    fun provideDriverLocationGoodApi(
        retrofit: Retrofit
    ): DriverLocationGoodApi = retrofit.create(DriverLocationGoodApi::class.java)

    @Provides
    @Singleton
    fun provideDriverLocationGoodRepository(
        api: DriverLocationGoodApi
    ): DriverLocationGoodRepository = DriverLocationGoodRepositoryImpl(api)
}