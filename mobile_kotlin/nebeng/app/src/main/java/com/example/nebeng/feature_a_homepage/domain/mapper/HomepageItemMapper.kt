package com.example.nebeng.feature_a_homepage.domain.mapper

import com.example.nebeng.core.utils.BookingStatus
import com.example.nebeng.core.utils.PaymentStatus
import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.CustomerCurrentCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.DriverCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerPricingCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideBookingCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerTransactionCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PaymentMethodCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalCustomer
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet.LocationUiModel
import com.example.nebeng.feature_customer.domain.model.CustomerSummary
import com.example.nebeng.feature_driver.domain.model.DriverSummary
import com.example.nebeng.feature_driver_location_good.domain.model.DriverLocationGoodSummary
import com.example.nebeng.feature_passenger_pricing.domain.model.PassengerPricingSummary
import com.example.nebeng.feature_passenger_ride.domain.model.PassengerRideSummary
import com.example.nebeng.feature_passenger_ride_booking.domain.model.feature_a_history_order.PassengerRideBookingSummary
import com.example.nebeng.feature_passenger_transaction.domain.model.updatedv2.PassengerTransaction
import com.example.nebeng.feature_payment_method.domain.model.PaymentMethodSummary
import com.example.nebeng.feature_terminal.domain.model.TerminalSummary

fun PassengerRideBookingSummary.toPassengerRideBookingCustomer(): PassengerRideBookingCustomer {
    return PassengerRideBookingCustomer(
        idBooking               = bookingId,
        passengerRideId         = ride.id,
        customerId              = customer.id,
        createdAtBooking        = createdAt,
        bookingCode             = bookingCode.orEmpty(),
        totalPrice              = transaction.totalAmount,
        bookingStatus           = BookingStatus.fromString(status),
        seatsReservedBooking    = ride.seatsReserved
    )
}

fun CustomerSummary.toCustomerCurrentCustomer(): CustomerCurrentCustomer {
    return CustomerCurrentCustomer(
        idCustomer          = id,
        customerName        = fullName,
        customerTelephone   = telephone
    )
}

fun PassengerRideSummary.toPassengerRideCustomer(): PassengerRideCustomer {
    return PassengerRideCustomer(
        idPassengerRide     = id,
        driverIdRide        = driverId,
        departureTerminalId = departureTerminalId,
        arrivalTerminalId   = arrivalTerminalId,
        rideStatus          = RideStatus.fromString(rideStatus),
        seatsReservedRide   = seatsReserved,
        seatsAvailableRide  = seatsAvailable,
        departureTime       = departureTime,
        pricePerSeat        = pricePerSeat.toString(),
        vehicleType         = VehicleType.fromString(vehicleType),
        driverId            = driverId
    )
}

fun PassengerTransaction.toPassengerTransactionCustomer(): PassengerTransactionCustomer{
    return PassengerTransactionCustomer(
        idPassengerTransaction  = id,
        transactionDate         = transactionDate,
        paymentStatus           = PaymentStatus.fromString(paymentStatus),
        transactionCode         = transactionCode,
        midtransTransactionId   = midtransTransactionId,
        createdAt               = createdAt,
        paymentProofImg         = paymentProofImg,
        creditUsed              = creditUsed,
        paymentMethodId         = paymentMethodId,
        paymentType             = paymentType,
        updatedAt               = updatedAt,
        totalAmount             = totalAmount,
        midtransOrderId         = midtransOrderId,
        paymentExpiredAt        = paymentExpiredAt,
        passengerRideBookingId  = passengerRideBookingId,
        vaNumber                = vaNumber,
        customerId              = customerId,
    )
}

fun PaymentMethodSummary.toPaymentMethoCustomer(): PaymentMethodCustomer {
    return PaymentMethodCustomer(
        idPaymentMethod     = id,
        bankName            = bankName,
        accountName         = accountName,
        accountNumber       = accountNumber
    )
}

fun TerminalSummary.toTerminalCustomer(): TerminalCustomer {
    return TerminalCustomer(
        id                      = id,
        name                    = name,
        terminalFullAddress     = fullAddress,
        terminalRegencyId       = regencyId,
        terminalLongitude       = longitude,
        terminalLatitude        = latitude,
        publicTerminalSubtype   = publicTerminalSubtype,
        terminalType            = terminalType,
        regencyName             = regencyName
    )
}

fun DriverSummary.toDriverCustomer(): DriverCustomer {
    return DriverCustomer(
        idDriver            = id,
        fullNameDriver      = fullName,
        telephoneDriver     = telephone,
        profileImgDriver    = profileImg.orEmpty()
    )
}

fun PassengerPricingSummary.toPassengerPricingCustomer(): PassengerPricingCustomer {
    return PassengerPricingCustomer(
        id                  = id,
        vehicleType         = vehicleType,
        departureTerminalId = departureTerminalId ,
        arrivalTerminalId   = arrivalTerminalId,
        pricePerSeat        = pricePerSeat,
        commissionPercentage= commissionPercentage,
        createdAt           = createdAt,
        updatedAt           = updatedAt
    )
}

fun TerminalCustomer.toLocationUi(): LocationUiModel {
    return LocationUiModel(
        id          = id,
        name        = name,
        fullAddress = terminalFullAddress,
        regency     = regencyName,
        rawTerminal = this
    )
}
//
//fun DriverLocationGoodSummary