package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SelectLocationBottomSheet(
//    title: String,
//    locations: List<LocationUiModel>,
//    onSelect: (LocationUiModel) -> Unit,
//    onDismiss: () -> Unit
//) {
//    var searchQuery by remember { mutableStateOf("") }
//
//    ModalBottomSheet(
//        onDismissRequest = onDismiss,
//        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
//        containerColor = Color.White,
//        dragHandle = null
//    ) {
//
//        // ===== TITLE BAR =====
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp, vertical = 14.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                title,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.weight(1f)
//            )
//
//            IconButton(onClick = onDismiss) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_close_24),
//                    contentDescription = null,
//                    tint = Color(0xFF6B7280)
//                )
//            }
//        }
//
//        Spacer(Modifier.height(4.dp))
//
//        // ===== SEARCH FIELD =====
//        TextField(
//            value = searchQuery,
//            onValueChange = { searchQuery = it },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search_24),
//                    contentDescription = null,
//                    tint = Color(0xFF6B7280)
//                )
//            },
//            placeholder = { Text("Masukkan lokasi awal", fontSize = 14.sp) },
//            singleLine = true,
//            colors = TextFieldDefaults.colors(
//                unfocusedContainerColor = Color(0xFFF2F4F7),
//                focusedContainerColor = Color(0xFFF2F4F7),
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent
//            ),
//            shape = RoundedCornerShape(12.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp)
//        )
//
//        Spacer(Modifier.height(18.dp))
//
//        // ===== FILTERING LIST =====
////        val filteredLocations = locations.filter {
////            it.title.contains(searchQuery, ignoreCase = true) ||
////                    it.detail.contains(searchQuery, ignoreCase = true)
////        }
////        val filteredLocations = locations.filter {
////            it.name.contains(searchQuery, ignoreCase = true) ||
////                    it.regency.contains(searchQuery, ignoreCase = true)
////        }
//        // ===== FILTER RESULT =====
//        val filteredLocations = locations.filter {
//            searchQuery.isBlank() ||
//                    it.name.contains(searchQuery, ignoreCase = true) ||
//                    it.fullAddress.contains(searchQuery, ignoreCase = true) ||
//                    it.regency.contains(searchQuery, ignoreCase = true)
//        }
//
//        if (filteredLocations.isEmpty()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 40.dp, bottom = 40.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    "Lokasi tidak ditemukan",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//            }
//        } else {
//            LazyColumn {
//                items(filteredLocations) { location ->
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable { onSelect(location) }
//                            .padding(horizontal = 20.dp, vertical = 14.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_location_24),
//                            contentDescription = null,
//                            tint = Color(0xFF1A47B8),
//                            modifier = Modifier.size(28.dp)
//                        )
//                        Spacer(Modifier.width(14.dp))
//
//                        Column(Modifier.weight(1f)) {
//                            Text(
//                                text = location.name,
//                                fontWeight = FontWeight.SemiBold,
//                                fontSize = 15.sp,
//                                color = Color(0xFF0F1C43)
//                            )
//                            Spacer(Modifier.height(2.dp))
//                            Text(
//                                text = location.fullAddress,
//                                fontSize = 13.sp,
//                                color = Color(0xFF6B7280)
//                            )
//                        }
//                    }
//
//                    Divider(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 20.dp),
//                        color = Color(0xFFE6E6E6)
//                    )
//                }
//                item { Spacer(Modifier.height(20.dp)) }
//            }
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLocationBottomSheet(
    title: String,
    locations: List<LocationUiModel>,
    onSelect: (LocationUiModel) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // 🔍 DEBUG: cek apakah data diterima
    LaunchedEffect(locations) {
        Log.d("BOTTOM_SHEET", "Jumlah lokasi masuk: ${locations.size}")
        locations.forEach {
            Log.d("BOTTOM_SHEET", " - ${it.id} | ${it.name} | ${it.regency}")
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        containerColor = Color.White,
        dragHandle = null
    ) {

        // ===== TITLE =====
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss) {
                Icon(painterResource(id = R.drawable.ic_close_24), contentDescription = null, tint = Color.Gray)
            }
        }

        // ===== SEARCH BAR =====
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it

                // 🔍 DEBUG: Log perubahan query
                Log.d("BOTTOM_SHEET", "Search input: $searchQuery")
            },
            leadingIcon = {
                Icon(painterResource(id = R.drawable.ic_search_24), contentDescription = null, tint = Color.Gray)
            },
            placeholder = { Text("Cari lokasi…", fontSize = 14.sp) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF2F4F7),
                unfocusedContainerColor = Color(0xFFF2F4F7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(16.dp))

        // ===== FILTERING =====
        val filteredLocations = remember(searchQuery, locations) {
            locations.filter {
                searchQuery.isBlank() ||
                        it.name.contains(searchQuery, true) ||
                        it.fullAddress.contains(searchQuery, true) ||
                        it.regency.contains(searchQuery, true)
            }
        }

        // 🔍 DEBUG: log hasil filter
        LaunchedEffect(filteredLocations) {
            Log.d("BOTTOM_SHEET", "Filtered count: ${filteredLocations.size}")
        }

        if (filteredLocations.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Lokasi tidak ditemukan", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            LazyColumn {
                items(filteredLocations) { location ->

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { onSelect(location) }
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_24),
                            contentDescription = null,
                            tint = Color(0xFF1A47B8),
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.width(14.dp))

                        Column(Modifier.weight(1f)) {
                            Text(location.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Spacer(Modifier.height(2.dp))
                            Text(location.fullAddress, fontSize = 13.sp, color = Color.Gray)
                        }
                    }

                    Divider(
                        Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        color = Color(0xFFE6E6E6)
                    )
                }
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewSelectLocationBottomSheet() {
    val sampleList = listOf(
        LocationUiModel(
            id = 1,
            name = "Terminal Giwangan",
            fullAddress = "Jl. Imogiri Timur, Umbulharjo",
            regency = "Yogyakarta"
        ),
        LocationUiModel(
            id = 2,
            name = "Terminal Jombor",
            fullAddress = "Jl. Magelang KM 8, Sleman",
            regency = "Sleman"
        )
    )

    MaterialTheme {
        SelectLocationBottomSheet(
            title = "Pilih Lokasi Awal",
            locations = sampleList,
            onSelect = {},
            onDismiss = {}
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewSelectLocationBottomSheet_Empty() {
    MaterialTheme {
        SelectLocationBottomSheet(
            title = "Pilih Kota atau Pos Awal",
            locations = emptyList(),   // tidak ada data
            onSelect = {},
            onDismiss = {}
        )
    }
}
