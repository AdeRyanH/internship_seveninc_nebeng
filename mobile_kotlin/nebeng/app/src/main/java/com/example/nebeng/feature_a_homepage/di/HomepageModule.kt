package com.example.nebeng.feature_a_homepage.di

import com.example.nebeng.feature_a_homepage.domain.usecase.CreateByPassengerRideIdDriverLocationRideUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.CreatePassengerRideBookingUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.CreatePassengerTransactionUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetAllPassengerPricingUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetAllPassengerRideUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetAllPaymentMethodUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetAllTerminalUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdCustomerUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdDriverLocationRideUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdDriverUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdPassengerPricingUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdPassengerRideBookingUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdPassengerTransactionUseCase
import com.example.nebeng.feature_a_homepage.domain.usecase.GetByIdPaymentMethod
import com.example.nebeng.feature_a_homepage.domain.usecase.NebengMotorUseCases
import com.example.nebeng.feature_customer.data.repository.CustomerRepository
import com.example.nebeng.feature_driver.data.repository.DriverRepository
import com.example.nebeng.feature_driver_location_ride.data.repository.DriverLocationRideRepository
import com.example.nebeng.feature_passenger_pricing.data.repository.PassengerPricingRepository
import com.example.nebeng.feature_passenger_ride.data.repository.PassengerRideRepository
import com.example.nebeng.feature_passenger_ride_booking.data.repository.PassengerRideBookingRepository
import com.example.nebeng.feature_passenger_transaction.data.repository.updatedv2.PassengerTransactionUpdatedV2Repository
import com.example.nebeng.feature_payment_method.data.repository.PaymentMethodRepository
import com.example.nebeng.feature_terminal.data.repository.TerminalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomepageModule {
    @Provides
    @Singleton
    fun provideHomepageUseCases(
        passengerRideRepository: PassengerRideRepository,
        passengerRideBookingRepository: PassengerRideBookingRepository,
        paymentMethodRepository: PaymentMethodRepository,
        terminalRepository: TerminalRepository,
        customerRepository: CustomerRepository,
        driverRepository: DriverRepository,
        passengerTransactionUpdatedRepository: PassengerTransactionUpdatedV2Repository,
        passengerPricingRepository: PassengerPricingRepository,
        driverLocationRideRepository: DriverLocationRideRepository
    ): NebengMotorUseCases {
        return NebengMotorUseCases(
            /**
             * GET ALL
             */
            getAllPassengerRide         = GetAllPassengerRideUseCase(passengerRideRepository),
            getAllPaymentMethod         = GetAllPaymentMethodUseCase(paymentMethodRepository),
            getALlPassengerPricing      = GetAllPassengerPricingUseCase(passengerPricingRepository),
            getAllTerminal              = GetAllTerminalUseCase(terminalRepository),

            /**
             * GET BY ID
             */
            getByIdCustomer                 = GetByIdCustomerUseCase(customerRepository),
            getByIdDriver                   = GetByIdDriverUseCase(driverRepository),
            getByIdPaymentMethod            = GetByIdPaymentMethod(paymentMethodRepository),
            getByIdPassengerTransaction     = GetByIdPassengerTransactionUseCase(passengerTransactionUpdatedRepository),
            getByIdPassengerRideBooking     = GetByIdPassengerRideBookingUseCase(passengerRideBookingRepository),
            getByIdPassengerPricing         = GetByIdPassengerPricingUseCase(passengerPricingRepository),
            getByIdDriverLocationRideById   = GetByIdDriverLocationRideUseCase(driverLocationRideRepository),

            /**
             * CREATE
             */
            createPassengerRideBooking                  = CreatePassengerRideBookingUseCase(passengerRideBookingRepository),
            createPassengerTransaction                  = CreatePassengerTransactionUseCase(passengerTransactionUpdatedRepository),
            createByPassengerRideIdDriverLocationRide   = CreateByPassengerRideIdDriverLocationRideUseCase(driverLocationRideRepository)
        )
    }
}