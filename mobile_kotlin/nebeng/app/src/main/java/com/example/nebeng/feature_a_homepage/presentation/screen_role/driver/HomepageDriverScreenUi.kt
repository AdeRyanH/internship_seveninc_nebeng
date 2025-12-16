package com.example.nebeng.feature_a_homepage.presentation.screen_role.driver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.feature_auth.domain.model.Auth
import com.example.nebeng.feature_a_history_order.presentation.support_for_present.model.HistoryItemData
import com.example.nebeng.feature_a_homepage.presentation.HomepageUiState

//@Composable
//fun HomepageDriverScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Homepage Driver")
//    }
//}
//@Composable
//fun HomepageDriverScreenUi(
//    navController: NavHostController,
//    viewModel: HomepageViewModel = hiltViewModel()
//) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Driver Homepage — Coming Soon", color = Color.Gray)
//    }
//}
//
//
//@Composable
//fun HomepageDriverScreenUi(
//    state: HomepageUiState,
//    onVerifyClicked: () -> Unit = {}
//) {
//    val user = state.currentUser
//    val orders = state.historyItems
//    val isVerified = user?.isVerified == true // tambahkan properti ini nanti dari Auth domain
//    val hasOrders = orders.isNotEmpty()
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF8F9FD))
//    ) {
//        item {
//            // ======= HEADER =======
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .background(
//                        Brush.verticalGradient(
//                            listOf(Color(0xFF1877F2), Color(0xFF3B8DFE))
//                        )
//                    )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp, vertical = 32.dp)
//                ) {
//                    Text("Halo,", color = Color.White, style = MaterialTheme.typography.bodyLarge)
//                    Text(
//                        user?.name ?: "Driver",
//                        color = Color.White,
//                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
//                    )
//                }
//
//                IconButton(
//                    onClick = { /* TODO: notif */ },
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(top = 56.dp, end = 32.dp)
//                        .size(32.dp)
//                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
//                ) {
//                    Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", tint = Color.White)
//                }
//            }
//
//            // ======= CARD PENDAPATAN =======
//            Card(
//                modifier = Modifier
//                    .padding(horizontal = 24.dp)
//                    .offset(y = (-40).dp)
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.cardElevation(6.dp)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text("Pendapatan Hari Ini", style = MaterialTheme.typography.bodyMedium)
//                    Spacer(Modifier.height(8.dp))
//                    Text("-", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
//
//                    if (!isVerified) {
//                        Spacer(Modifier.height(12.dp))
//                        Card(
//                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
//                            shape = RoundedCornerShape(12.dp),
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Column(modifier = Modifier.padding(12.dp)) {
//                                Text(
//                                    text = "Kamu belum melakukan verifikasi dokumen",
//                                    color = Color(0xFFD32F2F),
//                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
//                                )
//                                Spacer(Modifier.height(4.dp))
//                                Text(
//                                    text = "Ayo verifikasi sekarang untuk mulai memberi tebengan!",
//                                    color = Color(0xFFD32F2F),
//                                    style = MaterialTheme.typography.bodySmall
//                                )
//                                Spacer(Modifier.height(8.dp))
//                                Button(
//                                    onClick = onVerifyClicked,
//                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C3BDB)),
//                                    modifier = Modifier.align(Alignment.End)
//                                ) {
//                                    Text("Verifikasi Sekarang")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            // ======= LAYANAN MITRA =======
//            Text(
//                text = "Layanan Mitra",
//                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier.padding(horizontal = 24.dp)
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 24.dp, vertical = 16.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                PartnerMenuItem(R.drawable.ic_motor, "Nebeng Motor")
//                PartnerMenuItem(R.drawable.ic_mobil, "Nebeng Mobil")
//                PartnerMenuItem(R.drawable.ic_barang, "Nebeng Barang")
//                PartnerMenuItem(R.drawable.ic_transport, "Titip Barang")
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            // ======= TEBENGAN AKAN DATANG =======
//            Text(
//                text = "Tebengan Akan Datang",
//                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier.padding(horizontal = 24.dp)
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            if (!isVerified) {
//                // CARD BELUM VERIFIKASI
//                Box(
//                    modifier = Modifier
//                        .padding(horizontal = 24.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(Color.White)
//                        .padding(24.dp)
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_search), // pakai ikon kaca pembesar
//                            contentDescription = null,
//                            tint = Color.LightGray,
//                            modifier = Modifier.size(36.dp)
//                        )
//                        Spacer(Modifier.height(12.dp))
//                        Text(
//                            text = "Tidak Ada Perjalanan",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Gray
//                            )
//                        )
//                        Spacer(Modifier.height(6.dp))
//                        Text(
//                            text = "Silahkan verifikasi dokumen Anda terlebih dahulu",
//                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            } else if (!hasOrders) {
//                Text(
//                    text = "Belum ada tebengan yang dijadwalkan",
//                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
//                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
//                )
//            } else {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    orders.forEach { order ->
//                        DriverUpcomingOrderCard(order)
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(32.dp))
//        }
//    }
//}


// ==========================================
//@Composable
//private fun PartnerMenuItem(iconRes: Int, label: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.weight(1f)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(56.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFEAF2FF)),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(painter = painterResource(id = iconRes), contentDescription = label)
//        }
//        Spacer(Modifier.height(8.dp))
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
//            textAlign = TextAlign.Center
//        )
//    }
//}

// ========================================
//@Composable
//private fun RowScope.PartnerMenuItem(iconRes: Int, label: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.weight(1f)
//    ) {
//        Box(
//            modifier = Modifier
//                .size(56.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFEAF2FF)),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(painter = painterResource(id = iconRes), contentDescription = label)
//        }
//        Spacer(Modifier.height(8.dp))
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//private fun DriverUpcomingOrderCard(order: HistoryItemData) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(2.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
////                text = order.route,
//                text = "${order.fromCity} → ${order.toCity}",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF0F1C43)
//                )
//            )
//            Spacer(Modifier.height(4.dp))
////            Text(order.title, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
//            Text(
//                text = "${order.dayDate}  •  ${order.time}",
//                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
//            )
//
//            Spacer(Modifier.height(8.dp))
//            Box(
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(Color(0xFFFFF1E6))
//                    .padding(horizontal = 12.dp, vertical = 4.dp)
//            ) {
//                Text(
//                    text = order.status,
//                    style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFE27A00))
//                )
//            }
//        }
//    }
//}
//
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////fun PreviewHomepageDriverScreenUi_Unverified() {
////    MaterialTheme {
////        HomepageDriverScreenUi(
////            state = HomepageUiState(
////                currentUser = Auth(
////                    id = 1,
////                    name = "Kamado Tanjirō",
////                    username = "tanjirou",
////                    email = "t@demo.com",
////                    user_type = "driver"
////                ),
////                historyItems = emptyList()
////            )
////        )
////    }
////}
////
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////fun PreviewHomepageDriverScreenUi_Verified() {
////    MaterialTheme {
////        HomepageUiState(
////            state = HistoryOrderUiState(
////                currentUser = Auth(id = 1, name = "Kamado Tanjirō", username = "tanjirou", email = "t@demo.com", user_type = "driver"),
////                historyItems = listOf(
////                    HistoryItemData("XZH80BV", "Nebeng Motor", "Yogyakarta → Purwokerto", "Rp 90.000", "Kosong", "Motor")
////                )
////            )
////        )
////    }
////}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomepageDriverScreenUi_Unverified() {
//    MaterialTheme {
//        HomepageDriverScreenUi(
//            state = HomepageUiState(
//                currentUser = Auth(
//                    id = 1,
//                    name = "Kamado Tanjirō",
//                    username = "tanjirou",
//                    email = "t@demo.com",
//                    user_type = "driver"
//                ),
//                historyItems = emptyList() // ✅ sudah tersedia sekarang
//            )
//        )
//    }
//}
//
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////fun PreviewHomepageDriverScreenUi_Verified() {
////    MaterialTheme {
////        HomepageDriverScreenUi(
////            state = HomepageUiState(
////                currentUser = Auth(
////                    id = 1,
////                    name = "Kamado Tanjirō",
////                    username = "tanjirou",
////                    email = "t@demo.com",
////                    user_type = "driver"
////                ),
////                historyItems = listOf(
////                    HistoryItemData(
////                        code = "XZH80BV",
////                        title = "Nebeng Motor",
////                        route = "Yogyakarta → Purwokerto",
////                        price = "Rp 90.000",
////                        status = "Kosong",
////                        category = "Motor"
////                    )
////                )
////            )
////        )
////    }
////}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewHomepageDriverScreenUi_Verified() {
//    MaterialTheme {
//        HomepageDriverScreenUi(
//            state = HomepageUiState(
//                currentUser = Auth(
//                    id = 1,
//                    name = "Kamado Tanjirō",
//                    username = "tanjirou",
//                    email = "t@demo.com",
//                    user_type = "driver"
//                ),
//                historyItems = listOf(
//                    HistoryItemData(
//                        code = "XZH80BV",
//                        category = "Motor",
//                        status = "Kosong",
//                        fromCity = "Yogyakarta",
//                        toCity = "Purwokerto",
//                        fromPos = "Pos 1",
//                        toPos = "Pos 2",
//                        dayDate = "Senin, 4 Januari 2025",
//                        time = "09:00",
//                        vehicle = "Beat Street",
//                        plate = "AB 1234 CD",
//                        pax = 1,
//                        totalPrice = "Rp 90.000"
//                    )
//                )
//            )
//        )
//    }
//}

// ===============================================================


@Composable
fun HomepageDriverScreenUi(
    isVerified: Boolean = false,
    onVerifyClicked: () -> Unit = {},
    state: HomepageUiState,
    onMenuMotorClick: () -> Unit = {},
    onMenuBarangClick: () -> Unit = {}
) {
    var verified by remember { mutableStateOf(isVerified) }
    val user = state.currentUser

    Scaffold(containerColor = Color(0xFFF8F9FD)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // =======================
            // 🔹 HEADER
            // =======================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0D47A1))
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Halo,",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Kamado Tanjirō",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notifications_black_24),
                            contentDescription = "Notifikasi",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // =======================
            // 🔹 Pendapatan Hari Ini
            // =======================
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .offset(y = (-24).dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Pendapatan Hari Ini",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = if (verified) "Rp 150.000" else "-",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    if (!verified) {
                        Spacer(Modifier.height(12.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "Kamu belum melakukan verifikasi dokumen",
                                    color = Color(0xFFD32F2F),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Ayo verifikasi sekarang untuk mulai memberi tebengan!",
                                    color = Color(0xFFD32F2F),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .clickable { onVerifyClicked() }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // =======================
            // 🔹 Layanan Mitra
            // =======================
//            Column(Modifier.padding(horizontal = 16.dp)) {
//                Text(
//                    text = "Layanan Mitra",
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
//                )
//                Spacer(Modifier.height(12.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    PartnerMenuItem(R.drawable.ic_motor, "Nebeng Motor")
//                    PartnerMenuItem(R.drawable.ic_mobil, "Nebeng Mobil")
//                    PartnerMenuItem(R.drawable.ic_barang, "Nebeng Barang")
//                    PartnerMenuItem(R.drawable.ic_transport, "Titip Barang")
//                }
//            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PartnerMenuItem(
                    iconRes = R.drawable.ic_motor,
                    label = "Nebeng Motor",
                    onClick = onMenuMotorClick
                )

                PartnerMenuItem(
                    iconRes = R.drawable.ic_mobil,
                    label = "Nebeng Mobil",
                    onClick = {
                        // sementara kosong / coming soon
                    }
                )

                PartnerMenuItem(
                    iconRes = R.drawable.ic_barang,
                    label = "Nebeng Barang",
                    onClick = onMenuBarangClick
                )

                PartnerMenuItem(
                    iconRes = R.drawable.ic_transport,
                    label = "Titip Barang",
                    onClick = {
                        // coming soon
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            // =======================
            // 🔹 Tebengan Akan Datang
            // =======================
            Column(Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tebengan Akan Datang",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    if (verified) {
                        Text(
                            text = "Lihat lebih",
                            color = Color(0xFF0D47A1),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                if (!verified) {
                    // ===== Card belum verifikasi =====
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "Tidak Ada Perjalanan",
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Silahkan verifikasi dokumen Anda terlebih dahulu",
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = { onVerifyClicked() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                            ) {
                                Text("Verifikasi Sekarang")
                            }
                        }
                    }
                } else {
                    // ===== Card sudah verifikasi =====
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Sab, 04 Januari 2025 | 13.45 - 18.45 | Nebeng Motor",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            RideRouteItem(
                                from = "Yogyakarta",
                                fromDetail = "Alun-alun Yogyakarta",
                                to = "Purwokerto",
                                toDetail = "Alun-alun Purwokerto"
                            )
                            Spacer(Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .background(Color(0xFFFFE0B2), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Kosong", color = Color(0xFFF57C00), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.PartnerMenuItem(
    iconRes: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(1f)
            .clickable{ onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF2FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = iconRes), contentDescription = label, tint = Color(0xFF0D47A1))
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RideRouteItem(from: String, fromDetail: String, to: String, toDetail: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0D47A1))
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(from, fontWeight = FontWeight.Bold)
                Text(fromDetail, color = Color.Gray, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD32F2F))
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(to, fontWeight = FontWeight.Bold)
                Text(toDetail, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}
