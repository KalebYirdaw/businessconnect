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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewScreen(
    businessData: BusinessData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSave: () -> Unit
) {

    val products =
        businessData.products

    val missingProducts =
        products.filter {
            it.availability == "Missing"
        }

    Column(
        modifier = modifier
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
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Review Business",
            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text =
                "Review all information before saving",
            style =
                MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        ReviewSection(
            title = "Business Details",
            details = listOf(
                "Business Name" to
                        businessData.businessName,

                "Business Type" to
                        businessData.businessType,

                "Store Status" to
                        businessData.storeStatus,

                "Registration Number" to
                        businessData.registrationNumber,

                "Phone Number" to
                        businessData.businessPhone,

                "Email Address" to
                        businessData.businessEmail
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        ReviewSection(
            title = "Owner Details",
            details = listOf(
                "Owner Name" to
                        businessData.ownerName,

                "Contact Person" to
                        businessData.contactPerson,

                "Phone Number" to
                        businessData.ownerPhone,

                "Email Address" to
                        businessData.ownerEmail
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        ReviewSection(
            title = "Location",
            details = listOf(
                "Street Address" to
                        businessData.streetAddress,

                "City" to
                        businessData.city,

                "Province" to
                        businessData.province,

                "Postal Code" to
                        businessData.postalCode,

                "Latitude" to
                        businessData.latitude,

                "Longitude" to
                        businessData.longitude
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * PRODUCT ASSESSMENT
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
                    text = "Product Assessment",
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
                            "No products selected.",
                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                } else {

                    Text(
                        text =
                            "${products.size} product(s) assessed",

                        style =
                            MaterialTheme.typography
                                .bodyLarge
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    products.forEach { product ->

                        ProductReviewItem(
                            product = product
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
            modifier = Modifier.height(16.dp)
        )

        /*
         * OPPORTUNITY SUMMARY
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

                if (missingProducts.isEmpty()) {

                    Text(
                        text =
                            "No missing products identified.",

                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                } else {

                    Text(
                        text =
                            "${missingProducts.size} missing product(s) identified",

                        style =
                            MaterialTheme.typography
                                .bodyLarge
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
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onSave,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text("Save Business")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}

@Composable
private fun ProductReviewItem(
    product: ProductAssessment
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
                    "Category: ${
                        product.productCategory
                    }",

                style =
                    MaterialTheme.typography
                        .bodyMedium
            )

            Text(
                text =
                    "Availability: ${
                        product.availability
                    }",

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

@Composable
fun ReviewSection(
    title: String,
    details: List<Pair<String, String>>
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
                text = title,

                style =
                    MaterialTheme.typography
                        .titleLarge
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            details.forEach { detail ->

                Text(
                    text = detail.first,

                    style =
                        MaterialTheme.typography
                            .labelLarge
                )

                Text(
                    text =
                        if (detail.second.isBlank()) {
                            "Not provided"
                        } else {
                            detail.second
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
    }
}