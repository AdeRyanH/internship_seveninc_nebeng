package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_03

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.CustomerCurrentCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerPricingCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorScheduleDetailScreen(
    session: BookingSession,
    onBack: () -> Unit = {},
    onPay: () -> Unit = {}
) {
    val customer = session.customer
    val ride = session.selectedRide
    val dep  = session.selectedDepartureTerminal
    val arr  = session.selectedArrivalTerminal
    val pricing  = session.selectedPricing

    if (customer == null || ride == null || dep == null || arr == null) {
        Text("Data tidak lengkap", modifier = Modifier.padding(20.dp))
        return
    }

    val odt = OffsetDateTime.parse(ride.departureTime)
    val dateText = odt.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    val timeText = odt.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    val bookingIdText = "FR-${ride.idPassengerRide}"

    var isAgreeChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
    ) {

        // =============== TOP BAR ==================
        TopAppBar(
            title = {
                Text(
                    "Detail Pesanan",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F3D82))
        )

        // ================= SCROLLABLE CONTENT =================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp) // ✅ penting agar button tidak nabrak
        ) {
            Spacer(Modifier.height(12.dp))

            // =============== NO PESANAN ==================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("No Pesanan:", fontWeight = FontWeight.SemiBold)
                Text(bookingIdText, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(16.dp))

            // =============== CARD DETAIL PERJALANAN ==================
//        OrderDetailCard()
            OrderDetailCard(
                dateText = dateText,
                timeText = timeText,
                dep = dep,
                arr = arr,
                totalPrice = session.totalPrice
            )

            Spacer(Modifier.height(26.dp))

            // =============== LABEL PENUMPANG ==================
            Text(
                text = "Penumpang",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )

            Spacer(Modifier.height(12.dp))

            // =============== CARD PENUMPANG ==================
//        PassengerInfoCard()
            PassengerInfoCard(
                customerName = customer.customerName,
                customerTelephone = customer.customerTelephone
            )

            Spacer(Modifier.height(26.dp))

            // =============== CARD TOTAL PEMBAYARAN ==================
//        TotalPaymentCard()
            TotalPaymentCard(totalPrice = session.totalPrice)

            Spacer(Modifier.height(18.dp))


            // =============== CHECKBOX SYARAT & KETENTUAN ==================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAgreeChecked,
                    onCheckedChange = { isAgreeChecked = it }
                )
                Text(
                    buildAnnotatedString {
                        append("Saya telah membaca dan setuju terhadap Syarat ")
                        pushStyle(
                            SpanStyle(
                                color = Color(0xFF0F3D82),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        append("dan ketentuan pembelian tiket")
                        pop()
                    },
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // =============== BUTTON BAYAR ==================
            Button(
                onClick = { if (isAgreeChecked) onPay() },
                enabled = isAgreeChecked,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F3D82),
                    disabledContainerColor = Color(0xFF9BB1D4)
                )
            ) {
                Text(
                    text = "Lanjutkan Pembayaran",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun OrderDetailCard(
    dateText: String,
    timeText: String,
    dep: TerminalCustomer,
    arr: TerminalCustomer,
    totalPrice: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text("$dateText | $timeText WIB", fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(16.dp))

            RouteRow(
                startTitle = dep.name,
                startDetail = dep.terminalFullAddress,
                endTitle = arr.name,
                endDetail = arr.terminalFullAddress
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Biaya", fontWeight = FontWeight.Medium)
                Text(
                    "Rp $totalPrice",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F3D82),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PassengerInfoCard(
    customerName: String,
    customerTelephone: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color(0xFFF3F5F9)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            InfoRow("Nama Penumpang:", customerName)
            Spacer(Modifier.height(6.dp))
            InfoRow("No. Tlp:", customerTelephone)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun TotalPaymentCard(
    totalPrice: Int
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total Pembayaran", fontWeight = FontWeight.SemiBold)
            Text("Rp $totalPrice", fontWeight = FontWeight.Bold, color = Color(0xFF0F3D82))
        }
    }
}

@Composable
private fun RouteRow(
    startTitle: String,
    startDetail: String,
    endTitle: String,
    endDetail: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 6.dp, bottom = 6.dp)
                .width(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // DOT HIJAU
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF2ECC71), shape = CircleShape)
            )

            // GARIS TENGAH
            Box(
                modifier = Modifier
                    .weight(1f)
                    .width(2.dp)
                    .background(Color(0xFFC8CCD0))
            )

            // DOT ORANYE
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFFFF6E42), shape = CircleShape)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(Modifier.weight(1f)) {
            Text(startTitle, fontWeight = FontWeight.Bold)
            Text(startDetail, color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            Text(endTitle, fontWeight = FontWeight.Bold)
            Text(endDetail, color = Color.Gray, fontSize = 13.sp)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPassengerRideMotorDetailScreen() {

    val dummyCustomer = CustomerCurrentCustomer(
        idCustomer = 1,
        customerName = "Ailsa Naswya",
        customerTelephone = "0829-9273-0984",
    )

    val dummyDep = TerminalCustomer(
        id = 4,
        name = "Terminal Giwangan",
        terminalFullAddress = "Jl. Imogiri Timur, Umbulharjo, Yogyakarta",
        terminalRegencyId = 3402,
        terminalLongitude = 110.3899,
        terminalLatitude = -7.8298,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Kota Yogyakarta"
    )

    val dummyArr = TerminalCustomer(
        id = 5,
        name = "Terminal Jombor",
        terminalFullAddress = "Jl. Magelang KM 8, Sleman",
        terminalRegencyId = 3404,
        terminalLongitude = 110.3102,
        terminalLatitude = -7.7309,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Sleman"
    )

    val dummyRide = PassengerRideCustomer(
        idPassengerRide = 101,
        driverId = 1,
        departureTerminalId = 4,
        arrivalTerminalId = 5,
        rideStatus = RideStatus.DIBATALKAN,
        seatsReservedRide = 0,
        seatsAvailableRide = 4,
        departureTime = "2025-12-02T06:00:00.000000Z",
        pricePerSeat = "20000",
        vehicleType = VehicleType.MOTOR,
        driverIdRide = 1
    )

    val dummyPricing = PassengerPricingCustomer(
        id = 99,
        departureTerminalId = 4,
        arrivalTerminalId = 5,
        pricePerSeat = 20000,
        commissionPercentage = 10,
        vehicleType = VehicleType.MOTOR,
        createdAt = "",
        updatedAt = ""
    )

    val dummySession = BookingSession(
        customer = dummyCustomer,
        selectedRide = dummyRide,
        selectedDepartureTerminal = dummyDep,
        selectedArrivalTerminal = dummyArr,
        selectedPricing = dummyPricing,
        totalPrice = 20000
    )

    PassengerRideMotorScheduleDetailScreen(
        session = dummySession,
        onBack = {},
        onPay = {}
    )
}
