package com.example.nebeng.core.utils


enum class UserType(val value: String) {
    SUPER_ADMIN("superadmin"),
    ADMIN("admin"),
    FINANCE("finance"),
    CUSTOMER("customer"),
    DRIVER("driver"),
    TERMINAL("terminal");


    companion object {
        fun fromString(value: String): UserType =
            entries.find { it.value.equals(value, ignoreCase = true) } ?: CUSTOMER
    }
}