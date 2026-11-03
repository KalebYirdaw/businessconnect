
package com.example.businessconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.delay
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
    onNext: () -> Unit,
    existingBusinesses: List<BusinessEntity> = emptyList()
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

    var suggestions by remember {
        mutableStateOf<List<LocationSuggestion>>(emptyList())
    }

    var isSearching by remember {
        mutableStateOf(false)
    }

    var searchError by remember {
        mutableStateOf("")
    }

    // Text we filled in ourselves (after a pick / GPS fix) - no need to search for it
    var selectedText by remember {
        mutableStateOf<String?>(null)
    }

    // Business data fields are plain vars, so bump this to redraw after we change them
    var refreshTick by remember {
        mutableStateOf(0)
    }
    @Suppress("UNUSED_VARIABLE")
    val redrawTrigger = refreshTick

    fun applyLocation(
        lat: Double,
        lon: Double,
        address: LocationSearchResult?
    ) {
        businessData.latitude = lat.toString()
        businessData.longitude = lon.toString()

        if (address != null) {
            if (address.streetAddress.isNotBlank()) businessData.streetAddress = address.streetAddress
            if (address.city.isNotBlank()) businessData.city = address.city
            if (address.province.isNotBlank()) businessData.province = address.province
            if (address.postalCode.isNotBlank()) businessData.postalCode = address.postalCode
        }

        refreshTick += 1
    }

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    // Search-as-you-type: waits 300ms after the last keystroke, cancels stale requests
    LaunchedEffect(searchText) {
        if (searchText == selectedText) {
            return@LaunchedEffect
        }
        if (searchText.trim().length < 2) {
            suggestions = emptyList()
            isSearching = false
            searchError = ""
            return@LaunchedEffect
        }
        delay(300)
        isSearching = true
        val found = withContext(Dispatchers.IO) {
            LocationSearchService.suggest(searchText)
        }
        suggestions = found
        isSearching = false
        searchError = if (found.isEmpty()) "No matches yet. Keep typing or try a nearby landmark." else ""
    }

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
                text = "Start typing a place, suburb, shopping centre or address.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
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
                trailingIcon = {
                    if (isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = brightBlue,
                            strokeWidth = 2.dp
                        )
                    } else if (searchText.isNotEmpty()) {
                        TextButton(onClick = {
                            searchText = ""
                            suggestions = emptyList()
                        }) {
                            Text("✕", color = secondaryText)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
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

            // Suggestions dropdown (Uber-style list under the field)
            if (suggestions.isNotEmpty()) {

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(elevatedBlack)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                ) {
                    suggestions.forEachIndexed { index, suggestion ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusManager.clearFocus()
                                    scope.launch {
                                        val result = LocationSearchService.resolve(suggestion)

                                        if (result == null) {
                                            searchError = "Couldn't load that location. Please try another result."
                                            return@launch
                                        }

                                        businessData.streetAddress = result.streetAddress
                                        businessData.city = result.city
                                        businessData.province = result.province
                                        businessData.postalCode = result.postalCode
                                        businessData.latitude = result.latitude
                                        businessData.longitude = result.longitude

                                        selectedText = suggestion.title
                                        searchText = suggestion.title
                                        suggestions = emptyList()
                                        refreshTick += 1
                                    }
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = suggestion.title,
                                color = white,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (suggestion.subtitle.isNotBlank()) {
                                Text(
                                    text = suggestion.subtitle,
                                    color = secondaryText,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        if (index < suggestions.lastIndex) {
                            HorizontalDivider(color = borderColor)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            UseMyLocationButton(
                onLocationFound = { lat, lon, address ->
                    applyLocation(lat, lon, address)
                    val label = address?.displayName?.takeIf { it.isNotBlank() }
                        ?: "Current location"
                    selectedText = label
                    searchText = label
                    suggestions = emptyList()
                },
                containerColor = elevatedBlack,
                contentColor = brightBlue,
                errorColor = Color(0xFFFF7B86)
            )
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

        // Map: tap to place the pin, long-press and drag to fine-tune
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {

            Text(
                text = "PIN ON MAP",
                color = white,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Tap the map to drop the pin, or long-press the pin and drag it. The address updates to match. Handy when a place isn't in the search results.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(14.dp))

            LocationMapPicker(
                latitude = businessData.latitude,
                longitude = businessData.longitude,
                onPinMoved = { lat, lon, address ->
                    applyLocation(lat, lon, address)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Duplicate warning
        val duplicates = DuplicateChecker.findDuplicates(
            name = businessData.businessName,
            latitude = businessData.latitude,
            longitude = businessData.longitude,
            existing = existingBusinesses
        )

        if (duplicates.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF241D0F))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF7A5C1E),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(14.dp)
            ) {

                Text(
                    text = "POSSIBLE DUPLICATE",
                    color = Color(0xFFFFC857),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                duplicates.take(3).forEach { match ->
                    Text(
                        text = "${match.business.businessName.ifBlank { "Unnamed business" }} - ${match.reason}",
                        color = Color(0xFFF2DDB0),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
