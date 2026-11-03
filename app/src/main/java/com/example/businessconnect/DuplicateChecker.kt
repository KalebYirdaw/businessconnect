package com.example.businessconnect

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

data class DuplicateMatch(
    val business: BusinessEntity,
    val reason: String
)

object DuplicateChecker {

    /** Pins closer than this are treated as "the same place". */
    private const val SAME_PLACE_METERS = 50.0

    private val noiseWords = setOf("pty", "ltd", "cc", "inc", "limited", "the", "and")

    private fun normalise(name: String): String =
        name.lowercase()
            .replace(Regex("[^a-z0-9 ]"), " ")
            .split(" ")
            .filter { it.isNotBlank() && it !in noiseWords }
            .joinToString("")

    fun distanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        return r * 2 * atan2(sqrt(a), sqrt(1 - a))
    }

    /**
     * @param excludeId skip this business (used when editing an existing record)
     */
    fun findDuplicates(
        name: String,
        latitude: String,
        longitude: String,
        existing: List<BusinessEntity>,
        excludeId: Int? = null
    ): List<DuplicateMatch> {

        val newName = normalise(name)
        val lat = latitude.toDoubleOrNull()
        val lon = longitude.toDoubleOrNull()
        val matches = mutableListOf<DuplicateMatch>()

        existing.forEach { other ->
            if (excludeId != null && other.id == excludeId) return@forEach

            val reasons = mutableListOf<String>()

            if (newName.isNotBlank() && normalise(other.businessName) == newName) {
                reasons += "same name"
            }

            val oLat = other.latitude.toDoubleOrNull()
            val oLon = other.longitude.toDoubleOrNull()
            if (lat != null && lon != null && oLat != null && oLon != null) {
                val d = distanceMeters(lat, lon, oLat, oLon)
                if (d <= SAME_PLACE_METERS) {
                    reasons += "same location (${d.roundToInt()} m away)"
                }
            }

            if (reasons.isNotEmpty()) {
                matches += DuplicateMatch(other, reasons.joinToString(" and "))
            }
        }
        return matches
    }
}
