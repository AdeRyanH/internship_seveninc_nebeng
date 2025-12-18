//package com.example.nebeng.feature_passenger_ride.domain.usecase
//
//import android.annotation.SuppressLint
//import com.example.nebeng.core.common.Result
//import com.example.nebeng.feature_passenger_ride.data.repository.PassengerRideRepository
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito.*
//
//@SuppressLint("CheckResult")
//@Suppress("UNCHECKED_CAST")
//private fun <T> anySafe(): T {
//    any<T>()
//    return null as T
//}
//
//class DeletePassengerRideUseCaseTest {
//    private lateinit var repository: PassengerRideRepository
//    private lateinit var useCase: DeletePassengerRideUseCase
//
//    @Before
//    fun setUp() {
//        repository = mock(PassengerRideRepository::class.java)
//        useCase = DeletePassengerRideUseCase(repository)
//    }
//
//    @Test
//    fun `should return success when repository deletes ride`() = runBlocking<Unit> {
//        `when`(repository.deletePassengerRide(anySafe(), anyInt()))
//            .thenReturn(Result.Success("Passenger ride deleted successfully"))
//
//        val result = useCase("dummy-token", 3)
//
//        assertTrue(result is Result.Success)
//        assertEquals("Passenger ride deleted successfully", (result as Result.Success).data)
//    }
//
//    @Test
//    fun `should return error when repository fails`() = runBlocking<Unit> {
//        `when`(repository.deletePassengerRide(anySafe(), anyInt()))
//            .thenReturn(Result.Error("Gagal menghapus passenger ride"))
//
//        val result = useCase("dummy-token", 3)
//
//        assertTrue(result is Result.Error)
//        assertEquals("Gagal menghapus passenger ride", (result as Result.Error).message)
//    }
//}