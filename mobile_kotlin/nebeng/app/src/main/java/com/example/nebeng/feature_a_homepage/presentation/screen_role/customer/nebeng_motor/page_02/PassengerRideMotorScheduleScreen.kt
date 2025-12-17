package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_02

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.nebeng.R
import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PassengerRideCustomer
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorScheduleScreen(
    session: BookingSession,
    rides: List<PassengerRideCustomer>,
    onBack: () -> Unit = {},
    onDetailClick: (Int) -> Unit = {},   // index order sementara
    onSelectRide: (PassengerRideCustomer) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
    ) {

        // ==================== TOP BAR ====================
        TopAppBar(
            title = {
                Text(
                    text = "${session.selectedDepartureTerminal?.regencyName} → ${session.selectedArrivalTerminal?.regencyName}",
                    color = Color(0xFF0F3D82),
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left_24),
                        contentDescription = null,
                        tint = Color(0xFF0F3D82)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.White)
        )

        Spacer(Modifier.height(6.dp))

        // =================== DAFTAR CARD ==================
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(rides) { ride ->
                OrderCard(
                    ride = ride,
                    startTerminal = session.selectedDepartureTerminal,
                    endTerminal = session.selectedArrivalTerminal,
                    onDetailClick = {
                        Log.d("UI_PAGE3", "UI PAGE2 → User klik ride ${ride.idPassengerRide}")
                        onSelectRide(ride)
                        onDetailClick(ride.idPassengerRide)
                    }
                )
                Spacer(Modifier.height(18.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderCard(
    ride: PassengerRideCustomer,
    startTerminal: TerminalCustomer?,
    endTerminal: TerminalCustomer?,
    onDetailClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {

            // =============== Tanggal & Jam ===============
            val date = ride.departureTime.substring(0, 10)
            val time = ride.departureTime.substring(11, 16)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(dateFormatted(date), fontWeight = FontWeight.SemiBold)
                Text("$time WIB", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(16.dp))

            // =============== RUTE ===============
            RouteRow(
                startTitle = startTerminal?.name ?: "Unknown",
                startDetail = startTerminal?.terminalFullAddress ?: "",
                endTitle = endTerminal?.name ?: "Unknown",
                endDetail = endTerminal?.terminalFullAddress ?: ""
            )

            Spacer(Modifier.height(12.dp))

            // =============== HARGA ===============
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Biaya", fontWeight = FontWeight.Medium)
                Text(
                    "Rp ${ride.pricePerSeat}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    color = Color(0xFF0F3D82)
                )
            }

            Spacer(Modifier.height(12.dp))

            Divider(color = Color(0xFFE8E8E8), thickness = 1.dp)

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Selengkapnya",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0F3D82),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onDetailClick)
                    .padding(vertical = 4.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatted(date: String): String {
    return try {
        val parsed = LocalDate.parse(date)
        parsed.format(DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy"))
    } catch (e: Exception) {
        date
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
private fun PreviewPassengerRideMotorScheduleScreen() {

    val dummyStartTerminal = TerminalCustomer(
        id = 4,
        name = "Terminal Giwangan",
        terminalFullAddress = "Jl. Imogiri Timur, Umbulharjo, Yogyakarta",
        terminalRegencyId = 3402,
        terminalLongitude = 110.3899,
        terminalLatitude = -7.8298,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Yogyakarta"
    )

    val dummyEndTerminal = TerminalCustomer(
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

    val dummySession = BookingSession(
        selectedDepartureTerminal = dummyStartTerminal,
        selectedArrivalTerminal = dummyEndTerminal,
        selectedDate = LocalDate.of(2025, 12, 2)
    )

    val dummyRides = listOf(
        PassengerRideCustomer(
            idPassengerRide = 3,
            driverId = 1,
            departureTerminalId = 4,
            arrivalTerminalId = 5,
            rideStatus = RideStatus.DIBATALKAN,
            seatsReservedRide = 0,
            seatsAvailableRide = 4,
            departureTime = "2025-12-02T06:00:00.000000Z",
            pricePerSeat = "16537",
            vehicleType = VehicleType.MOTOR,
            driverIdRide = 1
        ),
        PassengerRideCustomer(
            idPassengerRide = 4,
            driverId = 1,
            departureTerminalId = 4,
            arrivalTerminalId = 5,
            rideStatus = RideStatus.DIBATALKAN,
            seatsReservedRide = 0,
            seatsAvailableRide = 4,
            departureTime = "2025-12-02T08:30:00.000000Z",
            pricePerSeat = "20000",
            vehicleType = VehicleType.MOTOR,
            driverIdRide = 1
        )
    )

    PassengerRideMotorScheduleScreen(
        session = dummySession,
        rides = dummyRides,
        onBack = {},
        onDetailClick = {},
        onSelectRide = {}
    )
}