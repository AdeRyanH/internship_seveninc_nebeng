package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_08

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.core.utils.BookingStatus
import com.example.nebeng.core.utils.PaymentStatus
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerTransactionCustomer
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.TerminalCustomer
import com.example.nebeng.feature_a_homepage.domain.session.customer.nebeng_motor.BookingSession
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRideMotorPaymentSuccessScreen(
    session: BookingSession,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val transaction = session.transaction ?: return
    val booking = session.booking ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FD))
    ) {

        // 🔵 TOP APP BAR
        // 🔵 TOP APP BAR
        TopAppBar(
            title = {
                Text(
                    "Pembayaran",
                    color = Color.White,   // warna teks judul putih
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left_24),
                        contentDescription = null,
                        tint = Color.White   // icon juga putih
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF0F3D82)   // background TopAppBar biru
            )
        )

        // ✅ SCROLLABLE CONTENT
        Column(
            modifier = Modifier
                .weight(1f)               // ⬅️ ambil sisa layar
                .verticalScroll( rememberScrollState())
        ) {
            Spacer(Modifier.height(18.dp))

            // 🎉 PAYMENT SUCCESS ICON + TITLE
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star), // ikon ilustrasi
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(78.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "Pembayaran Berhasil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            // 📌 CARD DETAIL PEMBAYARAN
            PaymentSummaryCard(session = session)

            Spacer(Modifier.height(18.dp))

            // 🧭 CARD ROUTE RIDE
            RideInfoCard(
                session = session,
                bookingCode = booking.bookingCode
            )

            Spacer(Modifier.weight(1f))

            // 🔵 BUTTON LANJUT — BAWAH FIX
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F3D82))
            ) {
                Text(
                    "Lanjut",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun PaymentSummaryCard(
    session: BookingSession
) {
    val transaction = session.transaction ?: return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE6E6E6))
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {

            Text("Rincian Pembayaran", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(18.dp))

            PaymentRow("Tipe Pembayaran", session.selectedPaymentMethod?.bankName ?: "-")
            PaymentRow("Tanggal", transaction.transactionDate)
            PaymentRow("No Transaksi", transaction.midtransOrderId)

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            PaymentRow("Biaya Per Penebeng", transaction.totalAmount.toString())
            PaymentRow("Biaya Admin", "Rp 15.000,00")

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            PaymentRow(
                label = "Total",
                value = formatRupiah(transaction.totalAmount),
                valueColor = Color(0xFF0FA958),
                bold = true
            )
        }
    }
}

@Composable
private fun PaymentRow(
    label: String,
    value: String,
    valueColor: Color = Color.Black,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(
            value,
            color = valueColor,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun RideInfoCard(
    session: BookingSession,
    bookingCode: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color(0xFFE1E3E6)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text("04 Januari 2025 | 13.45 - 18.45", fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(18.dp))

            RouteRow(
                departure = session.selectedDepartureTerminal,
                arrival = session.selectedArrivalTerminal
            )

            Spacer(Modifier.height(18.dp))

            Divider(color = Color(0xFFE8E8E8))
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("No Pemesanan:", fontWeight = FontWeight.Medium)
                Text(bookingCode ?: "-", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun RouteRow(
    departure: TerminalCustomer?,
    arrival: TerminalCustomer?
) {
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 4.dp)
                .width(38.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(16.dp).background(Color(0xFF2ECC71), RoundedCornerShape(50))
            )
            Box(
                modifier = Modifier.weight(1f).width(2.dp).background(Color(0xFFD0D4D8))
            )
            Box(
                modifier = Modifier.size(16.dp).background(Color(0xFFFF6E42), RoundedCornerShape(50))
            )
        }

        Spacer(Modifier.width(10.dp))

        Column {
            Text(departure?.name ?: "-", fontWeight = FontWeight.Bold)
            Text(departure?.terminalFullAddress.orEmpty(), fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(10.dp))
            Text(arrival?.name ?: "-", fontWeight = FontWeight.Bold)
            Text(arrival?.terminalFullAddress.orEmpty(), fontSize = 13.sp, color = Color.Gray)
        }
    }
}

fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return formatter.format(amount)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPassengerRideMotorPaymentSuccessScreen() {

    val previewSession = BookingSession(

        // =========================
        // PAYMENT METHOD (USER CHOICE)
        // =========================
        selectedPaymentMethod = com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PaymentMethodCustomer(
            idPaymentMethod = 1,
            bankName = "QRIS",
            accountName = "PT Nebeng Indonesia",
            accountNumber = "0000000000"
        ),

        // =========================
        // TRANSACTION (BACKEND FINAL)
        // =========================
        transaction = PassengerTransactionCustomer(
            idPassengerTransaction = 1,
            transactionDate = "2025-01-04T13:45:00",
            transactionCode = "TRX-001",
            midtransTransactionId = "MID-123456",
            paymentStatus = PaymentStatus.DITERIMA,
            createdAt = "2025-01-04T13:45:00",
            paymentProofImg = null,
            creditUsed = 0,
            paymentMethodId = 1,
            paymentType = "qris",
            updatedAt = "2025-01-04T13:45:00",
            totalAmount = 65000,
            midtransOrderId = "INV/20250104/123456789",
            paymentExpiredAt = "2025-01-04T18:45:00",
            passengerRideBookingId = 1,
            vaNumber = "",
            customerId = 1
        ),

        // =========================
        // BOOKING (RECEIPT)
        // =========================
        booking = com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.PassengerRideBookingCustomer(
            idBooking = 1,
            passengerRideId = 1,
            customerId = 1,
            createdAtBooking = "2025-01-04T13:30:00",
            bookingCode = "FR-2345678997543234",
            totalPrice = 65000,
            bookingStatus = BookingStatus.DITERIMA,
            seatsReservedBooking = 1
        ),

        // =========================
        // TERMINALS
        // =========================
        selectedDepartureTerminal = TerminalCustomer(
            id = 1,
            name = "Yogyakarta",
            terminalFullAddress = "Patehan, Kecamatan Kraton, Kota Yogyakarta",
            terminalRegencyId = 3401,
            terminalLongitude = 110.3647,
            terminalLatitude = -7.8014,
            publicTerminalSubtype = com.example.nebeng.core.utils.PublicTerminalSubtype.TERMINAL_BIS,
            terminalType = com.example.nebeng.core.utils.TerminalType.PUBLIC,
            regencyName = "Kota Yogyakarta"
        ),

        selectedArrivalTerminal = TerminalCustomer(
            id = 2,
            name = "Purwokerto",
            terminalFullAddress = "Alun-alun Purwokerto",
            terminalRegencyId = 3302,
            terminalLongitude = 109.2396,
            terminalLatitude = -7.4266,
            publicTerminalSubtype = com.example.nebeng.core.utils.PublicTerminalSubtype.TERMINAL_BIS,
            terminalType = com.example.nebeng.core.utils.TerminalType.PUBLIC,
            regencyName = "Kab. Banyumas"
        )
    )

    PassengerRideMotorPaymentSuccessScreen(
        session = previewSession,
        onBack = {},
        onNext = {}
    )
}

