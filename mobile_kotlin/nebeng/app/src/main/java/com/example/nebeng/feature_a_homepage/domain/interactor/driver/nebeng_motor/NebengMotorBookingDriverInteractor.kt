package com.example.nebeng.feature_a_homepage.domain.interactor.driver.nebeng_motor

import android.util.Log
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_a_homepage.domain.mapper.toDriverLocationRideDriver
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideUiState
import com.example.nebeng.feature_a_homepage.domain.usecase.NebengMotorUseCases
import com.example.nebeng.feature_driver_location_ride.data.remote.model.request.CreateByPassengerRideIdDriverLocationRideRequest
import kotlinx.coroutines.delay
import javax.inject.Inject

class NebengMotorBookingDriverInteractor @Inject constructor(
    val useCases: NebengMotorUseCases
) {
    /**
     * LOOP kirim lokasi driver (realtime-ish)
     * - dipanggil dari ViewModel dengan viewModelScope.launch { ... }
     * - berhenti otomatis kalau coroutine dibatalkan (screen keluar / stop trip)
     */
    suspend fun startSendingDriverLocationLoop(
        token: String,
        rideId: Int,
        initialState: DriverRideUiState,
        intervalMillis: Long = 5_000L,
        getLatLng: suspend () -> Pair<Double, Double>,
        onUpdate: (DriverRideUiState) -> Unit
    ) {
        var state = initialState.copy(
            rideIdActive = rideId,
            isSendingLocation = true,
            errorMessage = null
        )

        Log.d(
            "DRIVER_INTERACTOR",
            """
            🔁 START LOCATION LOOP
            ----------------------
            rideId = $rideId
            interval = ${intervalMillis}ms
            ----------------------
            """.trimIndent()
        )

        onUpdate(state)

        while (state.isSendingLocation) {
            try {
                val (lat, lng) = getLatLng()

                Log.d(
                    "DRIVER_INTERACTOR",
                    "📡 Send location → lat=$lat lng=$lng"
                )

                val request = CreateByPassengerRideIdDriverLocationRideRequest(
                    latitude = lat,
                    longitude = lng
                )

                useCases.createByPassengerRideIdDriverLocationRide(
                    token = token,
                    rideId = rideId,
                    request = request
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val location = result.data.toDriverLocationRideDriver()

                            Log.d(
                                "DRIVER_INTERACTOR",
                                "✅ Location saved id=${location.id}"
                            )

                            state = state.copy(lastLocation = location)
                            onUpdate(state)
                        }

                        is Result.Error -> {
                            Log.e(
                                "DRIVER_INTERACTOR",
                                "❌ API error = ${result.message}"
                            )

                            state = state.copy(errorMessage = result.message)
                            onUpdate(state)
                        }

                        else -> Unit
                    }
                }

                delay(intervalMillis)
            } catch (e: Exception) {
                Log.e(
                    "DRIVER_INTERACTOR",
                    "💥 Exception: ${e.message}",
                    e
                )

                state = state.copy(errorMessage = e.message)
                onUpdate(state)

                delay(intervalMillis)
            }

            delay(intervalMillis)
        }

        Log.d("DRIVER_INTERACTOR", "🛑 STOP LOCATION LOOP")
    }

    fun stopSending(state: DriverRideUiState): DriverRideUiState {
        Log.d("DRIVER_INTERACTOR", "⏹ stopSending()")
        return state.copy(isSendingLocation = false)
    }
}