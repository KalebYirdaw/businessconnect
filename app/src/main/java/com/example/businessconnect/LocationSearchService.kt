package com.example.businessconnect

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class LocationSearchResult(
    val displayName: String,
    val streetAddress: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val latitude: String,
    val longitude: String
)

object LocationSearchService {

    fun searchLocations(
        query: String
    ): List<LocationSearchResult> {

        if (query.isBlank()) {
            return emptyList()
        }

        val encodedQuery = URLEncoder.encode(
            query,
            "UTF-8"
        )

        val urlString =
            "https://nominatim.openstreetmap.org/search" +
                    "?q=$encodedQuery" +
                    "&format=json" +
                    "&addressdetails=1" +
                    "&namedetails=1" +
                    "&limit=5"

        val connection =
            URL(urlString).openConnection() as HttpURLConnection

        connection.requestMethod = "GET"

        connection.setRequestProperty(
            "User-Agent",
            "BusinessConnect/1.0"
        )

        connection.connectTimeout = 10_000
        connection.readTimeout = 10_000

        return try {

            val responseCode =
                connection.responseCode

            if (responseCode != HttpURLConnection.HTTP_OK) {
                return emptyList()
            }

            val response =
                connection.inputStream
                    .bufferedReader()
                    .use {
                        it.readText()
                    }

            parseResults(response)

        } catch (e: Exception) {

            emptyList()

        } finally {

            connection.disconnect()
        }
    }

    private fun parseResults(
        json: String
    ): List<LocationSearchResult> {

        val results =
            mutableListOf<LocationSearchResult>()

        val jsonArray =
            JSONArray(json)

        for (i in 0 until jsonArray.length()) {

            val item =
                jsonArray.getJSONObject(i)

            val address =
                item.optJSONObject("address")

            // --------------------------------
            // STREET NAME
            // --------------------------------

            val street =
                address?.optString("road")
                    ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("pedestrian")
                        ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("residential")
                        ?.takeIf { it.isNotBlank() }
                    ?: ""

            // --------------------------------
            // STREET / HOUSE NUMBER
            // --------------------------------

            val houseNumber =
                address?.optString("house_number")
                    ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("house")
                        ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("building")
                        ?.takeIf { it.isNotBlank() }
                    ?: ""

            // --------------------------------
            // COMBINE NUMBER + STREET
            // --------------------------------

            val streetAddress =
                when {

                    houseNumber.isNotBlank() &&
                            street.isNotBlank() -> {
                        "$houseNumber $street"
                    }

                    houseNumber.isNotBlank() -> {
                        houseNumber
                    }

                    street.isNotBlank() -> {
                        street
                    }

                    else -> {
                        ""
                    }
                }

            // --------------------------------
            // CITY
            // --------------------------------

            val city =
                address?.optString("city")
                    ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("town")
                        ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("village")
                        ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("municipality")
                        ?.takeIf { it.isNotBlank() }
                    ?: ""

            // --------------------------------
            // PROVINCE
            // --------------------------------

            val province =
                address?.optString("state")
                    ?.takeIf { it.isNotBlank() }
                    ?: address?.optString("province")
                        ?.takeIf { it.isNotBlank() }
                    ?: ""

            // --------------------------------
            // POSTAL CODE
            // --------------------------------

            val postalCode =
                address?.optString("postcode")
                    ?.takeIf { it.isNotBlank() }
                    ?: ""

            // --------------------------------
            // COORDINATES
            // --------------------------------

            val latitude =
                item.optString("lat")

            val longitude =
                item.optString("lon")

            // --------------------------------
            // FULL DISPLAY NAME
            // --------------------------------

            val displayName =
                item.optString("display_name")

            results.add(
                LocationSearchResult(
                    displayName = displayName,
                    streetAddress = streetAddress,
                    city = city,
                    province = province,
                    postalCode = postalCode,
                    latitude = latitude,
                    longitude = longitude
                )
            )
        }

        return results
    }
}