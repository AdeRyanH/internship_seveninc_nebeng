package com.example.nebeng.feature_a_homepage.domain.usecase

data class NebengMotorUseCases(
    // GET ALL
    val getAllPassengerRide: GetAllPassengerRideUseCase,
    val getAllPaymentMethod: GetAllPaymentMethodUseCase,
    val getALlPassengerPricing: GetAllPassengerPricingUseCase,
    val getAllTerminal: GetAllTerminalUseCase,

    // GET BY ID
    val getByIdCustomer: GetByIdCustomerUseCase,
    val getByIdPassengerRideBooking: GetByIdPassengerRideBookingUseCase,
    val getByIdPassengerTransaction: GetByIdPassengerTransactionUseCase,
    val getByIdPaymentMethod: GetByIdPaymentMethod,
    val getByIdPassengerPricing: GetByIdPassengerPricingUseCase,
    val getByIdDriver: GetByIdDriverUseCase,
    val getByIdDriverLocationRideById: GetByIdDriverLocationRideUseCase,

    // CREATE
    val createPassengerRideBooking: CreatePassengerRideBookingUseCase,
    val createPassengerTransaction: CreatePassengerTransactionUseCase,
    val createByPassengerRideIdDriverLocationRide: CreateByPassengerRideIdDriverLocationRideUseCase,
)