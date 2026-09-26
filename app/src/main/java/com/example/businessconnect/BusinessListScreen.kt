package com.example.businessconnect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BusinessListScreen(
    viewModel: BusinessViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val businesses by viewModel.businesses.collectAsState(
        initial = emptyList()
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Businesses",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${businesses.size} business(es) saved",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (businesses.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "No businesses saved yet.",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Add a business from the dashboard to see it here.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    items = businesses,
                    key = { business -> business.id }
                ) { business ->

                    BusinessCard(
                        business = business
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
fun BusinessCard(
    business: BusinessEntity
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = business.businessName.ifBlank {
                    "Unnamed Business"
                },
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Type: ${business.businessType.ifBlank { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Owner: ${business.ownerName.ifBlank { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Phone: ${business.businessPhone.ifBlank { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Location: ${
                    listOf(
                        business.city,
                        business.province
                    )
                        .filter { it.isNotBlank() }
                        .joinToString(", ")
                        .ifBlank { "Not provided" }
                }",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Product: ${business.productName.ifBlank { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}