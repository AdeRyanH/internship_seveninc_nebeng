package com.example.nebeng.core.utils

enum class TransportType(val value: String) {
    SENDIRI("sendiri"),
    UMUM("umum");

    companion object {
        fun fromString(value: String): TransportType =
            entries.find { it.name.equals(value, ignoreCase = true) } ?: UMUM
    }
}
