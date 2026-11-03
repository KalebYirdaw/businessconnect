package com.example.businessconnect

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** Final, fully-resolved location that gets copied into BusinessData. */
data class LocationSearchResult(
    val displayName: String,
    val streetAddress: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val latitude: String,
    val longitude: String
)

/** One row in the "as you type" dropdown. */
data class LocationSuggestion(
    val id: String,
    val title: String,
    val subtitle: String,
    val resolved: LocationSearchResult? = null
)

object LocationSearchService {

    // Photon = OpenStreetMap search built for search-as-you-type (no API key needed).
    // Bias + bounding box keep results in South Africa. Change these if needed.
    private const val BIAS_LAT = -26.2
    private const val BIAS_LNG = 28.04
    private const val BBOX = "16.3,-35.0,33.0,-22.0" // minLon,minLat,maxLon,maxLat

    fun suggest(query: String): List<LocationSuggestion> {
        val q = query.trim()
        if (q.length < 2) return emptyList()
        return try {
            photonSearch(q)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            emptyList()
        }
    }

    /** Photon returns full details with each suggestion, so no second call is needed. */
    fun resolve(suggestion: LocationSuggestion): LocationSearchResult? = suggestion.resolved

    private fun photonSearch(query: String): List<LocationSuggestion> {
        val url = "https://photon.komoot.io/api/" +
                "?q=${URLEncoder.encode(query, "UTF-8")}" +
                "&limit=10&lang=en" +
                "&lat=$BIAS_LAT&lon=$BIAS_LNG" +
                "&bbox=$BBOX"

        val json = http(url) ?: return emptyList()
        val features = JSONObject(json).optJSONArray("features") ?: return emptyList()
        val out = mutableListOf<LocationSuggestion>()

        for (i in 0 until features.length()) {
            featureToSuggestion(features.getJSONObject(i), i)?.let { out += it }
        }
        return out.distinctBy { it.title + it.subtitle }
    }

    /**
     * Reverse geocoding: "what address is at this pin / GPS point?"
     * Returns null when nothing is found or there is no connection.
     */
    fun reverse(latitude: Double, longitude: Double): LocationSearchResult? {
        return try {
            val url = "https://photon.komoot.io/reverse" +
                    "?lat=$latitude&lon=$longitude&lang=en&limit=1"
            val json = http(url) ?: return null
            val features = JSONObject(json).optJSONArray("features") ?: return null
            if (features.length() == 0) return null
            featureToSuggestion(features.getJSONObject(0), 0)?.resolved
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    private fun featureToSuggestion(f: JSONObject, index: Int): LocationSuggestion? {
        val p = f.optJSONObject("properties") ?: return null
        val coords = f.optJSONObject("geometry")?.optJSONArray("coordinates") ?: return null

        val name = p.optString("name")
        val street = listOf(p.optString("housenumber"), p.optString("street"))
            .filter { it.isNotBlank() }.joinToString(" ")
        val district = p.optString("district")
        val city = p.optString("city").ifBlank { district }
            .ifBlank { p.optString("county") }
        val province = p.optString("state")
        val postcode = p.optString("postcode")

        val title = name.ifBlank { street }.ifBlank { city }
        if (title.isBlank()) return null

        val subtitle = listOf(
            if (name.isNotBlank()) street else "",
            district.takeIf { it != city } ?: "",
            city,
            province
        ).filter { it.isNotBlank() && it != title }.distinct().joinToString(", ")

        return LocationSuggestion(
            id = "photon-$index-${coords.optDouble(0)}",
            title = title,
            subtitle = subtitle,
            resolved = LocationSearchResult(
                displayName = listOf(title, subtitle).filter { it.isNotBlank() }
                    .joinToString(", "),
                streetAddress = street.ifBlank { name },
                city = city,
                province = province,
                postalCode = postcode,
                latitude = coords.optDouble(1).toString(),
                longitude = coords.optDouble(0).toString()
            )
        )
    }

    private fun http(url: String): String? {
        val conn = URL(url).openConnection() as HttpURLConnection
        return try {
            conn.requestMethod = "GET"
            conn.connectTimeout = 5_000
            conn.readTimeout = 5_000
            conn.setRequestProperty("User-Agent", "BusinessConnect/1.0")
            if (conn.responseCode != HttpURLConnection.HTTP_OK) return null
            conn.inputStream.bufferedReader().use { it.readText() }
        } finally {
            conn.disconnect()
        }
    }
}
