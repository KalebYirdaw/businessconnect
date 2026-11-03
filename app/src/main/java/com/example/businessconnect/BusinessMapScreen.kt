package com.example.businessconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BusinessMapScreen(
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onBusinessClick: (BusinessEntity) -> Unit
) {
    val background = Color(0xFF080A0D)
    val cardBlack = Color(0xFF11151A)
    val primaryBlue = Color(0xFF0066CC)
    val brightBlue = Color(0xFF00AEEF)
    val white = Color(0xFFFFFFFF)
    val secondaryText = Color(0xFFB8C0C8)
    val borderColor = Color(0xFF28313A)
    val green = Color(0xFF35C759)

    val context = LocalContext.current
    val businesses by viewModel.businesses.collectAsState(initial = emptyList())
    var selectedId by remember { mutableStateOf<Int?>(null) }

    val mapped = businesses.filter {
        it.latitude.toDoubleOrNull() != null && it.longitude.toDoubleOrNull() != null
    }
    val selected = businesses.firstOrNull { it.id == selectedId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
            TextButton(onClick = onBack) {
                Text("←  BACK", color = brightBlue, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "BUSINESS MAP",
                color = white,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${mapped.size} of ${businesses.size} businesses have a pinned location",
                color = secondaryText,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                LegendDot(Color(0xFF00AEEF), "New Prospect", white)
                LegendDot(green, "Existing Store", white)
                LegendDot(Color(0xFF9AA4AE), "Other", white)
            }
        }

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {

            BusinessesMapView(
                businesses = businesses,
                modifier = Modifier.fillMaxSize(),
                onBusinessTapped = { selectedId = it.id }
            )

            if (mapped.isEmpty()) {
                Text(
                    text = "No businesses have coordinates yet.\nAdd a location to a business to see it here.",
                    color = white,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(cardBlack.copy(alpha = 0.92f))
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            selected?.let { business ->
                val lat = business.latitude.toDoubleOrNull()
                val lon = business.longitude.toDoubleOrNull()
                val location = listOf(business.streetAddress, business.city)
                    .filter { it.isNotBlank() }.joinToString(", ")
                    .ifBlank { "Address not provided" }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(14.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(cardBlack)
                        .border(1.dp, borderColor, RoundedCornerShape(18.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = business.businessName.ifBlank { "Unnamed Business" },
                        color = white,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${business.storeStatus}  •  $location",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onBusinessClick(business) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryBlue,
                                contentColor = white
                            )
                        ) { Text("VIEW DETAILS", fontWeight = FontWeight.Bold) }

                        Button(
                            onClick = {
                                if (lat != null && lon != null) DeviceActions.navigate(context, lat, lon)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF171C22),
                                contentColor = brightBlue
                            )
                        ) { Text("NAVIGATE", fontWeight = FontWeight.Bold) }
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendDot(color: Color, label: String, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(6.dp))
        Text(label, color = textColor, style = MaterialTheme.typography.labelSmall)
    }
}
