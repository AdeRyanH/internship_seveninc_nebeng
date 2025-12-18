package com.example.nebeng.core.gps

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class DriverLocationProvider(
    private val context: Context
) {

    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Pair<Double, Double> =
        suspendCancellableCoroutine { cont ->
            fusedClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(
                            Pair(location.latitude, location.longitude),
                            null
                        )
                    } else {
                        cont.resumeWithException(
                            IllegalStateException("Location null")
                        )
                    }
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
}
