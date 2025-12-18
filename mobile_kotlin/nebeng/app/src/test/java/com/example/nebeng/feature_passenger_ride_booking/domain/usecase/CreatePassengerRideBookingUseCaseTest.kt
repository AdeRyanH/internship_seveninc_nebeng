//package com.example.nebeng.feature_passenger_ride_booking.domain.usecase
//
//import com.example.nebeng.feature_passenger_ride_booking.data.remote.model.request.CreatePassengerRideBookingRequest
//import com.example.nebeng.feature_passenger_ride_booking.data.repository.PassengerRideBookingRepository
//import com.example.nebeng.feature_passenger_ride_booking.domain.model.PassengerRideBooking
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito.mock
//import org.mockito.kotlin.whenever
//
//class CreatePassengerRideBookingUseCaseTest {
//    private lateinit var repository: PassengerRideBookingRepository
//    private lateinit var useCase: CreatePassengerRideBookingUseCase
//
//    @Before
//    fun setUp() {
//        repository = mock(PassengerRideBookingRepository::class.java)
//        useCase = CreatePassengerRideBookingUseCase(repository)
//    }
//
//    @Test
//    fun `should return created booking`() = runTest {
//        val request = CreatePassengerRideBookingRequest(
//            passengerRideId = 1,
//            totalPrice =  10000,
//            customerId =  2,
//            seatsReserved = 1,
//            status = "Pending"
//        )
//
//        val fakeBooking = PassengerRideBooking(
//            id =1,
//            bookingCode = "BK001",
//            passengerRideId = 1,
//            customerId = 2,
//            seatsReserved = 1,
//            totalPrice = 10000,
//            status = "Pending",
//            createdAt = "",
//            updatedAt = ""
////            ,
////            passengerRide = null,
////            passengerTransaction = null,
////            customer = null,
////            driver = null
//        )
//        whenever(repository.createPassengerRideBooking("token", request)).thenReturn(flowOf(fakeBooking))
//        val result = useCase("token", request).first()
//        assertEquals(fakeBooking, result)
//    }
//}
