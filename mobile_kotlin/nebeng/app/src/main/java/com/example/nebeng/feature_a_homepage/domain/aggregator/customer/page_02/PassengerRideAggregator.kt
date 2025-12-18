package com.example.nebeng.feature_a_homepage.domain.aggregator.customer.page_02

import com.example.nebeng.core.common.Result
//import com.example.nebeng.feature_a_homepage.domain.mapper.toTerminalArrivalCustomer
//import com.example.nebeng.feature_a_homepage.domain.mapper.toTerminalDepartureCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
//import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalArrivalCustomer
//import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalDepartureCustomer

object PassengerRideAggregator {

//    fun combineRideWithTerminals(
//        ride: PassengerRideCustomer,
//        terminals: List<TerminalSummary>
//    ): Result<Pair<TerminalDepartureCustomer, TerminalArrivalCustomer>> {
//
//        if (terminals.isEmpty()) {
//            return Result.Error("❌ Daftar terminal kosong — tidak dapat melakukan mapping")
//        }
//
//        val departure = terminals.find { it.id == ride.departureTerminalId }
//        val arrival = terminals.find { it.id == ride.arrivalTerminalId }
//
//        // selalu null-safe
//        val departureMapped =
//            departure?.toTerminalDepartureCustomer() ?: TerminalDepartureCustomer.empty()
//
//        val arrivalMapped =
//            arrival?.toTerminalArrivalCustomer() ?: TerminalArrivalCustomer.empty()
//
//        return Result.Success(departureMapped to arrivalMapped)
//    }
    fun combineRideWithTerminals(
        ride: PassengerRideCustomer,
        terminals: List<TerminalCustomer>
    ): Result<Pair<TerminalCustomer, TerminalCustomer>> {

        if (terminals.isEmpty()) {
            return Result.Error("❌ Daftar terminal kosong — tidak dapat melakukan mapping")
        }

        val departure = terminals.find { it.id == ride.departureTerminalId } ?: TerminalCustomer.empty()
        val arrival   = terminals.find { it.id == ride.arrivalTerminalId } ?: TerminalCustomer.empty()

        return Result.Success(departure to arrival)
    }
}