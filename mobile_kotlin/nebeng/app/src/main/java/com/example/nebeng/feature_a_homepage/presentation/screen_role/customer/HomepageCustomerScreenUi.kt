package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebeng.R
import com.example.nebeng.feature_a_homepage.domain.model.nebeng_motor.customer.HomepageUser
import com.example.nebeng.feature_auth.domain.model.Auth
import com.example.nebeng.feature_a_homepage.presentation.HomepageUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomepageCustomerScreenUi(
    state: HomepageUiState,
    onMenuMotorClick: () -> Unit = {},
    onMenuOpenMapClick:() -> Unit = {}
) {
    val user = state.currentUser
    val points = state.points

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F9FD))
            .padding(bottom = 80.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1877F2), Color(0xFF3B8DFE))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text("Selamat Siang,", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                Text(
                    user?.name ?: "Customer",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            IconButton(
                onClick = { /* TODO: notif */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 56.dp, end = 32.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifikasi", tint = Color.White)
            }
        }

        // Card poin
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .offset(y = (-40).dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Poin",
                    modifier = Modifier.size(42.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Poin saya", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = points.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // ===== MENU ICONS =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuItem(
                iconRes = R.drawable.ic_motor,
                label = "Motor",
                modifier = Modifier
                    .weight(1f)
                    .clickable { onMenuMotorClick() }
            )
//            MenuItem(
//                iconRes = R.drawable.ic_mobil,
//                label = "Mobil",
//                modifier = Modifier
//                    .weight(1f)
////                    .clickable { onMenuMotorClick() }
//            )
            MenuItem(
                iconRes = R.drawable.ic_mobil,
                label = "MAP",
                modifier = Modifier
                    .weight(1f)
                    .clickable { onMenuOpenMapClick() }   // 🔥 Go to Map Screen
            )
            MenuItem(R.drawable.ic_barang, "Barang", modifier = Modifier.weight(1f))
            MenuItem(R.drawable.ic_transport, "Barang \n(Transportasi Umum)", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // ===== BANNER =====
        BannerSlider(
            banners = listOf(
                R.drawable.banner_nebeng,
                R.drawable.banner_nebeng,
                R.drawable.banner_nebeng
            )
        )

        Spacer(Modifier.height(24.dp))

        // ===== TUJUAN POPULER =====
        Text(
            text = "Tujuan Populer",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            text = "Berikut adalah kota-kota yang populer!",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                listOf(
                    "Jakarta" to R.drawable.img_jakarta,
                    "Bandung" to R.drawable.img_jakarta
                )
            ) { (city, image) ->
                PopularCityCard(city = city, imageRes = image)
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun MenuItem(iconRes: Int, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF2FF)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier.height(56.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            )
        }
    }
}

@Composable
fun PopularCityCard(city: String, imageRes: Int) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = city,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0x99000000))
                    )
                )
        )
        Text(
            text = city,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
    }
}

@Composable
fun BannerSlider(
    banners: List<Int>,
    autoScrollInterval: Long = 3000L
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll coroutine
    LaunchedEffect(pagerState.currentPage) {
        delay(autoScrollInterval)
        val nextPage = (pagerState.currentPage + 1) % banners.size
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        HorizontalPager(state = pagerState) { page ->
            Image(
                painter = painterResource(id = banners[page]),
                contentDescription = "Banner ${page + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // ===== DOTS INDICATOR =====
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(banners.size) { index ->
                val isSelected = pagerState.currentPage == index

                val dotWidth by animateDpAsState(
                    targetValue = if (isSelected) 20.dp else 8.dp,
                    label = ""
                )
                val dotColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF1877F2) else Color.LightGray,
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(8.dp)
                        .width(dotWidth)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewHomepageCustomerScreenUi() {
    MaterialTheme {
        HomepageCustomerScreenUi(
            state = HomepageUiState(
                currentUser = HomepageUser(
                    userId = 1,
                    customerId  = 1,
                    userType = "customer",
                    name = "Nadya Amalya",
                    username = "nadya",
//                    email = "nadya@mail.com",
                    token = ""
                ),
                points = 120
            )
        )
    }
}