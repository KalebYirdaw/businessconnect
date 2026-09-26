package com.example.businessconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
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
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Location Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Search for the business location",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                searchResults = emptyList()
                searchError = ""
            },
            label = {
                Text("Search location")
            },
            placeholder = {
                Text("Example: Sandton City")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

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
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSearching
        ) {

            if (isSearching) {

                CircularProgressIndicator(
                    modifier = Modifier.height(20.dp),
                    strokeWidth = 2.dp
                )

            } else {

                Text("Search")
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (searchError.isNotBlank()) {

            Text(
                text = searchError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        if (searchResults.isNotEmpty()) {

            Text(
                text = "Search Results",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            searchResults.forEach { result ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = result.displayName,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select Location")
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        Text(
            text = "Selected Location",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LocationField(
            label = "Street Address",
            value = businessData.streetAddress
        )

        LocationField(
            label = "City",
            value = businessData.city
        )

        LocationField(
            label = "Province",
            value = businessData.province
        )

        LocationField(
            label = "Postal Code",
            value = businessData.postalCode
        )

        LocationField(
            label = "Latitude",
            value = businessData.latitude
        )

        LocationField(
            label = "Longitude",
            value = businessData.longitude
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "© OpenStreetMap contributors",
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}

@Composable
private fun LocationField(
    label: String,
    value: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        readOnly = true,
        singleLine = true
    )
}