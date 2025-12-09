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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.nebeng.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorOnTheWayScreen(
//    onBack: () -> Unit = {}
    onBack: () -> Unit = {},
    onCancelOrder: () -> Unit = {}
) {
    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = true   // ⬅️ BOTTOM SHEET TIDAK PERNAH HILANG
    )

    BottomSheetScaffold(
        sheetContent = { RouteDetailsBottomSheet(onCancelOrder) },
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
            val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp),
                factory = { context ->
                    MapView(context).apply {
                        setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                        controller.setZoom(15.0)
                        controller.setCenter(GeoPoint(-7.801194, 110.364917))

                        // =========================
                        // Markers Initialization
                        // =========================

                        val departurePoint = GeoPoint(-7.801194, 110.364917)
                        val arrivalPoint = GeoPoint(-7.804800, 110.365900)
                        val driverPoint = GeoPoint(-7.802500, 110.365300)  // posisi awal driver

                        val departureMarker = Marker(this).apply {
                            position = departurePoint
                            icon = ContextCompat.getDrawable(context, R.drawable.ic_pin_point)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }

                        val arrivalMarker = Marker(this).apply {
                            position = arrivalPoint
                            icon = ContextCompat.getDrawable(context, R.drawable.ic_pin_point)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }

                        val driverMarker = Marker(this).apply {
                            position = driverPoint
                            icon = ContextCompat.getDrawable(context, R.drawable.ic_pin_point)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        }

                        overlays.add(departureMarker)
                        overlays.add(arrivalMarker)
                        overlays.add(driverMarker)

                        invalidate()

                        // ======================================================
                        // 🔄 Update posisi driver secara realtime (tiap 3 detik)
                        // ======================================================
                        lifecycleScope?.launch {
                            while (true) {
                                delay(3000)

                                // simulasi driver bergerak
                                val newLat = driverMarker.position.latitude + 0.00030
                                val newLon = driverMarker.position.longitude + 0.00010
                                driverMarker.position = GeoPoint(newLat, newLon)

                                invalidate()
                            }
                        }
                    }
                }
            )
        }
    }
}


@Composable
private fun MapContent() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                controller.setZoom(16.0)
                controller.setCenter(
                    org.osmdroid.util.GeoPoint(-7.801194, 110.364917) // Yogyakarta contoh
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp)   // supaya tidak tertutup TopAppBar
    )
}

@Composable
private fun RouteDetailsBottomSheet(onCancelOrder: () -> Unit) {
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
            Text("FR-2345678997543234", fontWeight = FontWeight.Bold)
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
                    Text("Nmax - HITAM", fontWeight = FontWeight.Bold)
                    Text("R 2424 MJ", color = Color.Gray, fontSize = 13.sp)
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
                Text("Jamal Driver", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Icon(painterResource(id = R.drawable.ic_motor), contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Icon(painterResource(id = R.drawable.ic_motor), contentDescription = null)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Route
        RouteRow(
            startTitle = "Yogyakarta",
            startDetail = "Patehan, Kecamatan Kraton, Kota Yogyakarta 55133",
            endTitle = "Purwokerto",
            endDetail = "Alun-alun Purwokerto"
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
                Text("Tunai", fontWeight = FontWeight.Medium)
            }
            Text("Rp 120.000", fontWeight = FontWeight.Bold, color = Color(0xFF0F3D82))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRouteRowCard() {
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
                Text("04 Januari 2025 | 13.45 – 18.45", fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(14.dp))

                RouteRow(
                    startTitle = "Yogyakarta · Pos 1",
                    startDetail = "Patehan, Kecamatan Kraton, Kota Yogyakarta 55133",
                    endTitle = "Purwokerto · Pos 2",
                    endDetail = "Jl. Prof. Dr. Suharso No.8, Purwokerto Lor, Kec. Purwokerto Timur"
                )

                Spacer(Modifier.height(14.dp))
                Divider()
                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("No Pemesanan:", fontWeight = FontWeight.Medium)
                    Text("FR-2345678997543234", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewRouteDetailsScreen() {
    PassengerRideMotorOnTheWayScreen()
}
