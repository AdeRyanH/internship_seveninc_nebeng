package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.feature_a_homepage.domain.mapper.toLocationUi
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet.LocationUiModel
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet.SelectLocationBottomSheet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorScreen(
    session: BookingSession,
    loading: Boolean,
    error: String?,
    terminals: List<TerminalCustomer>,
    onBack: () -> Unit = {},
    // ⬇️ ganti: kirim LocationUiModel ke luar
    onSelectStartLocation: (LocationUiModel) -> Unit = {},
    onSelectEndLocation: (LocationUiModel) -> Unit = {},
    onSelectDate: (LocalDate) -> Unit = {},
    onNext: () -> Unit = {}
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var isSelectingStart by remember { mutableStateOf(true) }

    var showCalendarSheet by remember { mutableStateOf(false) }

    val startLocation = session.selectedDepartureTerminal
    val endLocation = session.selectedArrivalTerminal
    val selectedDate = session.selectedDate

    LaunchedEffect(terminals) {
        println("Terminal UI: ")
        terminals.forEach {
            println(" - ${it.name} | ${it.terminalFullAddress} | ${it.regencyName}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
            .padding(bottom = 56.dp)
    ) {

        // ================= HEADER BIRU ==================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            // Background header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp)
                    .clip(RoundedCornerShape(bottomStart = 34.dp, bottomEnd = 34.dp))
                    .background(Color(0xFF0F3D82))
            )

            TopAppBar(
                title = {
                    Text(
                        "Nebeng Motor",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.padding(top = 6.dp)
            )

            // ========= CARD INPUT (ROUTE + DATE) =============
            PassengerRouteCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = 40.dp),
                startRegency = session.selectedDepartureTerminal?.regencyName ?: "Kota Awal",
                startDisplay = session.selectedDepartureTerminal?.name ?: "Lokasi Awal",
                startDetail = session.selectedDepartureTerminal?.terminalFullAddress ?: "Pilih Lokasi Awal",
                endRegency = session.selectedArrivalTerminal?.regencyName ?: "Kota Tujuan",
                endDisplay = session.selectedArrivalTerminal?.name ?: "Pilih Lokasi Tujuan",
                endDetail = session.selectedArrivalTerminal?.terminalFullAddress ?: "Pilih Lokasi Tujuan",
                dateDisplay = session.selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "Tanggal Keberangkatan",
                onStartClick = {
                    isSelectingStart = true
                    showBottomSheet = true
                },
                onEndClick = {
                    isSelectingStart = false
                    showBottomSheet = true
                },
                onDateClick = { showCalendarSheet = true }
            )
        }

        Spacer(Modifier.height(60.dp)) // beri ruang setelah kartu input

        // ================= HISTORI ALAMAT ================
        Text(
            text = "Histori Alamat",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(Modifier.height(12.dp))

        val history = (1..10).map {
            "Yogyakarta · Pos $it" to "Patehan, Kecamatan Kraton, Kota Yogyakarta 55133"
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            items(10) {
                Text(
                    text = "Belum ada history (nanti diisi API)",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        // ===== BUTTON SELANJUTNYA — FIX DI BAWAH =====
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
//                .padding(bottom = 48.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0F3D82))
        ) {
            Text("Selanjutnya", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        }
    }

    if (showBottomSheet) {
        val locationUiModels = terminals.map { it.toLocationUi() }

        SelectLocationBottomSheet(
            title = if (isSelectingStart) "Pilih Lokasi Awal" else "Pilih Lokasi Tujuan",
            locations = locationUiModels,   // nanti dihubungkan ke session.listTerminals
            onSelect = { selected ->
                if(isSelectingStart) {
                    onSelectStartLocation(selected)
                } else {
                    onSelectEndLocation(selected)
                }

                showBottomSheet = false
            },
            onDismiss = { showBottomSheet = false }
        )

        LaunchedEffect(terminals) {
            println("Terminal UI: ")
            terminals.forEach {
                println(" - ${it.name} | ${it.terminalFullAddress} | ${it.regencyName}")
            }
        }
    }

    if (showCalendarSheet) {
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { date ->
                onSelectDate(date)
                showCalendarSheet = false
            },
            onDismiss = { showCalendarSheet = false }
        )
    }

}

@Composable
private fun PassengerRouteCard(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
    onDateClick: () -> Unit,
    startRegency: String,
    startDisplay: String,
    startDetail: String,
    endRegency: String,
    endDisplay: String,
    endDetail: String,
    dateDisplay: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color(0xFFE6E6E6))
    ) {
        Column(Modifier.padding(20.dp)) {

            // =====================
            // LOKASI AWAL & TUJUAN
            // =====================
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {

                    // =====================
                    // LOKASI AWAL & TUJUAN
                    // =====================
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),   // ← kunci agar line vertikal menyesuaikan
                        verticalAlignment = Alignment.Top
                    ) {

                        // KIRI → RAIL + DOT
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(top = 4.dp, bottom = 4.dp)
                                .width(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            RailDot(color = Color(0xFF2ECC71)) // dot hijau lokasi awal

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .width(2.dp)
                                    .background(Color(0xFFBCC6CE)) // garis vertical
                            )

                            RailDot(color = Color(0xFFFF6E42)) // dot oranye lokasi tujuan
                        }

                        Spacer(Modifier.width(12.dp))

                        // KANAN → LABEL & INPUT
                        Column(modifier = Modifier.weight(1f)) {

                            // Lokasi Awal
                            Text(text = "$startRegency * $startDisplay", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            LocationBox(startDetail, onStartClick)

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                color = Color(0xFFE0E0E0)
                            )

                            // Lokasi Tujuan
                            Text(text =  "$endRegency * $endDisplay", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            LocationBox(endDetail, onEndClick)
                        }
                    }
                }
            }


            Spacer(Modifier.height(18.dp))

            // Tanggal Keberangkatan
            Card(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDateClick() }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_24),
                        contentDescription = null,
                        tint = Color(0xFF0F3D82),
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(Modifier.width(14.dp))
//                    Text("Tanggal Keberangkatan", fontWeight = FontWeight.Medium)
                    Text(dateDisplay, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun RailDot(color: Color) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(color, CircleShape)
            .shadow(8.dp, CircleShape)
    )
}

