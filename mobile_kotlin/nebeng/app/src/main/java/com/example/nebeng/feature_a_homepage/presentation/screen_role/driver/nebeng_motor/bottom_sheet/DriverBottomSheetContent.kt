package com.example.nebeng.feature_a_homepage.presentation.screen_role.driver.nebeng_motor.bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideState
import com.example.nebeng.feature_a_homepage.domain.session.driver.nebeng_motor.DriverRideUiState

@Composable
fun DriverBottomSheetContent(
    uiState: DriverRideState,
    onPrimaryAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        PassengerInfoCard()

        Spacer(Modifier.height(20.dp))

        DestinationHeader(
            onDetailClick = {
                // nanti bisa open detail perjalanan
            }
        )

        Spacer(Modifier.height(12.dp))

        DestinationAddress()

        Spacer(Modifier.height(24.dp))

        PrimaryActionButton(
            uiState = uiState,
            onClick = onPrimaryAction
        )
    }
}

@Composable
private fun PassengerInfoCard() {
    Card(
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "Ema Ariska",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { /* call */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = null,
                    tint = Color(0xFF0F3D82)
                )
            }

            IconButton(onClick = { /* chat */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_black_24),
                    contentDescription = null,
                    tint = Color(0xFF0F3D82)
                )
            }
        }
    }
}

@Composable
private fun DestinationHeader(
    onDetailClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Menuju Titik Tujuan",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        OutlinedButton(
            onClick = onDetailClick,
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)
        ) {
            Text("Detail", fontSize = 12.sp)
        }
    }
}

@Composable
private fun DestinationAddress() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location_24),
            contentDescription = null,
            tint = Color(0xFF0F3D82),
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = "Alun-alun Kidul Yogyakarta",
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Patehan, Kecamatan Kraton, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55133",
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun PrimaryActionButton(
    uiState: DriverRideState,
    onClick: () -> Unit
) {
    val label = when (uiState) {
        DriverRideState.NOT_STARTED -> "Menuju titik Tujuan"
        DriverRideState.ON_THE_WAY  -> "Perjalanan Selesai"
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0F3D82)
        )
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

