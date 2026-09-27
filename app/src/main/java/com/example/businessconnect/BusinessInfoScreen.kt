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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun BusinessInfoScreen(
    business: BusinessEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
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

    val context = LocalContext.current

    val database = remember {
        BusinessDatabase.getDatabase(context)
    }

    val productRepository = remember {
        ProductRepository(
            database.productDao()
        )
    }

    val productFactory = remember {
        ProductViewModelFactory(
            productRepository
        )
    }

    val productViewModel: ProductViewModel = viewModel(
        key = "business_products_${business.id}",
        factory = productFactory
    )

    val products by productViewModel
        .getProductsForBusiness(business.id)
        .collectAsState(initial = emptyList())

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val missingProducts = products.filter {
        it.availability == "Missing"
    }

    val presentProducts = products.filter {
        it.availability == "Present"
    }

    val totalProducts = products.size
    val missingCount = missingProducts.size

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

    val status = business.storeStatus.ifBlank {
        "Not provided"
    }

    val statusColor = when {
        status.equals("Existing Store", ignoreCase = true) ->
            green

        status.equals("New Prospect", ignoreCase = true) ->
            brightBlue

        else ->
            secondaryText
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {

        /*
         * BACK
         */

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

        /*
         * BUSINESS HEADER
         */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(cardBlack)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "BUSINESS PROFILE",
                        color = brightBlue,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = business.businessName.ifBlank {
                            "Unnamed Business"
                        },
                        color = white,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = business.businessType.ifBlank {
                            "Business type not provided"
                        },
                        color = secondaryText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = status.uppercase(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .background(
                            statusColor.copy(alpha = 0.12f)
                        )
                        .border(
                            width = 1.dp,
                            color = statusColor.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(9.dp)
                        )
                        .padding(
                            horizontal = 9.dp,
                            vertical = 7.dp
                        ),
                    color = statusColor,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LOCATION",
                    color = secondaryText,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = location,
                    modifier = Modifier.weight(1f),
                    color = white,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * BUSINESS DETAILS
         */

        BusinessInfoSection(
            title = "BUSINESS DETAILS",
            subtitle = "Store and company information",
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            brightBlue = brightBlue,
            borderColor = borderColor
        ) {
            BusinessInfoRow(
                label = "BUSINESS NAME",
                value = business.businessName,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "BUSINESS TYPE",
                value = business.businessType,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "REGISTRATION NUMBER",
                value = business.registrationNumber,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "PHONE",
                value = business.businessPhone,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "EMAIL",
                value = business.businessEmail,
                white = white,
                secondaryText = secondaryText
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * OWNER / CONTACT
         */

        BusinessInfoSection(
            title = "OWNER / CONTACT",
            subtitle = "Primary business contact",
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            brightBlue = brightBlue,
            borderColor = borderColor
        ) {
            BusinessInfoRow(
                label = "OWNER NAME",
                value = business.ownerName,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "CONTACT PERSON",
                value = business.contactPerson,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "PHONE",
                value = business.ownerPhone,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "EMAIL",
                value = business.ownerEmail,
                white = white,
                secondaryText = secondaryText
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * LOCATION DETAILS
         */

        BusinessInfoSection(
            title = "LOCATION",
            subtitle = "Captured business location",
            cardBlack = cardBlack,
            elevatedBlack = elevatedBlack,
            white = white,
            secondaryText = secondaryText,
            brightBlue = brightBlue,
            borderColor = borderColor
        ) {
            BusinessInfoRow(
                label = "STREET ADDRESS",
                value = business.streetAddress,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "CITY",
                value = business.city,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "PROVINCE",
                value = business.province,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "POSTAL CODE",
                value = business.postalCode,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "LATITUDE",
                value = business.latitude,
                white = white,
                secondaryText = secondaryText
            )

            BusinessInfoRow(
                label = "LONGITUDE",
                value = business.longitude,
                white = white,
                secondaryText = secondaryText
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        /*
         * PRODUCT OVERVIEW
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

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Current store product assessment",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BusinessStatCard(
                    modifier = Modifier.weight(1f),
                    value = totalProducts.toString(),
                    label = "ASSESSED",
                    valueColor = brightBlue,
                    elevatedBlack = elevatedBlack,
                    secondaryText = secondaryText
                )

                BusinessStatCard(
                    modifier = Modifier.weight(1f),
                    value = presentProducts.size.toString(),
                    label = "PRESENT",
                    valueColor = green,
                    elevatedBlack = elevatedBlack,
                    secondaryText = secondaryText
                )

                BusinessStatCard(
                    modifier = Modifier.weight(1f),
                    value = missingCount.toString(),
                    label = "MISSING",
                    valueColor = red,
                    elevatedBlack = elevatedBlack,
                    secondaryText = secondaryText
                )
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

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

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Products recorded for this business",
                color = secondaryText,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            if (products.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(elevatedBlack)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "NO PRODUCTS RECORDED",
                        color = white,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "No product assessments are currently linked to this business.",
                        color = secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                products.forEach { product ->

                    ProductInfoCard(
                        product = product,
                        elevatedBlack = elevatedBlack,
                        white = white,
                        secondaryText = secondaryText,
                        brightBlue = brightBlue,
                        green = green,
                        red = red,
                        borderColor = borderColor
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * OPPORTUNITY
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

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            when {
                products.isEmpty() -> {
                    Text(
                        text = "No products have been assessed.",
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
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )

                        Column {
                            Text(
                                text = "NO MISSING PRODUCTS",
                                color = green,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

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
                            text = missingCount.toString(),
                            color = red,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {
                            Text(
                                text = "PRODUCT OPPORTUNITY",
                                color = white,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(3.dp)
                            )

                            Text(
                                text = "$missingCount product(s) identified as missing.",
                                color = secondaryText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "MISSING PRODUCTS",
                        color = brightBlue,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

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

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

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

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * ACTIONS
         */

        Button(
            onClick = onEdit,
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
                text = "EDIT BUSINESS",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        TextButton(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "DELETE BUSINESS",
                color = red,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }

    /*
     * DELETE CONFIRMATION
     */

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            containerColor = cardBlack,
            titleContentColor = white,
            textContentColor = secondaryText,
            title = {
                Text(
                    text = "Delete Business?"
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${business.businessName}? This will also remove its product assessments."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) {
                    Text(
                        text = "DELETE",
                        color = red,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "CANCEL",
                        color = brightBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Composable
private fun BusinessInfoSection(
    title: String,
    subtitle: String,
    cardBlack: Color,
    elevatedBlack: Color,
    white: Color,
    secondaryText: Color,
    brightBlue: Color,
    borderColor: Color,
    content: @Composable () -> Unit
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

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = subtitle,
            color = secondaryText,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        content()
    }
}

@Composable
private fun BusinessInfoRow(
    label: String,
    value: String,
    white: Color,
    secondaryText: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp)
    ) {
        Text(
            text = label,
            color = secondaryText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = value.ifBlank {
                "Not provided"
            },
            color = white,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun BusinessStatCard(
    modifier: Modifier,
    value: String,
    label: String,
    valueColor: Color,
    elevatedBlack: Color,
    secondaryText: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(elevatedBlack)
            .padding(vertical = 14.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = label,
            color = secondaryText,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProductInfoCard(
    product: ProductEntity,
    elevatedBlack: Color,
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
            .background(elevatedBlack)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
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

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

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

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        ProductDetailRow(
            label = "QUANTITY",
            value = product.quantity.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        ProductDetailRow(
            label = "PRICE",
            value = product.price.ifBlank {
                "Not provided"
            },
            white = white,
            secondaryText = secondaryText
        )

        ProductDetailRow(
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
private fun ProductDetailRow(
    label: String,
    value: String,
    white: Color,
    secondaryText: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.width(78.dp),
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