@Composable
private fun LocationBox(
    placeholder: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp), // vertical lebih fleksibel
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Text otomatis multiline
            Text(
                text = placeholder,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.padding(start = 6.dp, top = 2.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewPassengerRideMotorScreen() {

    val dummyTerminals = listOf(
        TerminalCustomer(
            id = 1,
            name = "Terminal Giwangan",
            terminalFullAddress = "Jl. Imogiri Timur, Umbulharjo, Yogyakarta",
            terminalRegencyId = 3402,
            terminalLongitude = 110.3899,
            terminalLatitude = -7.8298,
            publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
            terminalType = TerminalType.PUBLIC,
            regencyName = "Yogyakarta"
        ),
        TerminalCustomer(
            id = 2,
            name = "Terminal Jombor",
            terminalFullAddress = "Jl. Magelang KM 8, Sleman",
            terminalRegencyId = 3404,
            terminalLongitude = 110.3102,
            terminalLatitude = -7.7309,
            publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
            terminalType = TerminalType.PUBLIC,
            regencyName = "Sleman"
        )
    )

    val dummySession = BookingSession(
        listTerminals = dummyTerminals,
        selectedDepartureTerminal = dummyTerminals[0],
        selectedArrivalTerminal = null,
        selectedDate = null
    )

    PassengerRideMotorScreen(
        session = dummySession,
        loading = false,
        error = null,
        terminals = dummyTerminals,
        onBack = {},
        onSelectStartLocation = {},
        onSelectEndLocation = {},
        onSelectDate = {},
        onNext = {}
    )
}
