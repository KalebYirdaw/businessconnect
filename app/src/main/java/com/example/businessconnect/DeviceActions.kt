package com.example.businessconnect

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/** Phone, WhatsApp, email and navigation helpers. */
object DeviceActions {

    private fun launch(context: Context, intent: Intent, failMessage: String) {
        try {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (e: Exception) {
            Toast.makeText(context, failMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun call(context: Context, phone: String) {
        val number = phone.filter { it.isDigit() || it == '+' }
        if (number.isBlank()) return
        // ACTION_DIAL opens the dialler - no CALL_PHONE permission needed
        launch(context, Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")), "No phone app found")
    }

    /** Turns 082 123 4567 into 27821234567 (South African numbers). */
    fun toInternationalDigits(phone: String): String {
        val digits = phone.filter { it.isDigit() }
        return when {
            digits.startsWith("00") -> digits.drop(2)
            digits.startsWith("0") -> "27" + digits.drop(1)
            else -> digits
        }
    }

    fun whatsApp(context: Context, phone: String) {
        val digits = toInternationalDigits(phone)
        if (digits.isBlank()) return
        launch(
            context,
            Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$digits")),
            "WhatsApp is not installed"
        )
    }

    fun email(context: Context, address: String) {
        if (address.isBlank()) return
        launch(
            context,
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${address.trim()}")),
            "No email app found"
        )
    }

    /** Opens turn-by-turn directions in Google Maps (or any maps app). */
    fun navigate(context: Context, latitude: Double, longitude: Double) {
        val nav = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$latitude,$longitude"))
            .setPackage("com.google.android.apps.maps")
        try {
            context.startActivity(nav.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (e: Exception) {
            // Google Maps missing: fall back to any app that handles a geo link / browser
            launch(
                context,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude")
                ),
                "No maps app found"
            )
        }
    }
}

object LocationUtils {

    fun hasPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    /**
     * Gets a fresh fix: tries GPS first, then network location, and finally
     * falls back to the last known position. Returns null if nothing works.
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Location? {
        if (!hasPermission(context)) return null

        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
            .filter { manager.isProviderEnabled(it) }

        for (provider in providers) {
            val fix = withTimeoutOrNull(10_000) {
                suspendCancellableCoroutine<Location?> { cont ->
                    val signal = CancellationSignal()
                    cont.invokeOnCancellation { signal.cancel() }
                    LocationManagerCompat.getCurrentLocation(
                        manager,
                        provider,
                        signal,
                        ContextCompat.getMainExecutor(context)
                    ) { location ->
                        if (cont.isActive) cont.resume(location)
                    }
                }
            }
            if (fix != null) return fix
        }

        return providers.firstNotNullOfOrNull { manager.getLastKnownLocation(it) }
    }
}
