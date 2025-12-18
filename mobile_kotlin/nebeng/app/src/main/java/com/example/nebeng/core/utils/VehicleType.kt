package com.example.nebeng.core.utils

enum class VehicleType(val value: String) {
    MOTOR("motor"),
    MOBIL("mobil"),
    BARANG("barang"),
    BARANG_TRANSPORTASI_UMUM("barang_ransportasi_umum"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): VehicleType =
            entries.find { it.value.equals(value, ignoreCase = true) } ?: UNKNOWN
    }
}