package com.example.nebeng.feature_a_homepage.presentation.screen_role.driver

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.presentation.HomepageUiState

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
