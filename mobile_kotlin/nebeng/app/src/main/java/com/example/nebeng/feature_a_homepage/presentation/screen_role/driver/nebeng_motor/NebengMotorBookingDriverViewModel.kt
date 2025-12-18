package com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.nebeng_motor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebeng.feature_a_homepage.domain.interactor.driver.nebeng_motor.NebengMotorBookingDriverInteractor
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//@HiltViewModel
//class NebengMotorBookingDriverViewModel @Inject constructor(
//    private val interactor: NebengMotorBookingDriverInteractor
//) : ViewModel() {
//
//    private val _driverState = MutableStateFlow(DriverRideUiState())
//    val driverState: StateFlow<DriverRideUiState> = _driverState.asStateFlow()
//
//    private var sendLocJob: Job? = null
//
//    fun startRealtimeLocation(
//        token: String,
//        rideId: Int,
//        getLatLng: suspend () -> Pair<Double, Double>
//    ) {
//
//        Log.d(
//            "DRIVER_VM",
//            """
//            ▶️ startRealtimeLocation()
//            --------------------------
//            rideId = $rideId
//            jobActive = ${sendLocJob?.isActive}
//            --------------------------
//            """.trimIndent()
//        )
//
//        sendLocJob?.cancel()
//        sendLocJob = viewModelScope.launch {
//            interactor.startSendingDriverLocationLoop(
//                token           = token,
//                rideId          = rideId,
//                initialState    = _driverState.value,
//                getLatLng       = getLatLng,
//                onUpdate        = {
//                    Log.d(
//                        "DRIVER_VM",
//                        "📍 state update → lat=${it.lastLocation?.latitude}, lng=${it.lastLocation?.longitude}"
//                    )
//                    _driverState.value = it
//                }
//            )
//        }
//    }
//
//    fun stopRealtimeLocation() {
//        Log.d(
//            "DRIVER_VM",
//            "⏹ stopRealtimeLocation() → cancel job"
//        )
//
//        sendLocJob?.cancel()
//        _driverState.value = interactor.stopSending(_driverState.value)
//    }
//}

@HiltViewModel
class NebengMotorBookingDriverViewModel @Inject constructor(
    private val interactor: NebengMotorBookingDriverInteractor
) : ViewModel() {

    private val _driverState = MutableStateFlow(DriverRideUiState())
    val driverState: StateFlow<DriverRideUiState> = _driverState.asStateFlow()

    private var sendLocJob: Job? = null

    fun startRealtimeLocation(
        token: String,
        rideId: Int,
        getLatLng: suspend () -> Pair<Double, Double>
    ) {

        Log.d(
            "DRIVER_VM",
            """
            ▶️ startRealtimeLocation()
            --------------------------
            rideId = $rideId
            jobActive = ${sendLocJob?.isActive}
            --------------------------
            """.trimIndent()
        )

        sendLocJob?.cancel()
        sendLocJob = viewModelScope.launch {
            interactor.startSendingDriverLocationLoop(
                token           = token,
                rideId          = rideId,
                initialState    = _driverState.value,
                getLatLng       = getLatLng,
                onUpdate        = {
                    Log.d(
                        "DRIVER_VM",
                        "📍 state update → lat=${it.lastLocation?.latitude}, lng=${it.lastLocation?.longitude}"
                    )
                    _driverState.value = it
                }
            )
        }
    }

    fun stopRealtimeLocation() {
        Log.d(
            "DRIVER_VM",
            "⏹ stopRealtimeLocation() → cancel job"
        )

        sendLocJob?.cancel()
        _driverState.value = interactor.stopSending(_driverState.value)
    }
}