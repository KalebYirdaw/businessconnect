package com.example.businessconnect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BusinessInfoScreen(
    business: BusinessEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    val context =
        LocalContext.current

    val database =
        remember {
            BusinessDatabase.getDatabase(
                context
            )
        }

    val productRepository =
        remember {
            ProductRepository(
                database.productDao()
            )
        }

    val productFactory =
        remember {
            ProductViewModelFactory(
                productRepository
            )
        }

    val productViewModel:
            ProductViewModel =
        viewModel(
            key =
                "business_products_${business.id}",
            factory =
                productFactory
        )

    val products by
    productViewModel
        .getProductsForBusiness(
            business.id
        )
        .collectAsState(
            initial = emptyList()
        )

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val missingProducts =
        products.filter {
            it.availability == "Missing"
        }

    val presentProducts =
        products.filter {
            it.availability == "Present"
        }

    val totalProducts =
        products.size

    val missingCount =
        missingProducts.size

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text =
                "Business Information",
            style =
                MaterialTheme.typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * BUSINESS DETAILS
         */

        InfoSection(
            title =
                "Business Details"
        ) {

            InfoRow(
                label =
                    "Business Name",
                value =
                    business.businessName
            )

            InfoRow(
                label =
                    "Business Type",
                value =
                    business.businessType
            )

            InfoRow(
                label =
                    "Store Status",
                value =
                    business.storeStatus
            )

            InfoRow(
                label =
                    "Registration Number",
                value =
                    business.registrationNumber
            )

            InfoRow(
                label =
                    "Phone Number",
                value =
                    business.businessPhone
            )

            InfoRow(
                label =
                    "Email Address",
                value =
                    business.businessEmail
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * OWNER DETAILS
         */

        InfoSection(
            title =
                "Owner / Contact"
        ) {

            InfoRow(
                label =
                    "Owner Name",
                value =
                    business.ownerName
            )

            InfoRow(
                label =
                    "Contact Person",
                value =
                    business.contactPerson
            )

            InfoRow(
                label =
                    "Phone Number",
                value =
                    business.ownerPhone
            )

            InfoRow(
                label =
                    "Email Address",
                value =
                    business.ownerEmail
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * LOCATION
         */

        InfoSection(
            title =
                "Location"
        ) {

            InfoRow(
                label =
                    "Street Address",
                value =
                    business.streetAddress
            )

            InfoRow(
                label =
                    "City",
                value =
                    business.city
            )

            InfoRow(
                label =
                    "Province",
                value =
                    business.province
            )

            InfoRow(
                label =
                    "Postal Code",
                value =
                    business.postalCode
            )

            InfoRow(
                label =
                    "Latitude",
                value =
                    business.latitude
            )

            InfoRow(
                label =
                    "Longitude",
                value =
                    business.longitude
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * PRODUCT OVERVIEW
         */

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Text(
                    text =
                        "Product Overview",

                    style =
                        MaterialTheme.typography
                            .titleLarge
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    ProductSummaryItem(
                        label =
                            "Assessed",
                        value =
                            totalProducts
                                .toString()
                    )

                    ProductSummaryItem(
                        label =
                            "Present",
                        value =
                            presentProducts
                                .size
                                .toString()
                    )

                    ProductSummaryItem(
                        label =
                            "Missing",
                        value =
                            missingCount
                                .toString()
                    )
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * PRODUCTS
         */

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Text(
                    text =
                        "Product Assessment",

                    style =
                        MaterialTheme.typography
                            .titleLarge
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                if (products.isEmpty()) {

                    Text(
                        text =
                            "No products recorded.",

                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                } else {

                    products.forEach { product ->

                        ProductInfoCard(
                            product =
                                product
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )
                    }
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * OPPORTUNITY
         */

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Text(
                    text =
                        "Opportunity Summary",

                    style =
                        MaterialTheme.typography
                            .titleLarge
                )

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                if (products.isEmpty()) {

                    Text(
                        text =
                            "No products have been assessed.",

                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                } else if (
                    missingProducts.isEmpty()
                ) {

                    Text(
                        text =
                            "No missing products identified.",

                        style =
                            MaterialTheme.typography
                                .bodyLarge
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            "All assessed products are currently available at this store.",

                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                } else {

                    Text(
                        text =
                            "$missingCount product(s) identified as an opportunity.",

                        style =
                            MaterialTheme.typography
                                .bodyLarge
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text =
                            "Missing Products",

                        style =
                            MaterialTheme.typography
                                .titleMedium
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    missingProducts.forEach { product ->

                        Text(
                            text =
                                "• ${product.productName}",

                            style =
                                MaterialTheme.typography
                                    .bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * ACTIONS
         */

        Button(
            onClick =
                onEdit,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                "Edit Business"
            )
        }

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        TextButton(
            onClick = {
                showDeleteDialog = true
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                "Delete Business"
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
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

            title = {
                Text(
                    "Delete Business?"
                )
            },

            text = {
                Text(
                    "Are you sure you want to delete ${business.businessName}? This will also remove its product assessments."
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
                        "Delete"
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
                        "Cancel"
                    )
                }
            }
        )
    }
}

@Composable
private fun ProductSummaryItem(
    label: String,
    value: String
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text =
                value,

            style =
                MaterialTheme.typography
                    .headlineSmall
        )

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        Text(
            text =
                label,

            style =
                MaterialTheme.typography
                    .bodySmall
        )
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    title,

                style =
                    MaterialTheme.typography
                        .titleLarge
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Text(
            text =
                label,

            style =
                MaterialTheme.typography
                    .labelLarge
        )

        Text(
            text =
                value.ifBlank {
                    "Not provided"
                },

            style =
                MaterialTheme.typography
                    .bodyMedium
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )
    }
}

@Composable
private fun ProductInfoCard(
    product: ProductEntity
) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    product.productName,

                style =
                    MaterialTheme.typography
                        .titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(
                text =
                    "Category: ${product.productCategory}",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Text(
                text =
                    "Availability: ${product.availability}",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Text(
                text =
                    "Quantity: ${
                        product.quantity.ifBlank {
                            "Not provided"
                        }
                    }",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Text(
                text =
                    "Price: ${
                        product.price.ifBlank {
                            "Not provided"
                        }
                    }",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Text(
                text =
                    "Notes: ${
                        product.notes.ifBlank {
                            "Not provided"
                        }
                    }",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )
        }
    }
}