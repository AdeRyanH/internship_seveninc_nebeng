package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_06

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.PaymentUiMode
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_07.PassengerRideMotorPaymentWaitingScreen
import com.example.nebeng.feature_a_homepage.presentation.utils.calculateRemainingDuration

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun PassengerRideMotorPaymentStatusContainer(
    session: BookingSession,
    onBack: () -> Unit,
    onSwitchMode: (PaymentUiMode) -> Unit
) {
    val transaction = session.transaction ?: return
    val booking = session.booking ?: return

    val remainingTime = remember(transaction.paymentExpiredAt) {
        calculateRemainingDuration(transaction.paymentExpiredAt)
    }

    when (session.paymentUiMode) {
        PaymentUiMode.STATUS -> {
            PassengerRideMotorPaymentStatusScreen(
                transaction = transaction,
                booking = booking,
                ride = session.selectedRide,
                departure = session.selectedDepartureTerminal,
                arrival = session.selectedArrivalTerminal,
                remainingTime = remainingTime,
                onBack = onBack,
                onCheckStatus = { onSwitchMode(PaymentUiMode.WAITING) }
            )
        }

        PaymentUiMode.WAITING -> {
            PassengerRideMotorPaymentWaitingScreen(
                transaction = transaction,
                booking = booking,
                ride = session.selectedRide,
                departure = session.selectedDepartureTerminal,
                arrival = session.selectedArrivalTerminal,
                remainingTime = remainingTime,
                onBack = { onSwitchMode(PaymentUiMode.STATUS) }
            )
        }
    }
}
