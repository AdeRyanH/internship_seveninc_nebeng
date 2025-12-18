package com.example.nebeng.feature_passenger_ride.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebeng.feature_passenger_ride.domain.usecase.PassengerRideUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.nebeng.core.common.Result
import com.example.nebeng.feature_passenger_ride.data.remote.model.request.CreatePassengerRideRequest
import com.example.nebeng.feature_passenger_ride.data.remote.model.request.UpdatePassengerRideRequest
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRide
import com.example.nebeng.core.utils.RideStatus
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PassengerRideViewModel @Inject constructor(
    private val useCases: PassengerRideUseCases
) : ViewModel() {

    // State untuk list semua rides
//    private val _rideListState = MutableStateFlow<Result<List<PassengerRide>>>(Result.Loading)
//    val rideListState: StateFlow<Result<List<PassengerRide>>> = _rideListState
//
//    // State untuk detail ride tunggal
//    private val _rideDetailState = MutableStateFlow<Result<PassengerRide>>(Result.Loading)
//    val rideDetailState: StateFlow<Result<PassengerRide>> = _rideDetailState
//
//    /**
//     * Ambil semua passenger rides
//     */
//    fun getAllPassengerRides(token: String) {
//        viewModelScope.launch {
//            _rideListState.value = Result.Loading
//            _rideListState.value = useCases.readAll(token)
//        }
//    }
//
//    /**
//     * Ambil detail passenger ride berdasarkan ID
//     */
//    fun getPassengerRideById(token: String, id: Int) {
//        viewModelScope.launch {
//            _rideDetailState.value = Result.Loading
//            _rideDetailState.value = useCases.readById(token, id)
//        }
//    }
//
//    /**
//     * Get rides by driver ID
//     */
//    fun getPassengerRidesByDriverId(token: String, driverId: Int) {
//        viewModelScope.launch {
//            _rideListState.value = Result.Loading
//            _rideListState.value = useCases.readByDriverId(token, driverId)
//        }
//    }
//
//    /**
//     * Get rides by Passenger Ride Status
//     */
//    fun getPassengerRideByStatus(token: String, status: RideStatus) {
//        viewModelScope.launch {
//            _rideListState.value = Result.Loading
//            _rideListState.value = useCases.readByStatus(token, status.value)
//        }
//    }
//
//    /**
//     * Post Passenger Ride
//     */
//    private val _createRideState = MutableStateFlow<Result<PassengerRide>>(Result.Loading)
//    val createRideState: StateFlow<Result<PassengerRide>> = _createRideState
//
//    fun createPassengerRide(token: String, request: CreatePassengerRideRequest) {
//        viewModelScope.launch {
//            _createRideState.value = Result.Loading
//            _createRideState.value = useCases.create(token, request)
//        }
//    }
//
//    /**
//     * Put Passenger Ride
//     */
//    private val _updateRideState = MutableStateFlow<Result<PassengerRide>>(Result.Loading)
//    val updateRideState: StateFlow<Result<PassengerRide>> = _updateRideState
//
//    fun updatePassengerRide(token: String, id: Int, request: UpdatePassengerRideRequest) {
//        viewModelScope.launch {
//            _updateRideState.value = Result.Loading
//            _updateRideState.value = useCases.update(token, id, request)
//        }
//    }
//
//    /**
//     * Delete Passenger Ride
//     */
//    private val _deleteRideState = MutableStateFlow<Result<String>>(Result.Loading)
//    val deleteRideState: StateFlow<Result<String>> = _deleteRideState
//
//    fun deletePassengerRide(token: String, id: Int) {
//        viewModelScope.launch {
//            _deleteRideState.value = Result.Loading
//            _deleteRideState.value = useCases.delete(token, id)
//        }
//    }
}
