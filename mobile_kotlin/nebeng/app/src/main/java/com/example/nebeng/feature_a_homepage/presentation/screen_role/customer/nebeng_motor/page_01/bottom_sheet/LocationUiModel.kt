package com.example.nebeng.feature_a_homepage.presentation.screen_role.customer.nebeng_motor.page_01.bottom_sheet

import com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor.TerminalCustomer

data class LocationUiModel(
    val id: Int,
    val name: String,
    val fullAddress: String,
    val regency: String,
    val rawTerminal: TerminalCustomer? = null
)