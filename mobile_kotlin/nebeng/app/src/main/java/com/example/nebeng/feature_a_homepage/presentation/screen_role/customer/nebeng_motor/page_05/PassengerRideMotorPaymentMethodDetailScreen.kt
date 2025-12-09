package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_05

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_04.PassengerRideMotorPaymentMethodModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorPaymentMethodDetailScreen(
    onBack: () -> Unit,
    onPay: () -> Unit,
    orderNumber: String,
    paymentMethod: PassengerRideMotorPaymentMethodModel,
    totalAmount: String
) {
    var showHowToPay by remember { mutableStateOf(false) }
    var termsChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
    ) {

        // ---------- TOP APP BAR ----------
        TopAppBar(
            title = {
                Text("Metode Pembayaran", color = Color.White, fontWeight = FontWeight.SemiBold)
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

            // ---------- NO PEMESANAN ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("No Pemesanan:", fontWeight = FontWeight.Medium)
                Text(orderNumber, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(14.dp))

            // ---------- CARD PAYMENT METHOD ----------
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(Color.White),
                border = BorderStroke(1.dp, Color(0xFFE3E6EB)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = paymentMethod.icon),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(Modifier.width(14.dp))
                    Text(paymentMethod.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.weight(1f))
                    RadioButton(selected = true, onClick = {})
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---------- CARD DETAIL RUTE + BIAYA ----------
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    RouteRow(
                        startTitle = "Yogyakarta · Pos 1",
                        startDetail = "Patehan, Kecamatan Kraton, Kota Yogyakarta 55133",
                        endTitle = "Purwokerto · Pos 2",
                        endDetail = "Alun-alun Purwokerto"
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Biaya", fontWeight = FontWeight.Medium)
                        Text(totalAmount, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F3D82))
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---------- CARD LIHAT CARA PEMBAYARAN ----------
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(Color.White),
                border = BorderStroke(1.dp, Color(0xFFE3E6EB)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { showHowToPay = !showHowToPay }
            ) {
                Column(Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.qris),
                            contentDescription = null,
                            tint = Color(0xFF0F3D82),
                            modifier = Modifier
                                .size(28.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text("Lihat cara pembayaran", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = if (showHowToPay) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                    if (showHowToPay) {
                        Spacer(Modifier.height(14.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("1. Buka BRImo / ATM / M-Banking", fontSize = 13.sp, color = Color.Gray)
                            Text("2. Pilih Menu Pembayaran / VA", fontSize = 13.sp, color = Color.Gray)
                            Text("3. Masukkan nomor Virtual Account", fontSize = 13.sp, color = Color.Gray)
                            Text("4. Periksa total pembayaran", fontSize = 13.sp, color = Color.Gray)
                            Text("5. Selesaikan pembayaran", fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---------- CARD TOTAL PEMBAYARAN ----------
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(Color.White),
                border = BorderStroke(1.dp, Color(0xFFE3E6EB)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(18.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Total Pembayaran", fontWeight = FontWeight.SemiBold)
                    Text(totalAmount, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F3D82))
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------- CHECKBOX SYARAT & KETENTUAN ----------
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = termsChecked, onCheckedChange = { termsChecked = it })
                Text(
                    "Saya telah membaca dan setuju terhadap Syarat dan ketentuan pembelian tiket",
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // ---------- BUTTON BAYAR ----------
            Button(
                onClick = onPay,
                enabled = termsChecked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F3D82),
                    disabledContainerColor = Color(0xFF96A1C2)
                )
            ) {
                Text("Lanjutkan Pembayaran", fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
            }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPaymentMethodDetailScreen() {
    PassengerRideMotorPaymentMethodDetailScreen(
        onBack = {},
        onPay = {},
        orderNumber = "FR-2345678997543234",
        paymentMethod = PassengerRideMotorPaymentMethodModel(
            name = "BRI Virtual Account",
            icon = R.drawable.qris
        ),
        totalAmount = "Rp 50.000,00"
    )
}
