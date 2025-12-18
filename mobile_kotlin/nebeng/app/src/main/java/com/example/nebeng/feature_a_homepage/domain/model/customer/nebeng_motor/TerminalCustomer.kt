package com.example.nebeng.feature_a_homepage.domain.model.customer.nebeng_motor

import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.TerminalType

data class TerminalCustomer(
    val id: Int,
    val name: String,
    val terminalFullAddress: String,
    val terminalRegencyId: Int,
    val terminalLongitude: Double,
    val terminalLatitude: Double,
    val publicTerminalSubtype: PublicTerminalSubtype,
    val terminalType: TerminalType,
    val regencyName: String,
) {
    companion object {
        fun empty() = TerminalCustomer(
            id = 0,
            name = "",
            terminalFullAddress = "",
            terminalRegencyId = 0,
            terminalLongitude = 0.0,
            terminalLatitude = 0.0,
            publicTerminalSubtype = PublicTerminalSubtype.TERMINAL_BIS,
            terminalType = TerminalType.PUBLIC,
            regencyName = ""
        )
    }
}
