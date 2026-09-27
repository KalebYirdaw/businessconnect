package com.example.businessconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun BusinessListScreen(
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onBusinessClick: (BusinessEntity) -> Unit
) {
    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val elevatedBlack = Color(0xFF171C22)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)

    val businesses by viewModel.businesses.collectAsState(
        initial = emptyList()
    )

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredBusinesses = businesses.filter { business ->
        val search = searchText.trim()

        if (search.isBlank()) {
            true
        } else {
            business.businessName.contains(search, ignoreCase = true) ||
                    business.ownerName.contains(search, ignoreCase = true) ||
                    business.productName.contains(search, ignoreCase = true) ||
                    business.businessType.contains(search, ignoreCase = true) ||
                    business.storeStatus.contains(search, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        TextButton(
            onClick = onBack
        ) {
            Text(
                text = "←  BACK",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "BUSINESS DIRECTORY",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "View and manage captured business opportunities.",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        /*
         * DIRECTORY SUMMARY
         */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardBlack)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "BUSINESSES",
                    color = brightBlue,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = businesses.size.toString(),
                    color = white,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "SHOWING",
                    color = secondaryText,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = filteredBusinesses.size.toString(),
                    color = white,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * SEARCH
         */

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = {
                Text(
                    text = "Search businesses"
                )
            },
            placeholder = {
                Text(
                    text = "Name, owner, product, type or status"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = white,
                unfocusedTextColor = white,
                focusedBorderColor = brightBlue,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = brightBlue,
                unfocusedLabelColor = secondaryText,
                cursorColor = brightBlue
            )
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (businesses.isEmpty()) {

            EmptyBusinessState(
                title = "NO BUSINESSES SAVED",
                message = "Add a business from the dashboard to see it in the directory.",
                cardBlack = cardBlack,
                elevatedBlack = elevatedBlack,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue
            )

        } else if (filteredBusinesses.isEmpty()) {

            EmptyBusinessState(
                title = "NO MATCHING BUSINESSES",
                message = "Try a different business name, owner, product, type or status.",
                cardBlack = cardBlack,
                elevatedBlack = elevatedBlack,
                white = white,
                secondaryText = secondaryText,
                brightBlue = brightBlue
            )

        } else {

            Text(
                text = "BUSINESS RECORDS",
                color = white,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = filteredBusinesses,
                    key = { business -> business.id }
                ) { business ->

                    BusinessCard(
                        business = business,
                        cardBlack = cardBlack,
                        elevatedBlack = elevatedBlack,
                        white = white,
                        secondaryText = secondaryText,
                        brightBlue = brightBlue,
                        borderColor = borderColor,
                        onClick = {
                            onBusinessClick(business)
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BusinessCard(
    business: BusinessEntity,
    cardBlack: Color,
    elevatedBlack: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    borderColor: Color,
    onClick: () -> Unit
) {
    val status = business.storeStatus.ifBlank {
        "Not provided"
    }

    val location = listOf(
        business.city,
        business.province
    )
        .filter {
            it.isNotBlank()
        }
        .joinToString(", ")
        .ifBlank {
            "Location not provided"
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardBlack)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(18.dp)
    ) {

        /*
         * BUSINESS HEADER
         */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = business.businessName.ifBlank {
                        "Unnamed Business"
                    },
                    color = white,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = business.businessType.ifBlank {
                        "Business type not provided"
                    },
                    color = brightBlue,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            StatusBadge(
                status = status,
                brightBlue = brightBlue,
                white = white
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * BUSINESS INFORMATION
         */

        BusinessInfoRow(
            label = "OWNER",
            value = business.ownerName.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        BusinessInfoRow(
            label = "PHONE",
            value = business.businessPhone.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        BusinessInfoRow(
            label = "LOCATION",
            value = location,
            white = white,
            secondaryText = secondaryText
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        BusinessInfoRow(
            label = "PRODUCT",
            value = business.productName.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * VIEW INDICATOR
         */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "VIEW BUSINESS",
                color = brightBlue,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = "→",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BusinessInfoRow(
    label: String,
    value: String,
    white: Color,
    secondaryText: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.width(72.dp),
            color = secondaryText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = white,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun StatusBadge(
    status: String,
    brightBlue: Color,
    white: Color
) {
    val statusColor = when {
        status.equals("Existing Store", ignoreCase = true) -> Color(0xFF35C759)
        status.equals("New Prospect", ignoreCase = true) -> brightBlue
        else -> Color(0xFFB8C0C8)
    }

    Text(
        text = status.uppercase(),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(statusColor.copy(alpha = 0.12f))
            .border(
                width = 1.dp,
                color = statusColor.copy(alpha = 0.45f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 9.dp,
                vertical = 6.dp
            ),
        color = statusColor,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun EmptyBusinessState(
    title: String,
    message: String,
    cardBlack: Color,
    elevatedBlack: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardBlack)
            .padding(22.dp)
    ) {
        Text(
            text = "B",
            color = brightBlue,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = title,
            color = white,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = message,
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
