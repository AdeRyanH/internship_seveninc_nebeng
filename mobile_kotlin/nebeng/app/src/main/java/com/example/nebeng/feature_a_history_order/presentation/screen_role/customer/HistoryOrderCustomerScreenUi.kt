package com.example.nebeng.feature_a_history_order.presentation.screen_role.customer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import androidx.compose.material3.*
//import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.nebeng.core.utils.BookingStatus
import com.example.nebeng.core.utils.RideStatus
import com.example.nebeng.core.utils.VehicleType
import com.example.nebeng.feature_auth.domain.model.Auth
//import com.example.nebeng.feature_a_history_order.data.model.mapper.toPresentation
import com.example.nebeng.feature_a_history_order.domain.model.HistoryOrderItem
import com.example.nebeng.feature_a_history_order.presentation.HistoryOrderUiState
//import com.example.nebeng.feature_a_history_order.presentation.screen_role.driver.RideRouteItem
//import com.example.nebeng.feature_a_history_order.presentation.support_for_present.component.HistoryItemCard
import com.example.nebeng.feature_a_history_order.presentation.support_for_present.model.HistoryItemData

// =================================================================================================
// WORK
// =================================================================================================

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryOrderCustomerScreenUi(
////    orders: List<OrderCardData>,
//    uiState: HistoryOrderUiState,
//    onBack: () -> Unit = {},
//    onChangeSchedule: (HistoryItemData) -> Unit = {},
//) {
//    var selectedTab by remember { mutableStateOf(0) }              // 0=Riwayat, 1=Dalam Proses, 2=Jadwal
//    var selectedCategory by remember { mutableStateOf("Semua") }   // Semua / Motor / Mobil / Barang
//    var selectedStatus by remember { mutableStateOf("Semua Status") }
//
//    val blue900 = Color(0xFF0F2E6C)
//    val blue500 = Color(0xFF1A47B8)
//    val bg = Color(0xFFF8F9FD)
//
//    Scaffold(
//        containerColor = bg,
//        topBar = {
//            TopAppBar(
//                title = { Text("Pesanan", fontWeight = FontWeight.SemiBold) },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//
//            // ===== Tabs (Riwayat, Dalam Proses, Jadwal) =====
//            // 🔹 TAB BAR PALING ATAS (RIWAYAT / DALAM PROSES / JADWAL)
//            HistoryTopTabs(
//                selectedTab = selectedTab,
//                onTabSelected = { selectedTab = it }
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // ===== Filter chips (scrollable) =====
//            CategoryChipsRow(
//                categories = listOf("Semua", "Motor", "Mobil", "Barang", "Barang Transportasi Umum"),
//                selectedCategory = selectedCategory,
//                selectedStatus = selectedStatus,
//                onCategorySelected = { selectedCategory = it },
//                onStatusSelected = { selectedStatus = it },
//                activeColor = blue900,
//                borderColor = blue500
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // ===== List =====
////            val filtered = orders.filter { order ->
////                (selectedCategory == "Semua" || order.category == selectedCategory) &&
////                        when (selectedTab) {
////                            0 -> order.status in listOf("Dibatalkan", "Selesai")
////                            1 -> order.status in listOf("Proses", "Menunggu")
////                            2 -> order.status == "Jadwal"
////                            else -> true
////                        }
////            }
//
//            // ✅ Filtering data
//            val filtered = uiState.historyItems.filter { order ->
//                val matchCategory = selectedCategory == "Semua" || order.category == selectedCategory
//                val matchStatus = selectedStatus == "Semua Status" || order.status.equals(selectedStatus, ignoreCase = true)
//                val matchTab = when (selectedTab) {
//                    0 -> order.status in listOf("Dibatalkan", "Selesai")
//                    1 -> order.status in listOf("Proses", "Menunggu", "Dalam Perjalanan")
//                    2 -> order.status == "Jadwal"
//                    else -> true
//                }
//                matchCategory && matchStatus && matchTab
//            }
//
////            LazyColumn(
////                modifier = Modifier
////                    .fillMaxSize()
////                    .padding(horizontal = 16.dp),
////                verticalArrangement = Arrangement.spacedBy(12.dp)
////            ) {
////                items(filtered) { order ->
////                    OrderCard(
////                        data = order,
////                        onChangeSchedule = { onChangeSchedule(order) }
////                    )
////                }
////
////                item { Spacer(Modifier.height(12.dp)) }
////            }
////            // === List ===
////            when {
////                uiState.isLoading -> Box(
////                    modifier = Modifier.fillMaxSize(),
////                    contentAlignment = Alignment.Center
////                ) {
////                    CircularProgressIndicator(color = blue500)
////                }
////
////                uiState.errorMessage != null -> Box(
////                    modifier = Modifier.fillMaxSize(),
////                    contentAlignment = Alignment.Center
////                ) {
////                    Text(
////                        uiState.errorMessage,
////                        color = Color.Red,
////                        fontWeight = FontWeight.Medium
////                    )
////                }
////
////                filtered.isEmpty() -> Box(
////                    modifier = Modifier.fillMaxSize(),
////                    contentAlignment = Alignment.Center
////                ) {
////                    Text(
////                        "Tidak ada pesanan.",
////                        color = Color.Gray,
////                        fontSize = 14.sp
////                    )
////                }
////
////                else -> LazyColumn(
////                    modifier = Modifier
////                        .fillMaxSize()
////                        .padding(horizontal = 16.dp),
////                    verticalArrangement = Arrangement.spacedBy(12.dp)
////                ) {
////                    items(filtered) { order ->
////                        HistoryItemCard(
////                            data = order,
////                            onChangeSchedule = { onChangeSchedule(order) }
////                        )
////                    }
////                }
////            }
//            // ===== Content area =====
//            when {
//                uiState.isLoading -> Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = blue500)
//                }
//
//                uiState.errorMessage != null -> Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(uiState.errorMessage ?: "", color = Color.Red, fontWeight = FontWeight.Medium)
//                }
//
//                filtered.isEmpty() -> Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Tidak ada pesanan.", color = Color.Gray, fontSize = 14.sp)
//                }
//
//                else -> LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
//                ) {
//                    items(filtered) { order ->
//                        HistoryItemCard(
//                            data = order,
//                            onChangeSchedule = { onChangeSchedule(order) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
///* ---------------------------- Chips row ---------------------------- */
//
//@Composable
//fun CategoryChipsRow(
//    categories: List<String>,
//    selectedCategory: String,
//    selectedStatus: String,
//    onCategorySelected: (String) -> Unit,
//    onStatusSelected: (String) -> Unit,
//    activeColor: Color,
//    borderColor: Color
//) {
//    var expanded by remember { mutableStateOf(false) }
//    val statuses = listOf("Semua Status", "Pending", "Dalam Perjalanan", "Selesai", "Dibatalkan")
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        items(categories) { cat ->
//            val isSelected = selectedCategory == cat
//
//            if (cat == "Semua") {
//                Box {
//                    Box(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(14.dp))
//                            .background(if (isSelected) activeColor else Color.Transparent)
//                            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
//                            .clickable { expanded = !expanded }
//                            .padding(horizontal = 16.dp, vertical = 10.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(
//                                text = selectedStatus,
//                                color = if (isSelected) Color.White else activeColor,
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                            Icon(
//                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                                contentDescription = null,
//                                tint = if (isSelected) Color.White else activeColor,
//                                modifier = Modifier.size(18.dp)
//                            )
//                        }
//                    }
//
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier.background(Color.White)
//                    ) {
//                        statuses.forEach { status ->
//                            DropdownMenuItem(
//                                text = { Text(status) },
//                                onClick = {
//                                    onStatusSelected(status)
//                                    expanded = false
//                                    onCategorySelected(cat)
//                                }
//                            )
//                        }
//                    }
//                }
//            } else {
//                Box(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(14.dp))
//                        .background(if (isSelected) activeColor else Color.Transparent)
//                        .border(1.dp, borderColor, RoundedCornerShape(14.dp))
//                        .clickable { onCategorySelected(cat) }
//                        .padding(horizontal = 16.dp, vertical = 10.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = cat,
//                        color = if (isSelected) Color.White else activeColor,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//            }
//        }
//    }
//}
//
///* ---------------------------- Order card ---------------------------- */
//
//@Composable
//private fun OrderCard(
//    data: OrderCardData,
//    onChangeSchedule: () -> Unit
//) {
//    val shape = RoundedCornerShape(12.dp)
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        shape = shape,
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(2.dp)
//    ) {
//        Column(Modifier.padding(14.dp)) {
//
//            // header: layanan + aksi/status
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    val icon = when (data.category) {
//                        "Motor" -> Icons.Default.TwoWheeler
//                        "Mobil" -> Icons.Default.DirectionsCar
//                        else -> Icons.Default.Inventory2
//                    }
//                    Icon(
//                        icon, null, tint = Color(0xFF1A47B8),
//                        modifier = Modifier
//                            .size(18.dp)
//                            .clip(CircleShape)
//                    )
//                    Spacer(Modifier.width(8.dp))
//                    Text(
//                        "Nebeng ${data.category}",
//                        fontSize = 13.sp,
//                        color = Color(0xFF1A47B8),
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//
//                when (data.status) {
//                    "Ubah Jadwal" -> Text(
//                        "Ubah Jadwal",
//                        color = Color(0xFFE27A00),
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        textDecoration = TextDecoration.Underline,
//                        modifier = Modifier.clickable { onChangeSchedule() }
//                    )
//                    "Dibatalkan" -> StatusPill("Dibatalkan", Color(0xFFFFE2E2), Color(0xFFD11A2A))
//                    "Selesai" -> StatusPill("Selesai", Color(0xFFE8F5EC), Color(0xFF1B8E5F))
//                    "Proses" -> StatusPill("Proses", Color(0xFFE9E7FF), Color(0xFF5C3BDB))
//                }
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            // rute
//            Row(verticalAlignment = Alignment.Bottom) {
//                Text(
//                    buildAnnotatedString {
//                        append(data.fromCity)
//                        append(" ")
//                        withStyle(SpanStyle(color = Color(0xFF1A47B8) )) {
//                            append(data.fromPos)
//                        }
//                    },
////                    text = data.fromCity + " ${data.fromPos}",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF2C2F36)
//                )
//
//                Spacer(Modifier.width(6.dp))
//                Text(
//                    text = "→",
//                    color = Color(0xFF2C2F36),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
////                    padding = padding(8.dp)
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                )
//
//                Spacer(Modifier.width(6.dp))
//                Text(
//                    buildAnnotatedString {
//                        append(data.toCity)
//                        append(" ")
//                        withStyle(SpanStyle(color = Color(0xFF1A47B8) )) {
//                            append(data.toPos)
//                        }
//                    },
////                    text = data.toCity + " ${data.toPos}",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF2C2F36)
//                )
//            }
//
//            Spacer(Modifier.height(8.dp))
//            // tanggal jam
//            Text("${data.dayDate}  •  ${data.time}", color = Color(0xFF6B7280), fontSize = 12.sp)
//
//            // kendaraan
//            Spacer(Modifier.height(4.dp))
//            Text(
//                text = "${data.vehicle} • ${data.plate}",
//                color = Color(0xFF6B7280),
//                fontSize = 12.sp
//            )
//
//            Spacer(Modifier.height(12.dp))
//            Divider(color = Color(0xFFE5E7EB))
//
//            // footer: total harga dan pax
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_person_grey_24),
//                        contentDescription = null,
//                        tint =Color(0xFF6B7280),
//                        modifier = Modifier
//                            .size(20.dp)
//                            .padding(end = 4.dp)
//                    )
//
//                    Text(
//                        text = "${data.pax} Orang  ",
//                        color = Color(0xFF6B7280),
//                        fontSize = 12.sp
//                    )
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Total Harga: ", color = Color(0xFF6B7280), fontSize = 13.sp)
//                Row(verticalAlignment = Alignment.CenterVertically) {
////                    Text("${data.pax} Orang  ", color = Color(0xFF6B7280), fontSize = 12.sp)
//                    Text(
//                        data.totalPrice,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF0F2E6C)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun StatusPill(text: String, bg: Color, fg: Color) {
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(12.dp))
//            .background(bg)
//            .padding(horizontal = 10.dp, vertical = 4.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text, color = fg, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun HistoryTopTabs(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    val activeBlue = Color(0xFF002F6C)
//    val inactiveGray = Color(0xFF6B7280)
//    val tabs = listOf("Riwayat", "Dalam Proses", "Jadwal Pesanan")
//
//    PrimaryTabRow(
//        selectedTabIndex = selectedTab,
//        containerColor = Color.White,
//        indicator = {}, // Nonaktifkan indikator bawaan
//        divider = {
//            Divider(
//                color = activeBlue.copy(alpha = 0.15f),
//                thickness = 1.dp
//            )
//        }
//    ) {
//        tabs.forEachIndexed { index, title ->
//            val isSelected = selectedTab == index
//            Tab(
//                selected = isSelected,
//                onClick = { onTabSelected(index) },
//                modifier = Modifier.height(64.dp), // ✅ Tinggi tetap untuk semua tab
//                text = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight() // isi penuh area Tab
//                            .padding(top = 6.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        // Box teks agar tinggi seragam (misal 2 baris maksimal)
//                        Box(
//                            modifier = Modifier
//                                .height(40.dp) // ✅ tinggi teks seragam
//                                .fillMaxWidth(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = title,
//                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
//                                color = if (isSelected) activeBlue else inactiveGray,
//                                fontSize = 15.sp,
//                                textAlign = TextAlign.Center,
//                                lineHeight = 18.sp,
//                                overflow = TextOverflow.Ellipsis,
//                                maxLines = 2
//                            )
//                        }
//
//                        // Garis bawah
//                        if (isSelected) {
//                            Spacer(Modifier.height(4.dp))
//                            Box(
//                                modifier = Modifier
//                                    .height(3.dp)
//                                    .width(60.dp)
//                                    .background(
//                                        color = activeBlue,
//                                        shape = RoundedCornerShape(2.dp)
//                                    )
//                            )
//                        }
//                    }
//                }
//            )
//        }
//    }
//}
//
///* ---------------------------- UI model (khusus layar ini) ---------------------------- */
//data class OrderCardData(
//    val category: String,          // "Motor" | "Mobil" | "Barang"
//    val status: String,            // "Dibatalkan" | "Ubah Jadwal" | "Selesai" | "Proses" | "Menunggu" | "Jadwal"
//    val fromCity: String,
//    val toCity: String,
//    val fromPos: String,           // "Yogyakarta Pos 1"
//    val toPos: String,             // "Purwokerto Pos 1"
//    val dayDate: String,           // "Senin, 2 September 2024"
//    val time: String,              // "09:00"
//    val vehicle: String,           // "Mobil Avanza"
//    val plate: String,             // "R 2424 MJ"
//    val pax: Int,
//    val totalPrice: String         // "Rp120.000"
//)
//
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////private fun PreviewHistoryOrderCustomerUi() {
////    val demo = listOf(
////        OrderCardData("Mobil","Dibatalkan","Yogyakarta","Purwokerto","Pos 1","Pos 1",
////            "Senin, 2 September 2024","09:00","Mobil Avanza","R 2424 MJ",2,"Rp120.000"),
////        OrderCardData("Mobil","Ubah Jadwal","Yogyakarta","Purwokerto","Pos 1","Pos 1",
////            "Kamis, 4 September 2024","09:00","Mobil Avanza","R 2424 MJ",2,"Rp120.000"),
////        OrderCardData("Motor","Selesai","Yogyakarta","Malang","Pos 1","Pos 2",
////            "Senin, 1 September 2024","13:00","Motor Beat","AB 2010 KA",1,"Rp 90.000")
////    )
////    MaterialTheme { HistoryOrderCustomerScreenUi(orders = demo) }
////}
//
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////private fun PreviewHistoryOrderCustomerUi() {
////    val demoState = HistoryOrderUiState(
////        historyItems = listOf(
////            HistoryItemData("C001", "Nebeng Mobil", "Yogyakarta → Purwokerto", "Rp120.000", "Selesai", "Mobil"),
////            HistoryItemData("C002", "Nebeng Motor", "Solo → Semarang", "Rp90.000", "Dalam Perjalanan", "Motor"),
////            HistoryItemData(
////                "C003",
////                "Nebeng Barang",
////                "Yogya → Purwokerto",
////                "Rp150.000",
////                "Dibatalkan",
////                "Barang"
////            )
////        )
////    )
////    MaterialTheme { HistoryOrderCustomerScreenUi(uiState = demoState) }
////}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHistoryCustomer() {
//    val sample = HistoryOrderUiState(
//        historyItems = listOf(
//            HistoryItemData("1", "Mobil", "Selesai", "Yogyakarta", "Purwokerto", "Pos 1", "Pos 2", "Senin, 2 Sept 2024", "09:00", "Avanza", "R 2424 MJ", 2, "Rp120.000"),
//            HistoryItemData("2", "Motor", "Proses", "Solo", "Semarang", "Pos 1", "Pos 2", "Selasa, 3 Sept 2024", "13:00", "Beat", "AB 2010 KA", 1, "Rp90.000"),
//            HistoryItemData("3", "Barang", "Ubah Jadwal", "Yogyakarta", "Magelang", "Pos 3", "Pos 4", "Rabu, 4 Sept 2024", "08:00", "Pickup", "B 1234 XY", 1, "Rp150.000")
//        )
//    )
//    MaterialTheme {
//        HistoryOrderCustomerScreenUi(uiState = sample)
//    }
//}


// =================================================================================================
// 🟦 Warna khas Nebeng
//private val NebengBlue = Color(0xFF002F6C)
//private val NebengGray = Color(0xFF6B7280)
//private val NebengBg = Color(0xFFF8F9FD)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryOrderCustomerScreenUi(
//    uiState: HistoryOrderUiState,
//    onBack: () -> Unit = {},
//    onChangeSchedule: (HistoryItemData) -> Unit = {},
//) {
//    var selectedTab by remember { mutableStateOf(0) } // Riwayat, Dalam Proses, Jadwal
//    var selectedCategory by remember { mutableStateOf("Semua") }
////    var selectedStatus by remember { mutableStateOf("Semua Status") }
////    var selectedStatus by remember { mutableStateOf<RideStatus?>(null) }
////    var selectedStatusLabel by remember { mutableStateOf("Semua Status") }
//
//    var selectedVehicleType by remember { mutableStateOf<VehicleType?>(null) }
//    var selectedStatus by remember { mutableStateOf<RideStatus?>(null) }
//    var selectedStatusLabel by remember { mutableStateOf("Semua Status") }
//
//    Scaffold(
//        containerColor = NebengBg,
//        topBar = {
//            TopAppBar(
//                title = { Text("Pesanan", fontWeight = FontWeight.SemiBold) },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
//            )
//        }
//    ) { padding ->
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            // === Tabs ===
//            HistoryTopTabsM3(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
//            Spacer(Modifier.height(8.dp))
//
//            // === Chips ===
//            CategoryChipsRowM3(
//                vehicleTypes = VehicleType.entries,
//                selectedVehicleType = selectedVehicleType,
//                selectedStatus = selectedStatus,
//                selectedStatusLabel = selectedStatusLabel,
//                onVehicleSelected = { selectedVehicleType = it },
//                onStatusSelected = { status, label ->
//                    selectedStatus = status
//                    selectedStatusLabel = label
//                }
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // === Filter data ===
//            val filtered = uiState.historyItems.filter { order ->
//                val matchVehicle = selectedVehicleType == null ||
//                        VehicleType.fromString(order.category) == selectedVehicleType
//
//                val matchStatus = selectedStatus == null ||
//                        RideStatus.fromString(order.status) == selectedStatus
//
//                val matchTab = when (selectedTab) {
//                    0 -> order.status.equals(RideStatus.SELESAI.value, true) ||
//                            order.status.equals(RideStatus.DIBATALKAN.value, true)
//                    1 -> order.status.equals(RideStatus.PENDING.value, true) ||
//                            order.status.equals(RideStatus.DALAM_PERJALANAN.value, true)
//                    2 -> order.status.equals("jadwal", true)
//                    else -> true
//                }
//
//                matchVehicle && matchStatus && matchTab
//            }
//
//            // === Content ===
//            when {
//                uiState.isLoading -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = NebengBlue)
//                }
//
//                uiState.errorMessage != null -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(uiState.errorMessage ?: "Terjadi kesalahan", color = Color.Red)
//                }
//
//                filtered.isEmpty() -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Tidak ada pesanan.", color = NebengGray)
//                }
//
//                else -> LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filtered) { order ->
//                        HistoryItemCard(order, onChangeSchedule)
//                    }
//                }
//            }
//        }
//    }
//}
//
//// =============================================================
//// 🔹 TABS (Riwayat / Dalam Proses / Jadwal)
//// =============================================================
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryTopTabsM3(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    val tabs = listOf("Riwayat", "Dalam Proses", "Jadwal Pesanan")
//
//    PrimaryTabRow(
//        selectedTabIndex = selectedTab,
//        containerColor = Color.White,
//        indicator = {},
//        divider = { Divider(color = NebengBlue.copy(alpha = 0.15f), thickness = 1.dp) }
//    ) {
//        tabs.forEachIndexed { index, title ->
//            val isSelected = selectedTab == index
//
//            Tab(
//                selected = isSelected,
//                onClick = { onTabSelected(index) },
//                modifier = Modifier.height(60.dp),
//                text = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(top = 6.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        // === Label teks (maks. 2 baris) ===
//                        Box(
//                            modifier = Modifier
//                                .weight(1f)
//                                .fillMaxWidth(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = title,
//                                color = if (isSelected) NebengBlue else NebengGray,
//                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
//                                fontSize = 15.sp,
//                                textAlign = TextAlign.Center,
//                                lineHeight = 18.sp,
//                                overflow = TextOverflow.Ellipsis,
//                                maxLines = 2
//                            )
//                        }
//
//                        // === Garis bawah dengan tinggi tetap ===
//                        Box(
//                            modifier = Modifier
//                                .height(3.dp)
//                                .width(60.dp)
//                                .background(
//                                    if (isSelected) NebengBlue else Color.Transparent,
//                                    RoundedCornerShape(2.dp)
//                                )
//                        )
//                    }
//                }
//            )
//        }
//    }
//}
//
//// =============================================================
//// 🔹 CHIPS (Semua Status / Motor / Mobil / Barang ...)
//// =============================================================
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryChipsRowM3(
//    vehicleTypes: List<VehicleType>,
//    selectedVehicleType: VehicleType?,
//    selectedStatus: RideStatus?,
//    selectedStatusLabel: String,
//    onVehicleSelected: (VehicleType?) -> Unit,
//    onStatusSelected: (RideStatus?, String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        // 🔹 Dropdown status
//        item {
//            Box {
//                Surface(
//                    color = NebengBlue,
//                    border = BorderStroke(1.dp, NebengBlue),
//                    shape = RoundedCornerShape(14.dp),
//                    modifier = Modifier.clickable { expanded = !expanded }
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                    ) {
//                        Text(
//                            text = selectedStatusLabel,
//                            color = Color.White,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier.background(Color.White)
//                ) {
//                    DropdownMenuItem(
//                        text = { Text("Semua Status") },
//                        onClick = {
//                            onStatusSelected(null, "Semua Status")
//                            expanded = false
//                        }
//                    )
//
//                    RideStatus.entries.forEach { status ->
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    text = status.value.replace("_", " ").replaceFirstChar { it.uppercase() },
//                                    fontSize = 14.sp,
//                                    fontWeight = if (selectedStatus == status) FontWeight.Bold else FontWeight.Normal,
//                                    color = if (selectedStatus == status) NebengBlue else Color.Black
//                                )
//                            },
//                            onClick = {
//                                onStatusSelected(status, status.value.replaceFirstChar { it.uppercase() })
//                                expanded = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        // 🔹 Tambahkan chip “Semua” (opsi default)
//        item {
//            val isSelected = selectedVehicleType == null
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(null) }
//            ) {
//                Text(
//                    text = "Semua",
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//
//        // 🔹 VehicleType chips
//        items(vehicleTypes) { type ->
//            val isSelected = selectedVehicleType == type
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(type) }
//            ) {
//                Text(
//                    text = type.value,
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//    }
//}
//
//
//// =============================================================
//// 🔹 CARD PESANAN
//// =============================================================
//@Composable
//private fun HistoryItemCard(
//    data: HistoryItemData,
//    onChangeSchedule: (HistoryItemData) -> Unit
//) {
//    ElevatedCard(
//        onClick = { onChangeSchedule(data) },
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(Modifier.padding(16.dp)) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text(
//                    "${data.fromCity} → ${data.toCity}",
//                    fontWeight = FontWeight.SemiBold,
//                    color = NebengBlue
//                )
//                Text(
//                    data.totalPrice,
//                    color = Color(0xFF1877F2),
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            Spacer(Modifier.height(4.dp))
//            Text("${data.dayDate} • ${data.time}", color = Color.Gray, fontSize = 13.sp)
//            Spacer(Modifier.height(8.dp))
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("${data.vehicle} (${data.plate})", color = Color.Gray, fontSize = 13.sp)
//                Surface(
//                    color = when (data.status.lowercase()) {
//                        "selesai" -> Color(0xFFDCF8E8)
//                        "proses" -> Color(0xFFFFF3E0)
//                        "dalam perjalanan" -> Color(0xFFE8F0FE)
//                        "dibatalkan" -> Color(0xFFFFEBEE)
//                        else -> Color(0xFFF5F5F5)
//                    },
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Text(
//                        data.status,
//                        color = when (data.status.lowercase()) {
//                            "selesai" -> Color(0xFF1B5E20)
//                            "proses" -> Color(0xFFE27A00)
//                            "dalam perjalanan" -> Color(0xFF1A47B8)
//                            "dibatalkan" -> Color(0xFFC62828)
//                            else -> Color.Gray
//                        },
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//private fun PreviewHistoryOrderCustomerScreenUi() {
//    val sample = HistoryOrderUiState(
//        currentUser = Auth(
//            id = 1,
//            name = "Riiha",
//            username = "riiha",
//            email = "r@demo.com",
//            user_type = "customer"
//        ),
//        historyItems = listOf(
//            HistoryItemData("1", "Mobil", "Selesai", "Yogyakarta", "Purwokerto", "Pos 1", "Pos 2", "Senin, 2 Sept 2024", "09:00", "Avanza", "R 2424 MJ", 2, "Rp120.000"),
//            HistoryItemData("2", "Motor", "Proses", "Solo", "Semarang", "Pos 1", "Pos 2", "Selasa, 3 Sept 2024", "13:00", "Beat", "AB 2010 KA", 1, "Rp90.000"),
//            HistoryItemData("3", "Barang", "Dibatalkan", "Yogyakarta", "Magelang", "Pos 3", "Pos 4", "Rabu, 4 Sept 2024", "08:00", "Pickup", "B 1234 XY", 1, "Rp150.000")
//        )
//    )
//    MaterialTheme {
//        HistoryOrderCustomerScreenUi(uiState = sample)
//    }
//}

//private val NebengBlue = Color(0xFF002F6C)
//private val NebengGray = Color(0xFF6B7280)
//private val NebengBg = Color(0xFFF8F9FD)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryOrderCustomerScreenUi(
//    uiState: HistoryOrderUiState,
//    onBack: () -> Unit = {},
//    onChangeSchedule: (HistoryOrderItem) -> Unit = {}
//) {
//    var selectedTab by remember { mutableStateOf(0) } // Riwayat / Dalam Proses / Jadwal
//    var selectedVehicleType by remember { mutableStateOf<VehicleType?>(null) }
//    var selectedStatus by remember { mutableStateOf<RideStatus?>(null) }
//    var selectedStatusLabel by remember { mutableStateOf("Semua Status") }
//
//    Scaffold(
//        containerColor = NebengBg,
//        topBar = {
//            TopAppBar(
//                title = { Text("Pesanan", fontWeight = FontWeight.SemiBold) },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
//            )
//        }
//    ) { padding ->
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            // === Tabs ===
//            HistoryTopTabsM3(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
//            Spacer(Modifier.height(8.dp))
//
//            // === Chips ===
//            CategoryChipsRowM3(
//                vehicleTypes = VehicleType.entries,
//                selectedVehicleType = selectedVehicleType,
//                selectedStatus = selectedStatus,
//                selectedStatusLabel = selectedStatusLabel,
//                onVehicleSelected = { selectedVehicleType = it },
//                onStatusSelected = { status, label ->
//                    selectedStatus = status
//                    selectedStatusLabel = label
//                }
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // === Filtered list ===
//            val filtered = uiState.historyItems.filter { order ->
//                val matchVehicle = selectedVehicleType == null || order.vehicleType == selectedVehicleType
////                val matchStatus = selectedStatus == null ||
////                        order.rideStatus.equals(selectedStatus.value, ignoreCase = true)
//                val matchStatus = selectedStatus?.let { safeStatus ->
//                    order.rideStatus.equals(safeStatus.value, ignoreCase = true)
//                } ?: true
//
//                val matchTab = when (selectedTab) {
//                    0 -> order.bookingStatus == BookingStatus.DITERIMA ||
//                            order.bookingStatus == BookingStatus.DITOLAK
//                    1 -> order.bookingStatus == BookingStatus.PENDING
//                    2 -> false // jadwal belum disediakan di backend
//                    else -> true
//                }
//                matchVehicle && matchStatus && matchTab
//            }
//
//            // === Content ===
//            when {
//                uiState.isLoading -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = NebengBlue)
//                }
//
//                uiState.errorMessage != null -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(uiState.errorMessage ?: "Terjadi kesalahan", color = Color.Red)
//                }
//
//                filtered.isEmpty() -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Tidak ada pesanan.", color = NebengGray)
//                }
//
//                else -> LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filtered) { order ->
//                        HistoryItemCard(order, onChangeSchedule)
//                    }
//                }
//            }
//        }
//    }
//}
//
//// =============================================================
//// 🔹 TABS (Riwayat / Dalam Proses / Jadwal)
//// =============================================================
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryTopTabsM3(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    val tabs = listOf("Riwayat", "Dalam Proses", "Jadwal Pesanan")
//
//    PrimaryTabRow(
//        selectedTabIndex = selectedTab,
//        containerColor = Color.White,
//        indicator = {},
//        divider = { Divider(color = NebengBlue.copy(alpha = 0.15f), thickness = 1.dp) }
//    ) {
//        tabs.forEachIndexed { index, title ->
//            val isSelected = selectedTab == index
//
//            Tab(
//                selected = isSelected,
//                onClick = { onTabSelected(index) },
//                modifier = Modifier.height(60.dp),
//                text = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(top = 6.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .weight(1f)
//                                .fillMaxWidth(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = title,
//                                color = if (isSelected) NebengBlue else NebengGray,
//                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
//                                fontSize = 15.sp,
//                                textAlign = TextAlign.Center,
//                                lineHeight = 18.sp,
//                                overflow = TextOverflow.Ellipsis,
//                                maxLines = 2
//                            )
//                        }
//                        Box(
//                            modifier = Modifier
//                                .height(3.dp)
//                                .width(60.dp)
//                                .background(
//                                    if (isSelected) NebengBlue else Color.Transparent,
//                                    RoundedCornerShape(2.dp)
//                                )
//                        )
//                    }
//                }
//            )
//        }
//    }
//}
//
//// =============================================================
//// 🔹 CHIPS (Semua Status / Motor / Mobil / Barang ...)
//// =============================================================
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryChipsRowM3(
//    vehicleTypes: List<VehicleType>,
//    selectedVehicleType: VehicleType?,
//    selectedStatus: RideStatus?,
//    selectedStatusLabel: String,
//    onVehicleSelected: (VehicleType?) -> Unit,
//    onStatusSelected: (RideStatus?, String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        // 🔹 Dropdown status
//        item {
//            Box {
//                Surface(
//                    color = NebengBlue,
//                    border = BorderStroke(1.dp, NebengBlue),
//                    shape = RoundedCornerShape(14.dp),
//                    modifier = Modifier.clickable { expanded = !expanded }
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                    ) {
//                        Text(
//                            text = selectedStatusLabel,
//                            color = Color.White,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier.background(Color.White)
//                ) {
//                    DropdownMenuItem(
//                        text = { Text("Semua Status") },
//                        onClick = {
//                            onStatusSelected(null, "Semua Status")
//                            expanded = false
//                        }
//                    )
//
//                    RideStatus.entries.forEach { status ->
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    text = status.value.replace("_", " ")
//                                        .replaceFirstChar { it.uppercase() },
//                                    fontSize = 14.sp,
//                                    fontWeight = if (selectedStatus == status) FontWeight.Bold else FontWeight.Normal,
//                                    color = if (selectedStatus == status) NebengBlue else Color.Black
//                                )
//                            },
//                            onClick = {
//                                onStatusSelected(status, status.value.replaceFirstChar { it.uppercase() })
//                                expanded = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        // 🔹 Chip “Semua” (default)
//        item {
//            val isSelected = selectedVehicleType == null
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(null) }
//            ) {
//                Text(
//                    text = "Semua",
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//
//        // 🔹 VehicleType chips
//        items(vehicleTypes) { type ->
//            val isSelected = selectedVehicleType == type
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(type) }
//            ) {
//                Text(
//                    text = type.value,
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//    }
//}
//
//// =============================================================
//// 🔹 CARD PESANAN
//// =============================================================
//@Composable
//private fun HistoryItemCard(
//    data: HistoryOrderItem,
//    onChangeSchedule: (HistoryOrderItem) -> Unit
//) {
//    ElevatedCard(
//        onClick = { onChangeSchedule(data) },
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(Modifier.padding(16.dp)) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Text(
//                    "${data.departureTerminalId} → ${data.arrivalTerminalId}",
//                    fontWeight = FontWeight.SemiBold,
//                    color = NebengBlue
//                )
//                Text(
//                    "Rp${data.totalPrice}",
//                    color = Color(0xFF1877F2),
//                    fontWeight = FontWeight.Bold
//                )
//            }
//            Spacer(Modifier.height(4.dp))
//            Text(
//                text = data.createdAt.take(10),
//                color = Color.Gray,
//                fontSize = 13.sp
//            )
//            Spacer(Modifier.height(8.dp))
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("${data.vehicleType.value} • ${data.driverName}", color = Color.Gray, fontSize = 13.sp)
//                Surface(
//                    color = when (data.bookingStatus) {
//                        BookingStatus.DITERIMA -> Color(0xFFDCF8E8)
//                        BookingStatus.PENDING -> Color(0xFFFFF3E0)
//                        BookingStatus.DITOLAK -> Color(0xFFFFEBEE)
//                        else -> Color(0xFFF5F5F5)
//                    },
//                    shape = RoundedCornerShape(8.dp)
//                ) {
//                    Text(
//                        text = data.bookingStatus?.value ?: "-",
//                        color = when (data.bookingStatus) {
//                            BookingStatus.DITERIMA -> Color(0xFF1B5E20)
//                            BookingStatus.PENDING -> Color(0xFFE27A00)
//                            BookingStatus.DITOLAK -> Color(0xFFC62828)
//                            else -> Color.Gray
//                        },
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//// =============================================================
//// 🔹 PREVIEW (dummy state untuk desain & testing)
//// =============================================================
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//private fun PreviewHistoryOrderCustomerScreenUi() {
//    val sampleUi = HistoryOrderUiState(
//        historyItems = listOf(
//            HistoryOrderItem(
//                bookingId = 1,
//                bookingCode = "NB1234",
//                createdAt = "2025-11-09T10:00:00Z",
//                departureTerminalId = 1,
//                arrivalTerminalId = 2,
//                seatsReserved = 2,
//                totalPrice = 120000,
//                bookingStatus = BookingStatus.DITERIMA,
//                vehicleType = VehicleType.MOBIL,
//                rideStatus = "Selesai",
//                driverName = "Riiha"
//            ),
//            HistoryOrderItem(
//                bookingId = 2,
//                bookingCode = "NB5678",
//                createdAt = "2025-11-08T15:30:00Z",
//                departureTerminalId = 3,
//                arrivalTerminalId = 5,
//                seatsReserved = 1,
//                totalPrice = 80000,
//                bookingStatus = BookingStatus.PENDING,
//                vehicleType = VehicleType.MOTOR,
//                rideStatus = "Dalam perjalanan",
//                driverName = "Kamado"
//            )
//        )
//    )
//    MaterialTheme {
//        HistoryOrderCustomerScreenUi(uiState = sampleUi)
//    }
//}


///* ================================================================
// * 🎨 Warna khas Nebeng
// * ================================================================ */
//private val NebengBlue = Color(0xFF002F6C)
//private val NebengGray = Color(0xFF6B7280)
//private val NebengBg = Color(0xFFF8F9FD)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryOrderCustomerScreenUi(
//    uiState: HistoryOrderUiState,
//    onBack: () -> Unit = {},
//    onChangeSchedule: (HistoryItemData) -> Unit = {},
//) {
//    var selectedTab by remember { mutableStateOf(0) }
//    var selectedVehicleType by remember { mutableStateOf<VehicleType?>(null) }
//    var selectedStatus by remember { mutableStateOf<RideStatus?>(null) }
//    var selectedStatusLabel by remember { mutableStateOf("Semua Status") }
//
//    Scaffold(
//        containerColor = NebengBg,
//        topBar = {
//            TopAppBar(
//                title = { Text("Pesanan", fontWeight = FontWeight.SemiBold) },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
//            )
//        }
//    ) { padding ->
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            HistoryTopTabsM3(selectedTab, onTabSelected = { selectedTab = it })
//            Spacer(Modifier.height(8.dp))
//
//            CategoryChipsRowM3(
//                vehicleTypes = VehicleType.entries,
//                selectedVehicleType = selectedVehicleType,
//                selectedStatus = selectedStatus,
//                selectedStatusLabel = selectedStatusLabel,
//                onVehicleSelected = { selectedVehicleType = it },
//                onStatusSelected = { status, label ->
//                    selectedStatus = status
//                    selectedStatusLabel = label
//                }
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            // === Filtered data ===
//            val filtered = uiState.historyItems.filter { order ->
//                val matchVehicle = selectedVehicleType == null ||
//                        order.vehicleType == selectedVehicleType
//
//                val matchStatus = selectedStatus?.let { safeStatus ->
//                    order.rideStatus == safeStatus // ✅ enum to enum
//                } ?: true
//
//
//                val matchTab = when (selectedTab) {
//                    0 -> order.bookingStatus == BookingStatus.DITERIMA ||
//                            order.bookingStatus == BookingStatus.DITOLAK
//                    1 -> order.bookingStatus == BookingStatus.PENDING
//                    2 -> false // jadwal belum disediakan
//                    else -> true
//                }
//
//                matchVehicle && matchStatus && matchTab
//            }.map { it.toPresentation() }
//
//            when {
//                uiState.isLoading -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(color = NebengBlue)
//                }
//
//                uiState.errorMessage != null -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(uiState.errorMessage ?: "Terjadi kesalahan", color = Color.Red)
//                }
//
//                filtered.isEmpty() -> Box(
//                    Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Tidak ada pesanan.", color = NebengGray)
//                }
//
//                else -> LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(filtered) { order ->
//                        HistoryItemCard(order, onChangeSchedule)
//                    }
//                }
//            }
//        }
//    }
//}
//
///* ================================================================
// * 🔹 TABS
// * ================================================================ */
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryTopTabsM3(selectedTab: Int, onTabSelected: (Int) -> Unit) {
//    val tabs = listOf("Riwayat", "Dalam Proses", "Jadwal Pesanan")
//
//    PrimaryTabRow(
//        selectedTabIndex = selectedTab,
//        containerColor = Color.White,
//        indicator = {},
//        divider = { Divider(color = NebengBlue.copy(alpha = 0.15f), thickness = 1.dp) }
//    ) {
//        tabs.forEachIndexed { index, title ->
//            val isSelected = selectedTab == index
//            Tab(
//                selected = isSelected,
//                onClick = { onTabSelected(index) },
//                modifier = Modifier.height(60.dp),
//                text = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(top = 6.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = title,
//                            color = if (isSelected) NebengBlue else NebengGray,
//                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
//                            fontSize = 15.sp,
//                            textAlign = TextAlign.Center,
//                            lineHeight = 18.sp,
//                            overflow = TextOverflow.Ellipsis,
//                            maxLines = 2
//                        )
//                        Box(
//                            modifier = Modifier
//                                .height(3.dp)
//                                .width(60.dp)
//                                .background(
//                                    if (isSelected) NebengBlue else Color.Transparent,
//                                    RoundedCornerShape(2.dp)
//                                )
//                        )
//                    }
//                }
//            )
//        }
//    }
//}
//
///* ================================================================
// * 🔹 CHIPS
// * ================================================================ */
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CategoryChipsRowM3(
//    vehicleTypes: List<VehicleType>,
//    selectedVehicleType: VehicleType?,
//    selectedStatus: RideStatus?,
//    selectedStatusLabel: String,
//    onVehicleSelected: (VehicleType?) -> Unit,
//    onStatusSelected: (RideStatus?, String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        item {
//            Box {
//                Surface(
//                    color = NebengBlue,
//                    border = BorderStroke(1.dp, NebengBlue),
//                    shape = RoundedCornerShape(14.dp),
//                    modifier = Modifier.clickable { expanded = !expanded }
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                    ) {
//                        Text(
//                            text = selectedStatusLabel,
//                            color = Color.White,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//
//                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                    DropdownMenuItem(
//                        text = { Text("Semua Status") },
//                        onClick = {
//                            onStatusSelected(null, "Semua Status")
//                            expanded = false
//                        }
//                    )
//
//                    RideStatus.entries.forEach { status ->
//                        DropdownMenuItem(
//                            text = {
//                                Text(
//                                    text = status.value.replace("_", " ")
//                                        .replaceFirstChar { it.uppercase() },
//                                    fontSize = 14.sp,
//                                    fontWeight = if (selectedStatus == status) FontWeight.Bold else FontWeight.Normal,
//                                    color = if (selectedStatus == status) NebengBlue else Color.Black
//                                )
//                            },
//                            onClick = {
//                                onStatusSelected(status, status.value.replaceFirstChar { it.uppercase() })
//                                expanded = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        item {
//            val isSelected = selectedVehicleType == null
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(null) }
//            ) {
//                Text(
//                    text = "Semua",
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//
//        items(vehicleTypes) { type ->
//            val isSelected = selectedVehicleType == type
//            Surface(
//                color = if (isSelected) NebengBlue else Color.Transparent,
//                border = BorderStroke(1.dp, NebengBlue),
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.clickable { onVehicleSelected(type) }
//            ) {
//                Text(
//                    text = type.value,
//                    color = if (isSelected) Color.White else NebengBlue,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//    }
//}
//
///* ================================================================
// * 🔹 CARD PESANAN
// * ================================================================ */
//@Composable
//private fun HistoryItemCard(
//    data: HistoryItemData,
//    onChangeSchedule: (HistoryItemData) -> Unit
//) {
//    ElevatedCard(
//        onClick = { onChangeSchedule(data) },
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(Modifier.padding(16.dp)) {
//
//            // === Header ===
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    // 🔹 Icon kendaraan
//                    Icon(
//                        imageVector = when (data.category.lowercase()) {
//                            "mobil" -> Icons.Default.DirectionsCar
//                            "motor" -> Icons.Default.TwoWheeler
//                            else -> Icons.Default.LocalShipping
//                        },
//                        contentDescription = null,
//                        tint = NebengBlue,
//                        modifier = Modifier.size(22.dp)
//                    )
//                    Spacer(Modifier.width(6.dp))
//                    Text(
//                        "Nebeng ${data.category}",
//                        color = NebengBlue,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 14.sp
//                    )
//                }
//
//                // 🔹 Status kanan atas
//                Text(
//                    text = when (data.status) {
//                        RideStatus.DIBATALKAN -> "Dibatalkan"
//                        RideStatus.SELESAI -> "Selesai"
//                        RideStatus.PENDING -> "Pending"
//                        RideStatus.DALAM_PERJALANAN -> "Dalam Perjalanan"
//                        else -> "Tidak diketahui"
//                    },
//                    color = when (data.status) {
//                        RideStatus.DIBATALKAN -> Color(0xFFD32F2F)
//                        RideStatus.PENDING -> NebengBlue
//                        RideStatus.DALAM_PERJALANAN -> Color(0xFF1A47B8)
//                        RideStatus.SELESAI -> Color(0xFF1B5E20)
//                        else -> Color.Gray
//                    },
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 13.sp
//                )
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            // === Rute ===
//            Text(
//                buildAnnotatedString {
//                    append("${data.fromCity} ")
//                    withStyle(SpanStyle(color = NebengBlue, fontWeight = FontWeight.SemiBold)) {
//                        append(data.fromTerminal)
//                    }
//                    append("  →  ${data.toCity} ")
//                    withStyle(SpanStyle(color = NebengBlue, fontWeight = FontWeight.SemiBold)) {
//                        append(data.toTerminal)
//                    }
//                },
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 16.sp,
//                color = Color.Black
//            )
//
//            Spacer(Modifier.height(6.dp))
//
//            // === Tanggal & waktu ===
//            Text("${data.dayDate} • ${data.time}", color = Color.Gray, fontSize = 13.sp)
//
//            Spacer(Modifier.height(8.dp))
//
//            // === Detail kendaraan ===
//            Text("${data.vehicle} • ${data.plate}", color = Color.Gray, fontSize = 13.sp)
//
//            Spacer(Modifier.height(12.dp))
//            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
//            Spacer(Modifier.height(12.dp))
////
//            // === Baris bawah (jumlah orang + total harga, sejajar kanan) ===
//            Row(
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // 🔹 Kiri: kosong atau info tambahan (kalau ada)
//                Spacer(modifier = Modifier.width(1.dp))
//
//                // 🔹 Kanan: kolom vertikal berisi jumlah orang + total harga
//                Column(horizontalAlignment = Alignment.End) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            imageVector = Icons.Default.Person,
//                            contentDescription = null,
//                            tint = Color.Gray,
//                            modifier = Modifier.size(18.dp)
//                        )
//                        Spacer(Modifier.width(4.dp))
//                        Text("${data.seats} Orang", color = Color.Gray, fontSize = 13.sp)
//                    }
//                    Spacer(Modifier.height(4.dp))
//                    Row {
//                        Text(
//                            text = "Total Harga : ",
//                            color = Color.Black,
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                        Text(
//                            text = data.totalPrice,
//                            color = Color(0xFF002F6C),
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//
//        }
//    }
//}
//
///* ================================================================
// * 🔹 PREVIEW (Dummy)
// * ================================================================ */
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//private fun PreviewHistoryOrderCustomerScreenUi() {
//    val sample = HistoryOrderUiState(
//        currentUser = Auth(1, "Riiha", "riiha", "r@demo.com", "1234", "customer"),
//        historyItems = listOf(
//            HistoryOrderItem(
//                bookingId = 1,
//                bookingCode = "NB123",
//                createdAt = "2025-11-09T10:00:00",
//                departureTerminalId = 2,
//                arrivalTerminalId = 4,
//                seatsReserved = 2,
//                totalPrice = 120000,
//                bookingStatus = BookingStatus.DITERIMA,
//                vehicleType = VehicleType.MOBIL,
//                rideStatus = RideStatus.PENDING,
//                driverName = "Tanjirō"
//            ),
//            HistoryOrderItem(
//                bookingId = 2,
//                bookingCode = "NB124",
//                createdAt = "2025-11-10T08:30:00",
//                departureTerminalId = 4,
//                arrivalTerminalId = 3,
//                seatsReserved = 1,
//                totalPrice = 80000,
//                bookingStatus = BookingStatus.PENDING,
//                vehicleType = VehicleType.MOTOR,
//                rideStatus = RideStatus.PENDING,
//                driverName = null // untuk munculkan warning
//            )
//        )
//    )
//    MaterialTheme {
//        HistoryOrderCustomerScreenUi(uiState = sample)
//    }
//}

/* ================================================================
 * 🎨 Warna khas Nebeng
 * ================================================================ */
private val NebengBlue = Color(0xFF002F6C)
private val NebengGray = Color(0xFF6B7280)
private val NebengBg = Color(0xFFF8F9FD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryOrderCustomerScreenUi(
    uiState: HistoryOrderUiState,
    onBack: () -> Unit = {},
    onChangeSchedule: (HistoryOrderItem) -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedVehicleType by remember { mutableStateOf<VehicleType?>(null) }
    var selectedStatus by remember { mutableStateOf<RideStatus?>(null) }
    var selectedStatusLabel by remember { mutableStateOf("Semua Status") }

    Scaffold(
        containerColor = NebengBg,
        topBar = {
            TopAppBar(
                title = { Text("Pesanan", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HistoryTopTabsM3(selectedTab, onTabSelected = { selectedTab = it })
            Spacer(Modifier.height(8.dp))

            CategoryChipsRowM3(
                vehicleTypes = VehicleType.entries,
                selectedVehicleType = selectedVehicleType,
                selectedStatus = selectedStatus,
                selectedStatusLabel = selectedStatusLabel,
                onVehicleSelected = { selectedVehicleType = it },
                onStatusSelected = { status, label ->
                    selectedStatus = status
                    selectedStatusLabel = label
                }
            )

            Spacer(Modifier.height(8.dp))

            // === Filtered data ===
            val filtered = uiState.historyItems.filter { order ->
                val matchVehicle = selectedVehicleType == null || order.vehicleType == selectedVehicleType
                val matchStatus = selectedStatus == null || order.rideStatus == selectedStatus
                val matchTab = when (selectedTab) {
                    0 -> order.bookingStatus == BookingStatus.DITERIMA ||
                            order.bookingStatus == BookingStatus.DITOLAK
                    1 -> order.bookingStatus == BookingStatus.PENDING
                    2 -> false
                    else -> true
                }
                matchVehicle && matchStatus && matchTab
            }

            when {
                uiState.isLoading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = NebengBlue)
                }

                uiState.errorMessage != null -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.errorMessage, color = Color.Red)
                }

                filtered.isEmpty() -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada pesanan.", color = NebengGray)
                }

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { order ->
                        HistoryItemCard(order, onChangeSchedule)
                    }
                }
            }
        }
    }
}

/* ================================================================
 * 🔹 TABS
 * ================================================================ */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopTabsM3(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Riwayat", "Dalam Proses", "Jadwal Pesanan")

    PrimaryTabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.White,
        indicator = {},
        divider = { Divider(color = NebengBlue.copy(alpha = 0.15f), thickness = 1.dp) }
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTab == index
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                modifier = Modifier.height(60.dp),
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) NebengBlue else NebengGray,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                        Box(
                            modifier = Modifier
                                .height(3.dp)
                                .width(60.dp)
                                .background(
                                    if (isSelected) NebengBlue else Color.Transparent,
                                    RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }
            )
        }
    }
}

/* ================================================================
 * 🔹 CHIPS
 * ================================================================ */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipsRowM3(
    vehicleTypes: List<VehicleType>,
    selectedVehicleType: VehicleType?,
    selectedStatus: RideStatus?,
    selectedStatusLabel: String,
    onVehicleSelected: (VehicleType?) -> Unit,
    onStatusSelected: (RideStatus?, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box {
                Surface(
                    color = NebengBlue,
                    border = BorderStroke(1.dp, NebengBlue),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.clickable { expanded = !expanded }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = selectedStatusLabel,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Semua Status") },
                        onClick = {
                            onStatusSelected(null, "Semua Status")
                            expanded = false
                        }
                    )

                    RideStatus.entries.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = status.value.replace("_", " ")
                                        .replaceFirstChar { it.uppercase() },
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedStatus == status) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedStatus == status) NebengBlue else Color.Black
                                )
                            },
                            onClick = {
                                onStatusSelected(status, status.value.replaceFirstChar { it.uppercase() })
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            val isSelected = selectedVehicleType == null
            Surface(
                color = if (isSelected) NebengBlue else Color.Transparent,
                border = BorderStroke(1.dp, NebengBlue),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.clickable { onVehicleSelected(null) }
            ) {
                Text(
                    text = "Semua",
                    color = if (isSelected) Color.White else NebengBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }

        items(vehicleTypes) { type ->
            val isSelected = selectedVehicleType == type
            Surface(
                color = if (isSelected) NebengBlue else Color.Transparent,
                border = BorderStroke(1.dp, NebengBlue),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.clickable { onVehicleSelected(type) }
            ) {
                Text(
                    text = type.value,
                    color = if (isSelected) Color.White else NebengBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}

/* ================================================================
 * 🔹 CARD PESANAN
 * ================================================================ */
@Composable
private fun HistoryItemCard(
    data: HistoryOrderItem,
    onChangeSchedule: (HistoryOrderItem) -> Unit
) {
    ElevatedCard(
        onClick = { onChangeSchedule(data) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = when (data.vehicleType) {
                            VehicleType.MOBIL -> Icons.Default.DirectionsCar
                            VehicleType.MOTOR -> Icons.Default.TwoWheeler
                            VehicleType.BARANG -> Icons.Default.LocalShipping
                            VehicleType.BARANG_TRANSPORTASI_UMUM -> Icons.Default.LocalShipping
                            VehicleType.UNKNOWN -> TODO()
                        },
                        contentDescription = null,
                        tint = NebengBlue,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Nebeng ${data.vehicleType.value}",
                        color = NebengBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = when (data.rideStatus) {
                        RideStatus.DIBATALKAN -> "Dibatalkan"
                        RideStatus.SELESAI -> "Selesai"
                        RideStatus.DALAM_PERJALANAN -> "Dalam Perjalanan"
                        else -> "Pending"
                    },
                    color = when (data.rideStatus) {
                        RideStatus.DIBATALKAN -> Color(0xFFD32F2F)
                        RideStatus.DALAM_PERJALANAN -> Color(0xFF1A47B8)
                        RideStatus.SELESAI -> Color(0xFF1B5E20)
                        else -> NebengBlue
                    },
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = buildString {
                    append(data.departureTerminalName.ifBlank { "Terminal Keberangkatan" })
                    append(" → ")
                    append(data.arrivalTerminalName.ifBlank { "Terminal Tujuan" })
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )

            // ====== Jalur (Rute) ======
            Spacer(Modifier.height(6.dp))

            Text("${data.createdAt.take(10)} • ${data.createdAt.takeLast(8)}", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))

            Text(data.driverName ?: "Tanpa Driver", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(12.dp))
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(1.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("${data.seatsReserved} Orang", color = Color.Gray, fontSize = 13.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row {
                        Text("Total Harga : ", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("Rp${data.totalPrice}", color = NebengBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/* ================================================================
 * 🔹 PREVIEW (Dummy)
 * ================================================================ */
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewHistoryOrderCustomerScreenUi() {
    val sample = HistoryOrderUiState(
        currentUser = Auth(1, "Riiha", "riiha", "r@demo.com", "1234", "customer"),
        historyItems = listOf(
            HistoryOrderItem(
                bookingId = 1,
                bookingCode = "NB123",
                createdAt = "2025-11-09T10:00:00",
                departureTerminalId = 2,
                arrivalTerminalId = 4,
                seatsReserved = 2,
                totalPrice = 120000,
                bookingStatus = BookingStatus.DITERIMA,
                vehicleType = VehicleType.MOBIL,
                rideStatus = RideStatus.PENDING,
                driverName = "Tanjirō",
                customerId = 1,
                customerName = "Customer 1",
                averageRating = 4.5f
            ),
            HistoryOrderItem(
                bookingId = 2,
                bookingCode = "NB124",
                createdAt = "2025-11-10T08:30:00",
                departureTerminalId = 4,
                arrivalTerminalId = 3,
                seatsReserved = 1,
                totalPrice = 80000,
                bookingStatus = BookingStatus.PENDING,
                vehicleType = VehicleType.MOTOR,
                rideStatus = RideStatus.PENDING,
                driverName = null,
                customerId = 1,
                customerName = "Customer 1",
                averageRating = 4.5f
            )
        )
    )
    MaterialTheme {
        HistoryOrderCustomerScreenUi(uiState = sample)
    }
}
