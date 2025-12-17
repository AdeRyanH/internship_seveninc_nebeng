package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_09

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.nebeng.R
import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorOnTheWayScreen(
    session: BookingSession,
    onBack: () -> Unit = {},
    onCancelOrder: () -> Unit = {}
) {
    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = true   // ⬅️ BOTTOM SHEET TIDAK PERNAH HILANG
    )

    BottomSheetScaffold(
        sheetContent = { RouteDetailsBottomSheet(session, onCancelOrder) },
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
        sheetContainerColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetPeekHeight = 360.dp,        // ⬅️ posisi awal (seperti desain)
        topBar = {
            TopAppBar(
                title = { Text("Route details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F3D82))
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            NebengMotorMap(
                departure = session.selectedDepartureTerminal,
                arrival = session.selectedArrivalTerminal
            )
        }
    }
}

@Composable
private fun NebengMotorMap(
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp),
        factory = { context ->
            MapView(context).apply {
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                controller.setZoom(15.0)

                if (departure != null) {
                    val depPoint = GeoPoint(
                        departure.terminalLatitude,
                        departure.terminalLongitude
                    )

                    controller.setCenter(depPoint)

                    overlays.add(
                        Marker(this).apply {
                            position = depPoint
                            icon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_pin_point
                            )
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                    )
                }

                if (arrival != null) {
                    val arrPoint = GeoPoint(
                        arrival.terminalLatitude,
                        arrival.terminalLongitude
                    )

                    overlays.add(
                        Marker(this).apply {
                            position = arrPoint
                            icon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_pin_point
                            )
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                    )
                }

                invalidate()
            }
        }
    )
}


@Composable
private fun RouteDetailsBottomSheet(
//    onCancelOrder: () -> Unit
    session: BookingSession,
    onCancelOrder: () -> Unit
) {
    val booking = session.booking
    val transaction = session.transaction
    val departure = session.selectedDepartureTerminal
    val arrival = session.selectedArrivalTerminal
    val paymentMethod = session.selectedPaymentMethod
    val ride = session.selectedRide
    val driver = session.selectedDriver   // ✅ DATA DRIVER TIDAK BOLEH HILANG

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        // 🔹 No Pemesanan
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("No Pesanan :", fontWeight = FontWeight.Medium)
            Text(booking?.bookingCode ?: "-", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 Card Motor
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Jenis Kendaraan", fontWeight = FontWeight.Bold)
                    Text(ride?.vehicleType.toString(), color = Color.Gray, fontSize = 13.sp)
                }
                Image(
                    painterResource(id = R.drawable.ic_motor),  // gambar placeholder
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Card Driver
        Card(
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.ic_person_grey_24),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Text(  driver?.fullNameDriver ?: "-", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Icon(painterResource(id = R.drawable.ic_motor), contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Icon(painterResource(id = R.drawable.ic_motor), contentDescription = null)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Route (DOMAIN-BASED)
        RouteRow(
            departure = departure,
            arrival = arrival
        )

        Spacer(Modifier.height(16.dp))

        Divider()

        // 🔹 Payment Row
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.ic_person_grey_24), contentDescription = null, tint = Color(0xFF0F3D82))
                Spacer(Modifier.width(6.dp))
                Text(paymentMethod?.bankName ?: "-", fontWeight = FontWeight.Medium)
            }
            Text(transaction?.totalAmount.toString(), fontWeight = FontWeight.Bold, color = Color(0xFF0F3D82))
        }

        // 🔹 Button
        Button(
            onClick = onCancelOrder,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F3D82))
        ) {
            Text("Batalkan Pesanan", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun RouteRow(
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?
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
            Text(departure?.name ?: "-", fontWeight = FontWeight.Bold)
            Text(departure?.terminalFullAddress ?: "-", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            Text(arrival?.name ?: "-", fontWeight = FontWeight.Bold)
            Text(arrival?.terminalFullAddress ?: "-", color = Color.Gray, fontSize = 13.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRouteRowCard() {

    val previewDeparture = TerminalCustomer(
        id = 1,
        name = "Yogyakarta",
        terminalFullAddress = "Patehan, Kecamatan Kraton, Kota Yogyakarta 55133",
        terminalRegencyId = 3401,
        terminalLongitude = 110.3647,
        terminalLatitude = -7.8014,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Kota Yogyakarta"
    )

    val previewArrival = TerminalCustomer(
        id = 2,
        name = "Purwokerto",
        terminalFullAddress = "Jl. Prof. Dr. Suharso No.8, Purwokerto",
        terminalRegencyId = 3302,
        terminalLongitude = 109.2396,
        terminalLatitude = -7.4266,
        publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
        terminalType = TerminalType.PUBLIC,
        regencyName = "Kab. Banyumas"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
            .padding(20.dp)
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(1.dp, Color(0xFFE1E3E6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(18.dp)) {
                Text(
                    "04 Januari 2025 | 13.45 – 18.45",
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(14.dp))

                RouteRow(
                    departure = previewDeparture,
                    arrival = previewArrival
                )

                Spacer(Modifier.height(14.dp))
                Divider()
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("No Pemesanan:", fontWeight = FontWeight.Medium)
                    Text(
                        "FR-2345678997543234",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun PreviewRouteDetailsScreen() {
//    PassengerRideMotorOnTheWayScreen()
//}
