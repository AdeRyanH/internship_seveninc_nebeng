package com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.nebeng_motor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.domain.model.driver.nebeng_motor.DriverLocationRideDriver
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideState
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideUiState
import com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.nebeng_motor.bottom_sheet.DriverBottomSheetContent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverNebengMotorOnTheWayScreen(
    driverState: DriverRideUiState,
    onBack: () -> Unit = {},
    onStartRide: () -> Unit = {},
    onFinishRide: () -> Unit = {}
) {
    val rideState = remember(driverState.isSendingLocation) {
        if (driverState.isSendingLocation) {
            DriverRideState.ON_THE_WAY
        } else {
            DriverRideState.NOT_STARTED
        }
    }

    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = true
    )

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        ),
        sheetPeekHeight = 360.dp,
        sheetContainerColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        topBar = {
            TopAppBar(
                title = { Text("Perjalanan Berlangsung", color = Color.White) },
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
        },
        sheetContent = {
            DriverBottomSheetContent(
                uiState = rideState,
                onPrimaryAction = {
                    when (rideState) {
                        DriverRideState.NOT_STARTED -> onStartRide()
                        DriverRideState.ON_THE_WAY  -> onFinishRide()
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DriverNebengMotorMap(
                lastLocation = driverState.lastLocation
            )
        }
    }
}

@Composable
fun DriverNebengMotorMap(
    lastLocation: DriverLocationRideDriver?
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp),
        factory = { context ->
            MapView(context).apply {
                setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                controller.setZoom(16.0)
            }
        },
//                // Dummy posisi driver (sementara)
//                val driverPoint = lastLocation?.let {
//                    GeoPoint(it.latitude, it.longitude)
//                } ?:  GeoPoint(-7.8014, 110.3647)
//
//                controller.setCenter(driverPoint)
//
//                overlays.add(
//                    Marker(this).apply {
//                        position = driverPoint
//                        icon = ContextCompat.getDrawable(
//                            context,
//                            R.drawable.ic_pin_point
//                        )
//                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//                    }
//                )
//
//                invalidate()
        update = { mapView ->
            val point = lastLocation?.let {
                GeoPoint(it.latitude, it.longitude)
            } ?: GeoPoint(-7.8014, 110.3647)

            mapView.controller.setCenter(point)
            mapView.overlays.clear()

            mapView.overlays.add(
                Marker(mapView).apply {
                    position = point
                    icon = ContextCompat.getDrawable(
                        mapView.context,
                        R.drawable.ic_pin_point
                    )
                    setAnchor(
                        Marker.ANCHOR_CENTER,
                        Marker.ANCHOR_BOTTOM
                    )
                }
            )
        }
    )
}

@Composable
fun DriverRouteBottomSheet(onFinishRide: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        // 🔹 Status
        Text(
            text = "Status Perjalanan",
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Menuju titik tujuan",
            color = Color(0xFF2ECC71),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        // 🔹 Info Tujuan
        Card(
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Tujuan", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(
                    "Alun-alun Kidul Yogyakarta",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Patehan, Kecamatan Kraton, Kota Yogyakarta",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // 🔹 Button aksi driver
        Button(
            onClick = onFinishRide,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F3D82)
            )
        ) {
            Text(
                "Selesaikan Perjalanan",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//private fun PreviewDriverNebengMotorOnTheWayScreen() {
//    MaterialTheme {
//        DriverNebengMotorOnTheWayScreen()
//    }
//}
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewDriverNebengMotorOnTheWayScreen() {
    MaterialTheme {
        DriverNebengMotorOnTheWayScreen(
            driverState = DriverRideUiState(
                isSendingLocation = true,
                lastLocation = DriverLocationRideDriver(
                    id = 1,
                    rideId = 1,
                    driverId = 1,
                    latitude = -7.8014,
                    longitude = 110.3647,
                    lastSeenAt = "",
                    isActive = true,
                    createdAt = "",
                    updatedAt = ""
                )
            )
        )
    }
}

