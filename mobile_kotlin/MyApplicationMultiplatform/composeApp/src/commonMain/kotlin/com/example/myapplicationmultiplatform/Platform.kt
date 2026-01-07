package com.example.myapplicationmultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform