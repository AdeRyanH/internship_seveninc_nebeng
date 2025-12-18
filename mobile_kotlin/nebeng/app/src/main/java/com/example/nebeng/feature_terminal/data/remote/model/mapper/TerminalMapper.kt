package com.example.nebeng.feature_terminal.data.remote.model.mapper

import com.example.nebeng.core.utils.PublicTerminalSubtype
import com.example.nebeng.core.utils.TerminalType
import com.example.nebeng.feature_terminal.data.remote.model.dto.DataDto
import com.example.nebeng.feature_terminal.domain.model.Terminal
import com.example.nebeng.feature_terminal.domain.model.TerminalSummary

fun DataDto.toDomain(): Terminal {
    val terminalType = terminalType?.trim()?.let {
        TerminalType.fromString(it)
    } ?: TerminalType.PUBLIC

    val subtype = publicTerminalSubtype?.trim()?.let {
        PublicTerminalSubtype.fromString(it)
    } ?: PublicTerminalSubtype.TERMINAL_BIS

    return Terminal(
        id = id ?: 0,
        name = name.orEmpty(),
        terminalType = terminalType,
        publicTerminalSubtype = subtype,
        provinceId = provinceId ?: 0,
        regencyId = regencyId ?: 0,
        districtId = districtId ?: 0,
        fullAddress = fullAddress.orEmpty(),
        longitude = longitude ?: 0.0,
        latitude = latitude ?: 0.0,
        createdAt = createdAt.orEmpty(),
        updatedAt = updatedAt.orEmpty()
    )
}

fun DataDto.toSummary(): TerminalSummary {
    val terminalType = terminalType?.trim()?.let {
        TerminalType.fromString(it)
    } ?: TerminalType.PUBLIC

    val subtype = publicTerminalSubtype?.trim()?.let {
        PublicTerminalSubtype.fromString(it)
    } ?: PublicTerminalSubtype.TERMINAL_BIS
    return TerminalSummary(
        id = id ?: 0,
        name = name.orEmpty(),
        terminalType = terminalType,
        publicTerminalSubtype = subtype,
        provinceId = provinceId ?: 0,
        regencyId = regencyId ?: 0,
        districtId = districtId ?: 0,
        fullAddress = fullAddress.orEmpty(),
        longitude = longitude ?: 0.0,
        latitude = latitude ?: 0.0,
        createdAt = createdAt.orEmpty(),
        updatedAt = updatedAt.orEmpty(),
        regencyName = regency?.name.orEmpty()
    )
}