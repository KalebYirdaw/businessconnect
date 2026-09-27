
package com.example.businessconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LocationDetailsScreen(
    businessData: BusinessData,
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val elevatedBlack = Color(0xFF171C22)
    val primaryBlue = Color(0xFF0066CC)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)
    val green = Color(0xFF35C759)

    var searchText by remember {
        mutableStateOf("")
    }

    var searchResults by remember {
        mutableStateOf<List<LocationSearchResult>>(emptyList())
    }

    var isSearching by remember {
        mutableStateOf(false)
    }

    var searchError by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        // Back button
        TextButton(
            onClick = onBack
        ) {
            Text(
                text = "←  BACK",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Header
        Text(
            text = "LOCATION",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Find and confirm the business location",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Progress
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "LOCATION DETAILS",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "03 / 04",
                    color = secondaryText,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                LocationProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                LocationProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                LocationProgressStep(
                    active = true,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )

                LocationProgressStep(
                    active = false,
                    modifier = Modifier.weight(1f),
                    brightBlue = brightBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Search card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Text(
                text = "SEARCH LOCATION",
                color = white,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Search for the business using a place, suburb, shopping centre or address.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    searchResults = emptyList()
                    searchError = ""
                },
                label = {
                    Text(
                        text = "Search location",
                        color = secondaryText
                    )
                },
                placeholder = {
                    Text(
                        text = "Example: Sandton City",
                        color = Color(0xFF6F7882)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedTextColor = white,
                    unfocusedTextColor = white,
                    focusedBorderColor = brightBlue,
                    unfocusedBorderColor = borderColor,
                    focusedLabelColor = brightBlue,
                    unfocusedLabelColor = secondaryText,
                    cursorColor = brightBlue
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (searchText.isBlank()) {

                        searchError =
                            "Please enter a location to search."

                        return@Button
                    }

                    isSearching = true
                    searchError = ""
                    searchResults = emptyList()

                    scope.launch {

                        val results =
                            withContext(Dispatchers.IO) {

                                LocationSearchService.searchLocations(
                                    searchText
                                )
                            }

                        searchResults = results

                        isSearching = false

                        if (results.isEmpty()) {

                            searchError =
                                "No locations found. Try a different search."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = !isSearching,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue,
                    contentColor = white
                )
            ) {

                if (isSearching) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        color = white,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "SEARCHING...",
                        fontWeight = FontWeight.Bold
                    )

                } else {

                    Text(
                        text = "SEARCH LOCATION",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error message
        if (searchError.isNotBlank()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF211418))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF6B3038),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(14.dp)
            ) {

                Text(
                    text = searchError,
                    color = Color(0xFFFF7B86),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Search results
        if (searchResults.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(cardBlack)
                    .padding(18.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "SEARCH RESULTS",
                        color = white,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${searchResults.size} FOUND",
                        color = brightBlue,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                searchResults.forEach { result ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(elevatedBlack)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .padding(14.dp)
                    ) {

                        Text(
                            text = result.displayName,
                            color = white,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {

                                businessData.streetAddress =
                                    result.streetAddress

                                businessData.city =
                                    result.city

                                businessData.province =
                                    result.province

                                businessData.postalCode =
                                    result.postalCode

                                businessData.latitude =
                                    result.latitude

                                businessData.longitude =
                                    result.longitude

                                searchText =
                                    result.displayName

                                searchResults =
                                    emptyList()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryBlue,
                                contentColor = white
                            )
                        ) {

                            Text(
                                text = "SELECT LOCATION",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Selected location
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "SELECTED LOCATION",
                    color = white,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (
                    businessData.city.isNotBlank() ||
                    businessData.province.isNotBlank() ||
                    businessData.latitude.isNotBlank()
                ) {

                    Text(
                        text = "CONFIRMED",
                        color = green,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LocationField(
                label = "Street Address",
                value = businessData.streetAddress,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            LocationField(
                label = "City",
                value = businessData.city,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            LocationField(
                label = "Province",
                value = businessData.province,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            LocationField(
                label = "Postal Code",
                value = businessData.postalCode,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            LocationField(
                label = "Latitude",
                value = businessData.latitude,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )

            LocationField(
                label = "Longitude",
                value = businessData.longitude,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue,
                borderColor = borderColor
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "© OpenStreetMap contributors",
            color = Color(0xFF7E8791),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Continue
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = white
            )
        ) {

            Text(
                text = "CONTINUE  →",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LocationField(
    label: String,
    value: String,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    borderColor: Color
) {

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = {
            Text(
                text = label,
                color = secondaryText
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        readOnly = true,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            focusedTextColor = white,
            unfocusedTextColor = white,
            focusedBorderColor = brightBlue,
            unfocusedBorderColor = borderColor,
            focusedLabelColor = brightBlue,
            unfocusedLabelColor = secondaryText,
            cursorColor = brightBlue
        )
    )
}

@Composable
private fun LocationProgressStep(
    active: Boolean,
    modifier: Modifier,
    brightBlue: Color
) {

    Spacer(
        modifier = modifier
            .height(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (active) {
                    brightBlue
                } else {
                    Color(0xFF28313A)
                }
            )
    )
}
