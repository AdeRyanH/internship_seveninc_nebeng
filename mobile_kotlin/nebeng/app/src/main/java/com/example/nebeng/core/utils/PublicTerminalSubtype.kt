package com.example.nebeng.core.utils

enum class PublicTerminalSubtype(val label: String) {
    TERMINAL_BIS("terminal_bis"),
    STASIUN_KERETA("stasiun_kereta"),
    BANDARA("bandara"),
    PELABUHAN("pelabuhan");

    companion object {
        fun fromString(value: String): PublicTerminalSubtype =
            entries.find { it.label.equals(value, ignoreCase = true) } ?: TERMINAL_BIS
    }
}
