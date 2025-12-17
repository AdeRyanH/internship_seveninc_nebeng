package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_06

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideBookingCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerTransactionCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
import java.time.Duration

//import kotlin.time.Duration

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorPaymentStatusScreen(
    transaction: PassengerTransactionCustomer,
    booking: PassengerRideBookingCustomer,
    ride: PassengerRideCustomer?,
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?,
    remainingTime: Duration,
    onBack: () -> Unit,
    onCheckStatus: () -> Unit
) {
    // ------- Dummy Timer 00:59:50 -------
    val hours = remainingTime.toHours().toInt()
    val minutes = remainingTime.toMinutesPart()
    val seconds = remainingTime.toSecondsPart()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ================= TOP BAR =================
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
                containerColor = Color(0xFF0F3D82)
            )
        )

        // ================= SCROLLABLE CONTENT =================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp), // ✅ penting agar button tidak nabrak
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(22.dp))

            // ========== BOX TIMER ==========
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text("Sisa waktu pembayaran :", fontSize = 14.sp, color = Color.Black)

                Spacer(Modifier.height(14.dp))

                TimerBox(hours, minutes, seconds)

                Spacer(Modifier.height(14.dp))

                Text("Batas Akhir Pembayaran", fontWeight = FontWeight.Medium, color = Color.Gray)
                Text(
                    text = transaction.paymentExpiredAt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(26.dp))

            // ========== CARD VIRTUAL ACCOUNT ==========
            VaPaymentCard(transaction)

            Spacer(Modifier.height(22.dp))

            // ========== CARD DETAIL RUTE ==========
            RideInfoCard(
                ride = ride,
                departure = departure,
                arrival = arrival,
                booking = booking
            )

            Spacer(Modifier.weight(1f))

            // ========== BUTTON ==========
            Button(
                onClick = onCheckStatus,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF0F3D82))
            ) {
                Text("Cek Status Pembayaran", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun TimeSeparator() {
    Column(
        modifier = Modifier.height(52.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color(0xFFD3D8DE), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color(0xFFD3D8DE), CircleShape)
        )
    }
}

@Composable
private fun TimerBox(h: Int, m: Int, s: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
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
            .size(68.dp)
            .shadow(8.dp, RoundedCornerShape(22.dp)) // efek bayangan seluruh sisi
            .background(Color(0xFF0F3D82), RoundedCornerShape(22.dp)) // warna biru
            .border(3.dp, Color.White, RoundedCornerShape(22.dp)),   // border putih tebal
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun VaPaymentCard(
    transaction: PassengerTransactionCustomer
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Color(0xFFE1E3E6))
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(transaction.paymentType, fontWeight = FontWeight.Medium)
                Icon(
                    painter = painterResource(id = R.drawable.qris),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = transaction.vaNumber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center        // ⬅️ kunci
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    clipboardManager.setText(
                        AnnotatedString(transaction.vaNumber)
                    )

                    Toast.makeText(context, "Nomor VA disalin", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF0F3D82))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.qris),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Salin")
            }
        }
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
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE1E3E6)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(ride?.departureTime ?: "-", fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(18.dp))

            RouteRow(
                departureName = departure?.name ?: "-",
                departureAddress = departure?.terminalFullAddress ?: "-",
                arrivalName = arrival?.name ?: "-",
                arrivalAddress = arrival?.terminalFullAddress ?: "-"
            )

            Spacer(Modifier.height(18.dp))

            Divider(color = Color(0xFFE8E8E8), thickness = 1.dp)

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
private fun PreviewPassengerRideMotorPaymentStatusScreen() {

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

    PassengerRideMotorPaymentStatusScreen(
        transaction = transaction,
        booking = booking,
        ride = ride,
        departure = departure,
        arrival = arrival,
        remainingTime = Duration.ofMinutes(58),
        onBack = {},
        onCheckStatus = {}
    )
}
