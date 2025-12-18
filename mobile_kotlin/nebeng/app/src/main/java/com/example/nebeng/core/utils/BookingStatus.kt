package com.example.nebeng.core.utils

enum class BookingStatus(val value: String) {
    PENDING("pending"),
    DITERIMA("diterima"),
    DITOLAK("ditolak");

    companion object {
        fun fromString(value: String?): BookingStatus =
            entries.find { it.value.equals(value, ignoreCase = true) } ?: PENDING
    }
}