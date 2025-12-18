package com.example.nebeng.feature_a_homepage.presentation.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

/**
 * Hitung sisa waktu pembayaran dari paymentExpiredAt (ISO / backend string)
 * TIDAK BOLEH return null
 */
@RequiresApi(Build.VERSION_CODES.O)
fun calculateRemainingDuration(
    expiredAt: String,
    zoneId: ZoneId = ZoneId.systemDefault()
): Duration {
    return try {
        val expiredInstant = Instant.parse(expiredAt)
        val nowInstant = Instant.now()

        if (expiredInstant.isBefore(nowInstant)) {
            Duration.ZERO
        } else {
            Duration.between(nowInstant, expiredInstant)
        }
    } catch (e: Exception) {
        Duration.ZERO
    }
}