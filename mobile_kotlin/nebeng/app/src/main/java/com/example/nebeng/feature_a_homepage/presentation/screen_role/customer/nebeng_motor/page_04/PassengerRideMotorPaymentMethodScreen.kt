package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_04

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.PaymentMethodCustomer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorPaymentMethodScreen(
    paymentMethods: List<PaymentMethodCustomer>,
    onBack: () -> Unit = {},
    onSelect: (PaymentMethodCustomer) -> Unit = {},
    onNext: () -> Unit = {}
) {
    var selectedId by remember { mutableStateOf<Int?>(null) }

    Log.d("UI_PAGE_4", "Loaded paymentMethods=${paymentMethods.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
    ) {

        // ===== TOP BAR =====
        TopAppBar(
            title = {
                Text(
                    "Pilih Pembayaran",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF0F3D82)
            )
        )

        Spacer(Modifier.height(10.dp))

        // ===== PAYMENT METHOD LIST =====
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(paymentMethods) { method ->

                PaymentMethodItem(
                    method = method,
                    selected = selectedId == method.idPaymentMethod,
                    onSelect = {
                        Log.d("UI_PAGE_4", "Selected payment id=${method.idPaymentMethod} name=${method.bankName}")
                        selectedId = method.idPaymentMethod
                        onSelect(method)
                    }
                )

                Spacer(Modifier.height(12.dp))
            }
        }

        Spacer(Modifier.weight(1f))

        // ===== BUTTON LANJUTKAN =====
        Button(
            onClick = {
                Log.d("UI_PAGE_4", "Continue clicked → paymentId=$selectedId")
                onNext()
            },
            enabled = selectedId != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F3D82),
                disabledContainerColor = Color(0xFF98A7C3)
            )
        ) {
            Text(
                text = "Lanjutkan",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PaymentMethodItem(
    method: PaymentMethodCustomer,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE3E6EB))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(R.drawable.qris),
                contentDescription = method.bankName,
                modifier = Modifier.size(44.dp)
            )

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Text(method.bankName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                method.accountName.let {
                    Spacer(Modifier.height(2.dp))
                    Text(it, fontSize = 12.sp, color = Color.Gray)
                }
            }

            RadioButton(
                selected = selected,
                onClick = onSelect
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPaymentMethodScreen() {

    val dummyMethods = listOf(
        PaymentMethodCustomer(
            idPaymentMethod = 1,
            bankName = "BCA",
            accountName = "PT Nebeng Indonesia",
            accountNumber = "1234567890"
        ),
        PaymentMethodCustomer(
            idPaymentMethod = 2,
            bankName = "BRI",
            accountName = "PT Nebeng Indonesia",
            accountNumber = "0987654321"
        ),
        PaymentMethodCustomer(
            idPaymentMethod = 3,
            bankName = "Mandiri",
            accountName = "PT Nebeng Indonesia",
            accountNumber = "111222333"
        )
    )

    PassengerRideMotorPaymentMethodScreen(
        paymentMethods = dummyMethods,
        onBack = {},
        onSelect = {},
        onNext = {}
    )
}
