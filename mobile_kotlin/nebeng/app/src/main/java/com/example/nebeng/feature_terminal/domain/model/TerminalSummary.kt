package com.example.nebeng.feature_terminal.domain.model

import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.TerminalType

data class TerminalSummary(
    val id: Int,
    val name: String,
    val terminalType: TerminalType,
    val publicTerminalSubtype: PublicTerminalSubtype,
    val provinceId: Int,
    val regencyId: Int,
    val districtId: Int,
    val fullAddress: String,
    val longitude: Double,
    val latitude: Double,
    val createdAt: String,
    val updatedAt: String,
    val regencyName: String,
)
