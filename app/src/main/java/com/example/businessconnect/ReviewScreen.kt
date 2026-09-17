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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Review Business",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Review all information before saving",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        ReviewSection(
            title = "Business Details",
            details = listOf(
                "Business Name" to businessData.businessName,
                "Business Type" to businessData.businessType,
                "Registration Number" to businessData.registrationNumber,
                "Phone Number" to businessData.businessPhone,
                "Email Address" to businessData.businessEmail
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewSection(
            title = "Owner Details",
            details = listOf(
                "Owner Name" to businessData.ownerName,
                "Contact Person" to businessData.contactPerson,
                "Phone Number" to businessData.ownerPhone,
                "Email Address" to businessData.ownerEmail
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewSection(
            title = "Location",
            details = listOf(
                "Street Address" to businessData.streetAddress,
                "City" to businessData.city,
                "Province" to businessData.province,
                "Postal Code" to businessData.postalCode,
                "Latitude" to businessData.latitude,
                "Longitude" to businessData.longitude
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewSection(
            title = "Product",
            details = listOf(
                "Product Name" to businessData.productName,
                "Category" to businessData.productCategory,
                "Description" to businessData.productDescription,
                "Price" to businessData.price,
                "Quantity" to businessData.quantity
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Business")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ReviewSection(
    title: String,
    details: List<Pair<String, String>>
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            details.forEach { detail ->

                Text(
                    text = detail.first,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = if (detail.second.isBlank()) {
                        "Not provided"
                    } else {
                        detail.second
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}