//package com.example.nebeng.feature_passenger_ride_booking.domain.usecase
//
//import com.example.nebeng.feature_passenger_ride_booking.data.remote.model.request.UpdatePassengerRideBookingRequest
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
//class UpdatePassengerRideBookingUseCaseTest {
//
//    private lateinit var repository: PassengerRideBookingRepository
//    private lateinit var useCase: UpdatePassengerRideBookingUseCase
//
//    @Before
//    fun setup() {
//        repository = mock(PassengerRideBookingRepository::class.java)
//        useCase = UpdatePassengerRideBookingUseCase(repository)
//    }
//
//    @Test
//    fun `should update booking successfully`() = runTest {
//        val request = UpdatePassengerRideBookingRequest(15000, 2, "Diterima")
//        val updated = PassengerRideBooking(
//            id = 1,
//            bookingCode = "BK001",
//            passengerRideId = 10,
//            customerId = 5,
//            seatsReserved = 2,
//            totalPrice = 15000,
//            status = "Diterima",
//            createdAt = "",
//            updatedAt = ""
////            ,
////            null,
////            null,
////            null,
////            null
//        )
//        whenever(repository.updatePassengerRideBooking("token", 1, request)).thenReturn(flowOf(updated))
//        val result = useCase("token", 1, request).first()
//        assertEquals("Diterima", result.status)
//    }
//}