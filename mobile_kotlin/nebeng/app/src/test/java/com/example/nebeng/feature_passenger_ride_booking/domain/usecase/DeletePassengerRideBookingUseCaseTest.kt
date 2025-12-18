//package com.example.nebeng.feature_passenger_ride_booking.domain.usecase
//
//import com.example.nebeng.feature_passenger_ride_booking.data.repository.PassengerRideBookingRepository
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.assertTrue
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito.mock
//import org.mockito.kotlin.whenever
//
//class DeletePassengerRideBookingUseCaseTest {
//
//    private lateinit var repository: PassengerRideBookingRepository
//    private lateinit var useCase: DeletePassengerRideBookingUseCase
//
//    @Before
//    fun setup() {
//        repository = mock(PassengerRideBookingRepository::class.java)
//        useCase = DeletePassengerRideBookingUseCase(repository)
//    }
//
//    @Test
//    fun `should return true when delete succeeds`() = runTest {
//        whenever(repository.deletePassengerRideBooking(
//            token = "token",
//            id = 1)
//        ).thenReturn(flowOf(true))
//        val result = useCase(token =
//            "token",
//            id = 1
//        ).first()
//        assertTrue(result)
//    }
//}
