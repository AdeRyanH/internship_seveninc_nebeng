package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_07

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.core.utils.BookingStatus
import com.example.nebeng.core.utils.PaymentStatus
import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideBookingCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerTransactionCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalCustomer
import java.time.Duration


//@RequiresApi(Build.VERSION_CODES.S)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PassengerRideMotorPaymentWaitingScreen(
//    transaction: PassengerTransactionCustomer,
//    booking: PassengerRideBookingCustomer,
//    ride: PassengerRideCustomer?,
//    departure: TerminalCustomer?,
//    arrival: TerminalCustomer?,
//    remainingTime: Duration,
//    onBack: () -> Unit
//) {
//    val hours   = remainingTime.toHours().toInt()
//    val minutes = remainingTime.toMinutesPart()
//    val seconds = remainingTime.toSecondsPart()
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        // ================= BACKGROUND BLUE (STATIC) =================
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//                .clip(RoundedCornerShape(bottomStart = 42.dp, bottomEnd = 42.dp))
//                .background(Color(0xFF0F3D82))
//        )
//
//        // ================= MAIN CONTENT (SCROLLABLE) =================
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//        ) {
//
//            // ---------- TOP APP BAR ----------
//            TopAppBar(
//                title = {
//                    Text(
//                        "Selesaikan Pembayaran",
//                        color = Color.White,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_arrow_left_24),
//                            contentDescription = null,
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Transparent
//                )
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // ---------- PAYMENT WAITING CARD ----------
////            WaitingPaymentCard(hours, minutes, seconds)
//            WaitingPaymentCard(
//                h = hours,
//                m = minutes,
//                s = seconds,
//                vaNumber = transaction.vaNumber,
//                totalAmount = booking.totalPrice
//            )
//
//            Spacer(Modifier.height(18.dp))
//
//            // ---------- RIDE INFO CARD ----------
////            RideInfoCard()
//            RideInfoCard(
//                ride = ride,
//                departure = departure,
//                arrival = arrival,
//                booking = booking
//            )
//
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorPaymentWaitingScreen(
    transaction: PassengerTransactionCustomer,
    booking: PassengerRideBookingCustomer,
    ride: PassengerRideCustomer?,
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?,
    remainingTime: Duration,
    onBack: () -> Unit
) {
    val hours   = remainingTime.toHours().toInt()
    val minutes = remainingTime.toMinutesPart()
    val seconds = remainingTime.toSecondsPart()

    Box(modifier = Modifier.fillMaxSize()) {

        // ================= BACKGROUND BLUE (STATIC) =================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 42.dp,
                        bottomEnd = 42.dp
                    )
                )
                .background(Color(0xFF0F3D82))
        )

        // ================= MAIN LAYER =================
        Column(modifier = Modifier.fillMaxSize()) {

            // ---------- TOP APP BAR (FIXED) ----------
            TopAppBar(
                title = {
                    Text(
                        "Selesaikan Pembayaran",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            // ---------- SCROLLABLE CONTENT ----------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 32.dp) // ✅ aman untuk device kecil
            ) {

                Spacer(Modifier.height(16.dp))

                WaitingPaymentCard(
                    h = hours,
                    m = minutes,
                    s = seconds,
                    vaNumber = transaction.vaNumber,
                    totalAmount = booking.totalPrice
                )

                Spacer(Modifier.height(18.dp))

                RideInfoCard(
                    ride = ride,
                    departure = departure,
                    arrival = arrival,
                    booking = booking
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun WaitingPaymentCard(
    h: Int,
    m: Int,
    s: Int,
    vaNumber: String,
    totalAmount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.padding(20.dp)) {

            Text("Menunggu Pembayaran", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(16.dp))
            TimerBox(h, m, s)
            Spacer(Modifier.height(16.dp))

            Text("Nomor Virtual Account", fontWeight = FontWeight.Medium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vaNumber,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF0F3D82)
                )
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = Color(0xFFE6E6E6), thickness = 1.dp)
            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Pembayaran", fontWeight = FontWeight.Medium)
                Text(
                    text = "Rp${totalAmount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF0F3D82)
                )
            }
        }
    }
}

@Composable
private fun TimerBox(h: Int, m: Int, s: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TimeBox(h)
        TimeSeparator()
        TimeBox(m)
        TimeSeparator()
        TimeBox(s)
    }
}

