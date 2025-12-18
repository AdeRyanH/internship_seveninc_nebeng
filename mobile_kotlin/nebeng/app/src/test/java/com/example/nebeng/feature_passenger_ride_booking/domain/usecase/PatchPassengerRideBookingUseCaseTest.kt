//package com.example.nebeng.feature_passenger_ride_booking.domain.usecase
//
//import com.example.nebeng.feature_passenger_ride_booking.data.remote.model.request.PatchPassengerRideBookingRequest
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
//class PatchPassengerRideBookingUseCaseTest {
//
//    private lateinit var repository: PassengerRideBookingRepository
//    private lateinit var useCase: PatchPassengerRideBookingUseCase
//
//    @Before
//    fun setup() {
//        repository = mock(PassengerRideBookingRepository::class.java)
//        useCase = PatchPassengerRideBookingUseCase(repository)
//    }
//
//    @Test
//    fun `should patch status successfully`() = runTest {
//        val request = PatchPassengerRideBookingRequest(
//            status = "Diterima"
//        )
//
//        val patched = PassengerRideBooking(
//            id = 1,
//            bookingCode = "BK001",
//            passengerRideId = 10,
//            customerId = 5,
//            seatsReserved = 2,
//            totalPrice = 20000,
//            status = "Diterima",
//            createdAt = "",
//            updatedAt = ""
////            ,
////            passengerRide = null,
////            passengerTransaction = null,
////            customer = null,
////            driver = null
//        )
//
//        whenever(repository.patchPassengerRideBooking(
//            token = "token",
//            id = 1,
//            request)
//        ).thenReturn(flowOf(patched))
//
//        val result = useCase(
//            token = "token",
//            id = 1, request
//        ).first()
//
//        assertEquals("Diterima", result.status)
//    }
//}