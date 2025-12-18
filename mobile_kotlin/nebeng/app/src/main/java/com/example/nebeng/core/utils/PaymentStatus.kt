package com.example.nebeng.core.utils

enum class PaymentStatus(val value: String) {
    PENDING("pending"),
    DITERIMA("diterima"),
    DITOLAK("ditolak"),
    CREDITED("credited"),
    EXPIRED("expired");

    companion object {
        fun fromString(value: String): PaymentStatus =
            entries.find { it.value.equals(value, ignoreCase = true) } ?: PENDING

    }
}