@Composable
private fun TimeBox(value: Int) {
    Box(
        modifier = Modifier
            .size(74.dp)
            .shadow(8.dp, RoundedCornerShape(22.dp))
            .background(Color(0xFF0F3D82), RoundedCornerShape(22.dp))
            .border(3.dp, Color.White, RoundedCornerShape(22.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            value.toString().padStart(2, '0'),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun TimeSeparator() {
    Column(
        modifier = Modifier.height(50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(8.dp).background(Color(0xFFD3D8DE), CircleShape))
        Box(Modifier.size(8.dp).background(Color(0xFFD3D8DE), CircleShape))
    }
}

@Composable
private fun RideInfoCard(
    ride: PassengerRideCustomer?,
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?,
    booking: PassengerRideBookingCustomer?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE1E3E6))
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(text = ride?.departureTime ?: "-", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(18.dp))

            RouteRow(
                departureName = departure?.name ?: "-",
                departureAddress = departure?.terminalFullAddress ?: "-",
                arrivalName = arrival?.name ?: "-",
                arrivalAddress = arrival?.terminalFullAddress ?: "-"
            )

            Spacer(Modifier.height(18.dp))
            Divider(color = Color(0xFFE6E6E6), thickness = 1.dp)
            Spacer(Modifier.height(10.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("No Pemesanan:", fontWeight = FontWeight.Medium)
                Text(booking?.bookingCode ?: "-", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun RouteRow(
    departureName: String,
    departureAddress: String,
    arrivalName: String,
    arrivalAddress: String
) {
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 4.dp)
                .width(38.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(16.dp).background(Color(0xFF2ECC71), RoundedCornerShape(50))
            )
            Box(
                modifier = Modifier.weight(1f).width(2.dp).background(Color(0xFFD0D4D8))
            )
            Box(
                modifier = Modifier.size(16.dp).background(Color(0xFFFF6E42), RoundedCornerShape(50))
            )
        }

        Spacer(Modifier.width(10.dp))

        Column {
            Text(departureName, fontWeight = FontWeight.Bold)
            Text(departureAddress, fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(10.dp))
            Text(arrivalName, fontWeight = FontWeight.Bold)
            Text(arrivalAddress, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPassengerRideMotorPaymentWaitingScreen() {

    val transaction = PassengerTransactionCustomer(
        idPassengerTransaction = 1,
        transactionDate = "2025-01-04T06:30:00Z",
        transactionCode = "TX-P-20250104-0001",
        midtransTransactionId = "mid-001",
        paymentStatus = PaymentStatus.PENDING,
        createdAt = "",
        paymentProofImg = null,
        creditUsed = 0,
        paymentMethodId = 2,
        paymentType = "bank_transfer",
        updatedAt = "",
        totalAmount = 28000,
        midtransOrderId = "ORD-001",
        paymentExpiredAt = "2025-01-05T06:30:00Z",
        passengerRideBookingId = 10,
        vaNumber = "178827357436699373",
        customerId = 1
    )

    val booking = PassengerRideBookingCustomer(
        idBooking = 10,
        passengerRideId = 1,
        customerId = 1,
        createdAtBooking = "2025-01-01",
        bookingCode = "P-20250104-0001",
        totalPrice = 28000,
        bookingStatus = BookingStatus.PENDING,
        seatsReservedBooking = 1
    )

    val ride = PassengerRideCustomer(
        idPassengerRide = 1,
        driverId = 1,
        departureTerminalId = 1,
        arrivalTerminalId = 2,
        rideStatus = RideStatus.PENDING,
        seatsReservedRide = 1,
        seatsAvailableRide = 3,
        departureTime = "04 Januari 2025 | 13.45",
        pricePerSeat = "28000",
        vehicleType = VehicleType.MOTOR,
        driverIdRide = 1
    )

    val departure = TerminalCustomer(
        id = 1,
        name = "Yogyakarta",
        terminalFullAddress = "Patehan, Kecamatan Kraton, Kota Yogyakarta",
        terminalRegencyId = 1,
        terminalLongitude = 0.0,
        terminalLatitude = 0.0,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Yogyakarta"
    )

    val arrival = TerminalCustomer(
        id = 2,
        name = "Purwokerto",
        terminalFullAddress = "Alun-alun Purwokerto",
        terminalRegencyId = 2,
        terminalLongitude = 0.0,
        terminalLatitude = 0.0,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Banyumas"
    )

    PassengerRideMotorPaymentWaitingScreen(
        transaction = transaction,
        booking = booking,
        ride = ride,
        departure = departure,
        arrival = arrival,
        remainingTime = Duration.ofMinutes(58),
        onBack = {}
    )
}

