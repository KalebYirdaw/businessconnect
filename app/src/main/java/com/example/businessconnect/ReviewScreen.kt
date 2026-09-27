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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ReviewScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSave: () -> Unit
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
    val red = Color(0xFFFF5A67)

    val products = businessData.products

    val missingProducts = products.filter {
        it.availability == "Missing"
    }

    val presentProducts = products.filter {
        it.availability == "Present"
    }

    val totalProducts = products.size
    val opportunityCount = missingProducts.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        TextButton(onClick = onBack) {
            Text(
                text = "←  BACK",
                color = brightBlue,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "REVIEW BUSINESS",
            color = white,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Review the captured information before saving this business.",
            color = secondaryText,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        /*
         * REVIEW PROGRESS
         */

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
                    text = "FINAL REVIEW",
                    color = brightBlue,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "READY TO SAVE",
                    color = green,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ReviewProgressStep(
                    modifier = Modifier.weight(1f),
                    active = true,
                    brightBlue = brightBlue
                )

                ReviewProgressStep(
                    modifier = Modifier.weight(1f),
                    active = true,
                    brightBlue = brightBlue
                )

                ReviewProgressStep(
                    modifier = Modifier.weight(1f),
                    active = true,
                    brightBlue = brightBlue
                )

                ReviewProgressStep(
                    modifier = Modifier.weight(1f),
                    active = true,
                    brightBlue = brightBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        /*
         * BUSINESS DETAILS
         */

        ReviewSection(
            title = "BUSINESS DETAILS",
            subtitle = "Store information",
            details = listOf(
                "Business Name" to businessData.businessName,
                "Business Type" to businessData.businessType,
                "Store Status" to businessData.storeStatus,
                "Registration Number" to businessData.registrationNumber,
                "Phone Number" to businessData.businessPhone,
                "Email Address" to businessData.businessEmail
            ),
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            borderColor = borderColor,
            brightBlue = brightBlue
        )

        Spacer(modifier = Modifier.height(14.dp))

        /*
         * OWNER DETAILS
         */

        ReviewSection(
            title = "OWNER / CONTACT",
            subtitle = "Primary business contact",
            details = listOf(
                "Owner Name" to businessData.ownerName,
                "Contact Person" to businessData.contactPerson,
                "Phone Number" to businessData.ownerPhone,
                "Email Address" to businessData.ownerEmail
            ),
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            borderColor = borderColor,
            brightBlue = brightBlue
        )

        Spacer(modifier = Modifier.height(14.dp))

        /*
         * LOCATION
         */

        ReviewSection(
            title = "LOCATION",
            subtitle = "Business location",
            details = listOf(
                "Street Address" to businessData.streetAddress,
                "City" to businessData.city,
                "Province" to businessData.province,
                "Postal Code" to businessData.postalCode,
                "Latitude" to businessData.latitude,
                "Longitude" to businessData.longitude
            ),
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            borderColor = borderColor,
            brightBlue = brightBlue
        )

        Spacer(modifier = Modifier.height(18.dp))

        /*
         * PRODUCT SUMMARY
         */

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
                .padding(18.dp)
        ) {
            Text(
                text = "PRODUCT OVERVIEW",
                color = white,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Summary of the store assessment",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ReviewStatCard(
                    modifier = Modifier.weight(1f),
                    value = totalProducts.toString(),
                    label = "ASSESSED",
                    valueColor = brightBlue,
                    cardColor = elevatedBlack,
                    white = white,
                    secondaryText = secondaryText
                )

                ReviewStatCard(
                    modifier = Modifier.weight(1f),
                    value = presentProducts.size.toString(),
                    label = "PRESENT",
                    valueColor = green,
                    cardColor = elevatedBlack,
                    white = white,
                    secondaryText = secondaryText
                )

                ReviewStatCard(
                    modifier = Modifier.weight(1f),
                    value = opportunityCount.toString(),
                    label = "MISSING",
                    valueColor = red,
                    cardColor = elevatedBlack,
                    white = white,
                    secondaryText = secondaryText
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        /*
         * PRODUCT ASSESSMENT
         */

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
                .padding(18.dp)
        ) {
            Text(
                text = "PRODUCT ASSESSMENT",
                color = white,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Detailed product information captured in store.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (products.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(elevatedBlack)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "NO PRODUCTS SELECTED",
                        color = white,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "No product assessment was captured.",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                products.forEach { product ->
                    ProductReviewItem(
                        product = product,
                        cardColor = elevatedBlack,
                        white = white,
                        secondaryText = secondaryText,
                        brightBlue = brightBlue,
                        green = green,
                        red = red,
                        borderColor = borderColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        /*
         * OPPORTUNITY SUMMARY
         */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .border(
                    width = 1.dp,
                    color = if (missingProducts.isNotEmpty()) {
                        Color(0xFF6B2730)
                    } else {
                        borderColor
                    },
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(18.dp)
        ) {
            Text(
                text = "OPPORTUNITY SUMMARY",
                color = white,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            when {
                products.isEmpty() -> {
                    Text(
                        text = "No products were assessed.",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                missingProducts.isEmpty() -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✓",
                            color = green,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "NO MISSING PRODUCTS",
                                color = green,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "All assessed products are currently available.",
                                color = secondaryText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                else -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = opportunityCount.toString(),
                            color = red,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "PRODUCT OPPORTUNITY",
                                color = white,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "$opportunityCount product(s) identified as missing.",
                                color = secondaryText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "MISSING PRODUCTS",
                        color = brightBlue,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    missingProducts.forEach { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "•",
                                color = red,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = product.productName,
                                color = white,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /*
         * SAVE BUSINESS
         */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(cardBlack)
                .padding(18.dp)
        ) {
            Text(
                text = "READY TO SAVE?",
                color = white,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Save this business and its product assessment to the local database.",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue,
                    contentColor = white
                )
            ) {
                Text(
                    text = "SAVE BUSINESS  ✓",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ReviewSection(
    title: String,
    subtitle: String,
    details: List<Pair<String, String>>,
    cardBlack: Color,
    elevatedBlack: Color,
    white: Color,
    secondaryText: Color,
    borderColor: Color,
    brightBlue: Color
) {
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
            .padding(18.dp)
    ) {
        Text(
            text = title,
            color = white,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            color = secondaryText,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(14.dp))

        details.forEachIndexed { index, detail ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(elevatedBlack)
                    .padding(12.dp)
            ) {
                Text(
                    text = detail.first.uppercase(),
                    color = brightBlue,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (detail.second.isBlank()) {
                        "Not provided"
                    } else {
                        detail.second
                    },
                    color = white,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (index < details.lastIndex) {
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
    }
}

@Composable
private fun ReviewStatCard(
    modifier: Modifier,
    value: String,
    label: String,
    valueColor: Color,
    cardColor: Color,
    white: Color,
    secondaryText: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .padding(vertical = 14.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            color = secondaryText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProductReviewItem(
    product: ProductAssessment,
    cardColor: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    green: Color,
    red: Color,
    borderColor: Color
) {
    val availabilityColor =
        if (product.availability == "Present") {
            green
        } else {
            red
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(cardColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName,
                    color = white,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = product.productCategory,
                    color = brightBlue,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = product.availability.uppercase(),
                color = availabilityColor,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        ReviewProductDetail(
            label = "QUANTITY",
            value = product.quantity.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReviewProductDetail(
            label = "PRICE",
            value = product.price.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReviewProductDetail(
            label = "NOTES",
            value = product.notes.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )
    }
}

@Composable
private fun ReviewProductDetail(
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
            modifier = Modifier.width(80.dp),
            color = secondaryText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = white,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ReviewProgressStep(
    modifier: Modifier,
    active: Boolean,
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
