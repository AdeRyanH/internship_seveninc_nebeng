//package com.example.nebeng.feature_passenger_ride_booking.domain.usecase
//
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
//class ReadByPassengerRideIdPassengerRideBookingUseCaseTest {
//
//    private lateinit var repository: PassengerRideBookingRepository
//    private lateinit var useCase: ReadByPassengerRideIdPassengerRideBookingUseCase
//
//    @Before
//    fun setup() {
//        repository = mock(PassengerRideBookingRepository::class.java)
//        useCase = ReadByPassengerRideIdPassengerRideBookingUseCase(repository)
//    }
//
//    @Test
//    fun `should return bookings by ride id`() = runTest {
//        val bookings = listOf(
//            PassengerRideBooking(
//                id = 1,
//                bookingCode = "BK001",
//                passengerRideId = 10,
//                customerId = 5,
//                seatsReserved = 2,
//                totalPrice = 20000,
//                status = "Pending",
//                createdAt = "",
//                updatedAt = ""
////                ,
////                null,
////                null,
////                null,
////                null
//            )
//        )
//
//        whenever(repository.readByPassengerRideId("token", 10)).thenReturn(flowOf(bookings))
//        val result = useCase("token", 10).first()
//        assertEquals(1, result.size)
//        assertEquals(10, result.first().passengerRideId)
//    }
//}