package com.example.nebeng.feature_a_homepage.presentation

import com.example.nebeng.feature_a_history_order.presentation.support_for_present.model.HistoryItemData
import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.HomepageUser

data class HomepageUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentUser: HomepageUser? = null,
    val points: Int = 0,
    val historyItems: List<HistoryItemData> = emptyList() // ✅ tambahkan ini
)